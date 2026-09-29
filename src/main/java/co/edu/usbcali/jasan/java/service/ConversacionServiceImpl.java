package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.domain.Conversacion;
import co.edu.usbcali.jasan.java.domain.Usuario;
import co.edu.usbcali.jasan.java.domain.repository.ConversacionRepository;
import co.edu.usbcali.jasan.java.domain.repository.UsuarioRepository;
import co.edu.usbcali.jasan.java.dto.request.CrearConversacionRequest;
import co.edu.usbcali.jasan.java.dto.response.ObtenerConversacionResponse;
import co.edu.usbcali.jasan.java.mapper.ConversacionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConversacionServiceImpl implements ConversacionService {

    @Autowired
    private ConversacionRepository conversacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ObtenerConversacionResponse> obtenerConversaciones() {
        return ConversacionMapper.listaAResponses(conversacionRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ObtenerConversacionResponse> obtenerConversacionesDeUsuario(Integer usuarioId)
            throws Exception {
        validarId(usuarioId, "usuario");
        validarUsuario(usuarioId);
        return ConversacionMapper.listaAResponses(
                conversacionRepository.findByUsuarioId(usuarioId)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ObtenerConversacionResponse obtenerConversacionPorId(Integer id) throws Exception {
        validarId(id, "conversación");
        Conversacion conversacion = conversacionRepository.findById(id)
                .orElseThrow(() -> new Exception(
                        "No se ha encontrado la conversación con el id: " + id));
        return ConversacionMapper.conversacionAResponse(conversacion);
    }

    @Override
    @Transactional
    public ObtenerConversacionResponse crearConversacion(CrearConversacionRequest request)
            throws Exception {
        if (request == null) {
            throw new Exception("La petición de creación no puede ser nula");
        }
        validarId(request.usuario1Id(), "usuario 1");
        validarId(request.usuario2Id(), "usuario 2");
        if (request.usuario1Id().equals(request.usuario2Id())) {
            throw new Exception("Los participantes deben ser diferentes");
        }

        Usuario usuario1 = validarUsuario(request.usuario1Id());
        Usuario usuario2 = validarUsuario(request.usuario2Id());

        Integer usuarioMenorId = Math.min(request.usuario1Id(), request.usuario2Id());
        Integer usuarioMayorId = Math.max(request.usuario1Id(), request.usuario2Id());
        Usuario usuarioMenor = request.usuario1Id().equals(usuarioMenorId) ? usuario1 : usuario2;
        Usuario usuarioMayor = request.usuario1Id().equals(usuarioMenorId) ? usuario2 : usuario1;

        Conversacion conversacion = conversacionRepository
                .findEntreUsuarios(usuarioMenorId, usuarioMayorId)
                .orElseGet(() -> conversacionRepository.save(Conversacion.builder()
                        .usuario1(usuarioMenor)
                        .usuario2(usuarioMayor)
                        .build()));

        return ConversacionMapper.conversacionAResponse(conversacion);
    }

    private Usuario validarUsuario(Integer id) throws Exception {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new Exception("No se ha encontrado el usuario con el id: " + id));
    }

    private void validarId(Integer id, String nombre) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("El id del " + nombre + " debe ser mayor que cero");
        }
    }
}
