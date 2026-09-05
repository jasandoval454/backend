package co.edu.usbcali.jasan.java.domain.converter;

import co.edu.usbcali.jasan.java.domain.enums.TipoMedio;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoMedioConverter extends ValorDbEnumConverter<TipoMedio> {
    public TipoMedioConverter() {
        super(TipoMedio.class);
    }
}
