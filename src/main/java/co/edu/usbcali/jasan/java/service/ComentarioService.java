package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.dto.request.CrearComentarioRequest;
import co.edu.usbcali.jasan.java.dto.response.ObtenerComentarioResponse;

import java.util.List;

public interface ComentarioService {

    ObtenerComentarioResponse crearComentario(CrearComentarioRequest request) throws Exception;

    List<ObtenerComentarioResponse> obtenerComentariosPorPublicacion(Integer publicacionId)
            throws Exception;
}
