package co.edu.usbcali.jasan.java.domain.converter;

import co.edu.usbcali.jasan.java.domain.enums.TipoReaccion;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoReaccionConverter extends ValorDbEnumConverter<TipoReaccion> {
    public TipoReaccionConverter() {
        super(TipoReaccion.class);
    }
}
