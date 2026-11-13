package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.domain.Comentario;
import co.edu.usbcali.jasan.java.domain.Notificacion;
import co.edu.usbcali.jasan.java.domain.Publicacion;
import co.edu.usbcali.jasan.java.domain.Usuario;
import co.edu.usbcali.jasan.java.domain.enums.TipoNotificacion;
import co.edu.usbcali.jasan.java.domain.repository.ComentarioRepository;
import co.edu.usbcali.jasan.java.domain.repository.NotificacionRepository;
import co.edu.usbcali.jasan.java.domain.repository.PublicacionRepository;
import co.edu.usbcali.jasan.java.domain.repository.UsuarioRepository;
import co.edu.usbcali.jasan.java.dto.request.CrearNotificacionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class NotificacionServiceImpl implements NotificacionService {
    private final NotificacionRepository notificacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final PublicacionRepository publicacionRepository;
    private final ComentarioRepository comentarioRepository;

    public NotificacionServiceImpl(
            NotificacionRepository notificacionRepository,
            UsuarioRepository usuarioRepository,
            PublicacionRepository publicacionRepository,
            ComentarioRepository comentarioRepository) {
        this.notificacionRepository = notificacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.publicacionRepository = publicacionRepository;
        this.comentarioRepository = comentarioRepository;
    }

    @Override
    @Transactional
    public Notificacion crearNotificacion(CrearNotificacionRequest request) {
        if (request == null || request.usuarioId() == null || request.usuarioId() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El id del destinatario debe ser mayor que cero");
        }
        if (request.tipo() == null || request.tipo().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tipo de notificación es obligatorio");
        }
        validarIdOpcional(request.publicacionId(), "publicación");
        validarIdOpcional(request.comentarioId(), "comentario");
        TipoNotificacion tipo = TipoNotificacion.getTipoNotificacion(request.tipo().trim());
        if (tipo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tipo de notificación no es válido");
        }

        Usuario destinatario = buscarUsuario(request.usuarioId(), "destinatario");
        Usuario origen = request.origenUsuarioId() == null
                ? null
                : buscarUsuario(request.origenUsuarioId(), "origen");
        Publicacion publicacion = request.publicacionId() == null
                ? null
                : publicacionRepository.findByIdAndDeletedAtIsNull(request.publicacionId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "No se encontró la publicación con el id: " + request.publicacionId()));
        Comentario comentario = request.comentarioId() == null
                ? null
                : comentarioRepository.findById(request.comentarioId())
                        .filter(c -> c.getDeletedAt() == null)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "No se encontró el comentario con el id: " + request.comentarioId()));
        if (publicacion != null && comentario != null
                && !publicacion.getId().equals(comentario.getPublicacion().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El comentario no pertenece a la publicación indicada");
        }

        Notificacion notificacion = Notificacion.builder()
                .destinatario(destinatario)
                .origen(origen)
                .tipo(tipo)
                .publicacion(publicacion)
                .comentario(comentario)
                .leida(false)
                .build();
        return notificacionRepository.save(notificacion);
    }

    private Usuario buscarUsuario(Integer id, String rol) {
        if (id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El id del usuario " + rol + " debe ser mayor que cero");
        }
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se encontró el usuario " + rol + " con el id: " + id));
    }

    private void validarIdOpcional(Integer id, String recurso) {
        if (id != null && id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El id del " + recurso + " debe ser mayor que cero");
        }
    }
}
