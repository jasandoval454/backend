package co.edu.usbcali.jasan.java.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record ObtenerConversacionResponse(
        Integer id,
        @JsonProperty("usuario_1_id") Integer usuario1Id,
        @JsonProperty("usuario_2_id") Integer usuario2Id,
        @JsonProperty("created_at") LocalDateTime createdAt,
        @JsonProperty("updated_at") LocalDateTime updatedAt
) {
}
