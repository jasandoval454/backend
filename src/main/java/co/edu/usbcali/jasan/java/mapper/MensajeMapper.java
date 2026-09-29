package co.edu.usbcali.jasan.java.mapper;

import co.edu.usbcali.jasan.java.domain.Mensaje;
import co.edu.usbcali.jasan.java.dto.request.CrearMensajeRequest;
import co.edu.usbcali.jasan.java.dto.response.ObtenerMensajeResponse;

import java.util.List;

public class MensajeMapper {

    private MensajeMapper() {
    }

    public static ObtenerMensajeResponse mensajeAObtenerMensajeResponse(Mensaje mensaje) {
        return new ObtenerMensajeResponse(
                mensaje.getId(),
                mensaje.getConversacion() != null ? mensaje.getConversacion().getId() : null,
                mensaje.getRemitente() != null ? mensaje.getRemitente().getId() : null,
                mensaje.getDestinatario() != null ? mensaje.getDestinatario().getId() : null,
                mensaje.getContenido(),
                mensaje.getLeido(),
                mensaje.getCreatedAt(),
                mensaje.getUpdatedAt(),
                mensaje.getDeletedAt()
        );
    }

    public static List<ObtenerMensajeResponse> listaMensajesAListaObtenerMensajesResponse(
            List<Mensaje> mensajes
    ) {
        return mensajes.stream()
                .map(MensajeMapper::mensajeAObtenerMensajeResponse)
                .toList();
    }

    public static Mensaje crearMensajeRequestAMensaje(CrearMensajeRequest request) {
        return Mensaje.builder()
                .contenido(request.contenido().trim())
                .leido(false)
                .build();
    }
}
