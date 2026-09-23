package co.edu.usbcali.jasan.java.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CrearPublicacionRequest(
        @JsonProperty("autor_id") Integer autorId,
        String contenido,
        @JsonProperty("privacidad_publicacion") String privacidadPublicacion
) {
}
