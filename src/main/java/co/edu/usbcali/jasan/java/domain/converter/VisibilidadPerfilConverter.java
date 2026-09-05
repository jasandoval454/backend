package co.edu.usbcali.jasan.java.domain.converter;

import co.edu.usbcali.jasan.java.domain.enums.VisibilidadPerfil;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class VisibilidadPerfilConverter extends ValorDbEnumConverter<VisibilidadPerfil> {
    public VisibilidadPerfilConverter() {
        super(VisibilidadPerfil.class);
    }
}
