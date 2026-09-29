package co.edu.usbcali.jasan.java.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record ObtenerMensajeResponse(
        Integer id,
        @JsonProperty("conversacion_id") Integer conversacionId,
        @JsonProperty("remitente_id") Integer remitenteId,
        @JsonProperty("destinatario_id") Integer destinatarioId,
        String contenido,
        Boolean leido,
        @JsonProperty("created_at") LocalDateTime createdAt,
        @JsonProperty("updated_at") LocalDateTime updatedAt,
        @JsonProperty("deleted_at") LocalDateTime deletedAt
) {
}
