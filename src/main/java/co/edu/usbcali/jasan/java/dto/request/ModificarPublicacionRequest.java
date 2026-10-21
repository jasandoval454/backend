package co.edu.usbcali.jasan.java.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ModificarPublicacionRequest(
        String contenido,
        @JsonProperty("privacidad_publicacion") String privacidadPublicacion
) {
}
