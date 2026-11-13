package co.edu.usbcali.jasan.java.dto.request;

public record CrearUsuarioRequest(
        String email,
        String username,
        String password
) {
}
