package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.domain.Mensaje;
import co.edu.usbcali.jasan.java.domain.Conversacion;
import co.edu.usbcali.jasan.java.domain.Usuario;
import co.edu.usbcali.jasan.java.domain.repository.ConversacionRepository;
import co.edu.usbcali.jasan.java.domain.repository.MensajeRepository;
import co.edu.usbcali.jasan.java.domain.repository.UsuarioRepository;
import co.edu.usbcali.jasan.java.dto.request.CrearMensajeRequest;
import co.edu.usbcali.jasan.java.dto.response.ObtenerMensajeResponse;
import co.edu.usbcali.jasan.java.mapper.MensajeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class MensajeServiceImpl implements MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ConversacionRepository conversacionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ObtenerMensajeResponse> obtenerMensajes() {
        return MensajeMapper.listaMensajesAListaObtenerMensajesResponse(
                mensajeRepository.findAll()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ObtenerMensajeResponse obtenerMensajePorId(Integer id) throws Exception {
        validarId(id, "mensaje");

        Mensaje mensaje = mensajeRepository.findById(id)
                .orElseThrow(() -> new Exception("No se ha encontrado el mensaje con el id: " + id));

        return MensajeMapper.mensajeAObtenerMensajeResponse(mensaje);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ObtenerMensajeResponse> obtenerConversacion(
            Integer usuarioId,
            Integer otroUsuarioId
    ) throws Exception {
        validarId(usuarioId, "usuario");
        validarId(otroUsuarioId, "otro usuario");

        if (usuarioId.equals(otroUsuarioId)) {
            throw new Exception("Los usuarios de la conversación deben ser diferentes");
        }

        validarUsuarioExiste(usuarioId);
        validarUsuarioExiste(otroUsuarioId);

        Conversacion conversacion = conversacionRepository
                .findEntreUsuarios(usuarioId, otroUsuarioId)
                .orElseThrow(() -> new Exception("No existe una conversación entre estos usuarios"));

        return MensajeMapper.listaMensajesAListaObtenerMensajesResponse(
                mensajeRepository.findByConversacionIdOrderByCreatedAtAsc(conversacion.getId())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ObtenerMensajeResponse> obtenerMensajesNoLeidos(Integer destinatarioId)
            throws Exception {
        validarId(destinatarioId, "destinatario");
        validarUsuarioExiste(destinatarioId);

        return MensajeMapper.listaMensajesAListaObtenerMensajesResponse(
                mensajeRepository.findByDestinatarioIdAndLeidoFalseOrderByCreatedAtAsc(destinatarioId)
        );
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ObtenerMensajeResponse crearMensaje(CrearMensajeRequest request) throws Exception {
        if (request == null) {
            throw new Exception("La petición de creación no puede ser nula");
        }

        validarId(request.remitenteId(), "remitente");
        validarId(request.destinatarioId(), "destinatario");

        if (request.remitenteId().equals(request.destinatarioId())) {
            throw new Exception("El remitente y el destinatario deben ser diferentes");
        }

        if (request.contenido() == null || request.contenido().isBlank()) {
            throw new Exception("El contenido del mensaje no puede ser nulo ni vacío");
        }

        if (request.contenido().trim().length() > 2000) {
            throw new Exception("El contenido del mensaje no puede superar los 2000 caracteres");
        }

        Usuario remitente = validarUsuarioExiste(request.remitenteId());
        Usuario destinatario = validarUsuarioExiste(request.destinatarioId());
        Integer usuarioMenorId = Math.min(request.remitenteId(), request.destinatarioId());
        Integer usuarioMayorId = Math.max(request.remitenteId(), request.destinatarioId());
        Usuario usuarioMenor = request.remitenteId().equals(usuarioMenorId)
                ? remitente : destinatario;
        Usuario usuarioMayor = request.remitenteId().equals(usuarioMenorId)
                ? destinatario : remitente;

        Conversacion conversacion = conversacionRepository
                .findEntreUsuarios(usuarioMenorId, usuarioMayorId)
                .orElseGet(() -> conversacionRepository.save(Conversacion.builder()
                        .usuario1(usuarioMenor)
                        .usuario2(usuarioMayor)
                        .build()));
        conversacion.setUpdatedAt(LocalDateTime.now());

        Mensaje mensaje = MensajeMapper.crearMensajeRequestAMensaje(request);
        mensaje.setConversacion(conversacion);
        mensaje.setRemitente(remitente);
        mensaje.setDestinatario(destinatario);

        return MensajeMapper.mensajeAObtenerMensajeResponse(
                mensajeRepository.save(mensaje)
        );
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ObtenerMensajeResponse marcarComoLeido(Integer id) throws Exception {
        validarId(id, "mensaje");

        Mensaje mensaje = mensajeRepository.findById(id)
                .orElseThrow(() -> new Exception("No se ha encontrado el mensaje con el id: " + id));

        mensaje.setLeido(true);
        return MensajeMapper.mensajeAObtenerMensajeResponse(
                mensajeRepository.save(mensaje)
        );
    }

    private Usuario validarUsuarioExiste(Integer id) throws Exception {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new Exception("No se ha encontrado el usuario con el id: " + id));
    }

    private void validarId(Integer id, String nombre) throws Exception {
        if (id == null) {
            throw new Exception("El id del " + nombre + " no puede ser nulo");
        }
        if (id <= 0) {
            throw new Exception("El id del " + nombre + " debe ser mayor que cero");
        }
    }
}
