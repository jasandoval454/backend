package co.edu.usbcali.jasan.java.dto.request;

public record ModificarPerfilRequest(
        String nombre,
        String bio,
        String avatarUrl,
        String ubicacion,
        String enlace,
        String visibilidad
) {
}
