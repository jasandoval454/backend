package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.dto.request.CrearEtiquetaRequest;
import co.edu.usbcali.jasan.java.dto.response.ObtenerEtiquetaResponse;

import java.util.List;

public interface EtiquetaService {
    List<ObtenerEtiquetaResponse> obtenerEtiquetas();
    ObtenerEtiquetaResponse obtenerEtiquetaPorId(Integer id) throws Exception;
    ObtenerEtiquetaResponse crearEtiqueta(CrearEtiquetaRequest crearEtiqueta) throws Exception;
}
