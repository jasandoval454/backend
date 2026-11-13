package co.edu.usbcali.jasan.java.dto.request;

public record CrearMedioRequest(
        Integer publicacionId,
        String tipo,
        String urlOriginal,
        String urlThumbnail,
        String formato,
        Integer tamanoBytes,
        Short orden
) {
}
