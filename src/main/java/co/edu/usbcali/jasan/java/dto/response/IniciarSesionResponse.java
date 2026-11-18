package co.edu.usbcali.jasan.java.dto.response;

public record IniciarSesionResponse(
        String token,
        ObtenerUsuarioResponse usuario
) {
}
