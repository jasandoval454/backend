package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.domain.Comentario;
import co.edu.usbcali.jasan.java.domain.Publicacion;
import co.edu.usbcali.jasan.java.domain.Usuario;
import co.edu.usbcali.jasan.java.domain.repository.ComentarioRepository;
import co.edu.usbcali.jasan.java.domain.repository.PublicacionRepository;
import co.edu.usbcali.jasan.java.domain.repository.UsuarioRepository;
import co.edu.usbcali.jasan.java.dto.request.CrearComentarioRequest;
import co.edu.usbcali.jasan.java.dto.request.ModificarComentarioRequest;
import co.edu.usbcali.jasan.java.dto.response.ObtenerComentarioResponse;
import co.edu.usbcali.jasan.java.mapper.ComentarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public ObtenerComentarioResponse crearComentario(CrearComentarioRequest request) throws Exception {
        if (request == null) {
            throw new Exception("La petición de creación no puede ser nula");
        }

        validarId(request.publicacionId(), "publicación");
        validarId(request.autorId(), "autor");

        if (request.contenido() == null || request.contenido().isBlank()) {
            throw new Exception("El contenido del comentario no puede ser nulo ni vacío");
        }
        if (request.contenido().trim().length() > 1000) {
            throw new Exception("El contenido del comentario no puede superar los 1000 caracteres");
        }

        Publicacion publicacion = publicacionRepository
                .findByIdAndDeletedAtIsNull(request.publicacionId())
                .orElseThrow(() -> new Exception(
                        "No existe la publicación con el id: " + request.publicacionId()));
        Usuario autor = usuarioRepository.findById(request.autorId())
                .orElseThrow(() -> new Exception(
                        "No se ha encontrado el autor con el id: " + request.autorId()));

        Comentario comentarioPadre = null;
        if (request.comentarioPadreId() != null) {
            validarId(request.comentarioPadreId(), "comentario padre");
            comentarioPadre = comentarioRepository
                    .findByIdAndPublicacion_IdAndDeletedAtIsNull(
                            request.comentarioPadreId(),
                            request.publicacionId()
                    )
                    .orElseThrow(() -> new Exception(
                            "El comentario padre no existe en la publicación indicada"));
        }

        Comentario comentario = ComentarioMapper.crearComentarioRequestAComentario(request);
        comentario.setPublicacion(publicacion);
        comentario.setAutor(autor);
        comentario.setComentarioPadre(comentarioPadre);

        return ComentarioMapper.comentarioAObtenerComentarioResponse(
                comentarioRepository.save(comentario)
        );
    }

    @Override
    @Transactional
    public ObtenerComentarioResponse modificarComentario(
            Integer id,
            ModificarComentarioRequest request
    ) throws Exception {
        validarId(id, "comentario");
        if (request == null || request.contenido() == null) {
            throw new Exception("Debe indicar el contenido del comentario para modificarlo");
        }
        if (request.contenido().isBlank()) {
            throw new Exception("El contenido del comentario no puede ser vacío");
        }
        if (request.contenido().trim().length() > 1000) {
            throw new Exception("El contenido del comentario no puede superar los 1000 caracteres");
        }

        Comentario comentario = comentarioRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new Exception("No existe el comentario con el id: " + id));
        comentario.setContenido(request.contenido().trim());

        return ComentarioMapper.comentarioAObtenerComentarioResponse(
                comentarioRepository.save(comentario)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ObtenerComentarioResponse> obtenerComentariosPorPublicacion(Integer publicacionId)
            throws Exception {
        validarId(publicacionId, "publicación");
        publicacionRepository.findByIdAndDeletedAtIsNull(publicacionId)
                .orElseThrow(() -> new Exception(
                        "No existe la publicación con el id: " + publicacionId));

        return comentarioRepository
                .findByPublicacion_IdAndDeletedAtIsNullOrderByCreatedAtAsc(publicacionId)
                .stream()
                .map(ComentarioMapper::comentarioAObtenerComentarioResponse)
                .toList();
    }

    private void validarId(Integer id, String nombre) throws Exception {
        if (id == null) {
            throw new Exception("El id de " + nombre + " no puede ser nulo");
        }
        if (id <= 0) {
            throw new Exception("El id de " + nombre + " debe ser mayor que cero");
        }
    }
}
