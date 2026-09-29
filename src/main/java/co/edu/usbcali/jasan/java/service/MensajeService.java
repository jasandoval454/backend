package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.dto.request.CrearMensajeRequest;
import co.edu.usbcali.jasan.java.dto.response.ObtenerMensajeResponse;

import java.util.List;

public interface MensajeService {

    List<ObtenerMensajeResponse> obtenerMensajes();

    ObtenerMensajeResponse obtenerMensajePorId(Integer id) throws Exception;

    List<ObtenerMensajeResponse> obtenerConversacion(Integer usuarioId, Integer otroUsuarioId)
            throws Exception;

    List<ObtenerMensajeResponse> obtenerMensajesNoLeidos(Integer destinatarioId) throws Exception;

    ObtenerMensajeResponse crearMensaje(CrearMensajeRequest request) throws Exception;

    ObtenerMensajeResponse marcarComoLeido(Integer id) throws Exception;
}
