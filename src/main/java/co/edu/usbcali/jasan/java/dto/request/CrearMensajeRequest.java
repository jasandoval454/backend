package co.edu.usbcali.jasan.java.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CrearMensajeRequest(
        @JsonProperty("remitente_id") Integer remitenteId,
        @JsonProperty("destinatario_id") Integer destinatarioId,
        String contenido
) {
}
