package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.dto.request.CrearConversacionRequest;
import co.edu.usbcali.jasan.java.dto.response.ObtenerConversacionResponse;

import java.util.List;

public interface ConversacionService {

    List<ObtenerConversacionResponse> obtenerConversaciones();

    List<ObtenerConversacionResponse> obtenerConversacionesDeUsuario(Integer usuarioId)
            throws Exception;

    ObtenerConversacionResponse obtenerConversacionPorId(Integer id) throws Exception;

    ObtenerConversacionResponse crearConversacion(CrearConversacionRequest request)
            throws Exception;
}
