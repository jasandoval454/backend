package co.edu.usbcali.jasan.java.domain.converter;

import co.edu.usbcali.jasan.java.domain.enums.PrivacidadPublicacion;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PrivacidadPublicacionConverter extends ValorDbEnumConverter<PrivacidadPublicacion> {
    public PrivacidadPublicacionConverter() {
        super(PrivacidadPublicacion.class);
    }
}