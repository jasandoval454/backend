package co.edu.usbcali.jasan.java.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CrearComentarioRequest(
        @JsonProperty("publicacion_id") Integer publicacionId,
        @JsonProperty("autor_id") Integer autorId,
        @JsonProperty("comentario_padre_id") Integer comentarioPadreId,
        String contenido
) {
}
