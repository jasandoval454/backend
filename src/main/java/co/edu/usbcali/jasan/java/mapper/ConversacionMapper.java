package co.edu.usbcali.jasan.java.mapper;

import co.edu.usbcali.jasan.java.domain.Conversacion;
import co.edu.usbcali.jasan.java.dto.response.ObtenerConversacionResponse;

import java.util.List;

public class ConversacionMapper {

    private ConversacionMapper() {
    }

    public static ObtenerConversacionResponse conversacionAResponse(Conversacion conversacion) {
        return new ObtenerConversacionResponse(
                conversacion.getId(),
                conversacion.getUsuario1() != null ? conversacion.getUsuario1().getId() : null,
                conversacion.getUsuario2() != null ? conversacion.getUsuario2().getId() : null,
                conversacion.getCreatedAt(),
                conversacion.getUpdatedAt()
        );
    }

    public static List<ObtenerConversacionResponse> listaAResponses(
            List<Conversacion> conversaciones
    ) {
        return conversaciones.stream()
                .map(ConversacionMapper::conversacionAResponse)
                .toList();
    }
}
