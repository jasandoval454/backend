package co.edu.usbcali.jasan.java.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record ObtenerComentarioResponse(
        Integer id,
        @JsonProperty("publicacion_id") Integer publicacionId,
        @JsonProperty("autor_id") Integer autorId,
        @JsonProperty("comentario_padre_id") Integer comentarioPadreId,
        String contenido,
        @JsonProperty("created_at") LocalDateTime createdAt,
        @JsonProperty("updated_at") LocalDateTime updatedAt
) {
}
