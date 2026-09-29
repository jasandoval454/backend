package co.edu.usbcali.jasan.java.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CrearConversacionRequest(
        @JsonProperty("usuario_1_id") Integer usuario1Id,
        @JsonProperty("usuario_2_id") Integer usuario2Id
) {
}
