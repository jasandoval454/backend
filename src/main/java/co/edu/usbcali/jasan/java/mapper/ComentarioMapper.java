package co.edu.usbcali.jasan.java.mapper;

import co.edu.usbcali.jasan.java.domain.Comentario;
import co.edu.usbcali.jasan.java.dto.request.CrearComentarioRequest;
import co.edu.usbcali.jasan.java.dto.response.ObtenerComentarioResponse;

public class ComentarioMapper {

    private ComentarioMapper() {
    }

    public static Comentario crearComentarioRequestAComentario(CrearComentarioRequest request) {
        return Comentario.builder()
                .contenido(request.contenido().trim())
                .build();
    }

    public static ObtenerComentarioResponse comentarioAObtenerComentarioResponse(Comentario comentario) {
        return new ObtenerComentarioResponse(
                comentario.getId(),
                comentario.getPublicacion().getId(),
                comentario.getAutor().getId(),
                comentario.getComentarioPadre() != null
                        ? comentario.getComentarioPadre().getId()
                        : null,
                comentario.getContenido(),
                comentario.getCreatedAt(),
                comentario.getUpdatedAt()
        );
    }
}
