package co.edu.usbcali.jasan.java.mapper;

import co.edu.usbcali.jasan.java.domain.Etiqueta;
import co.edu.usbcali.jasan.java.dto.request.CrearEtiquetaRequest;
import co.edu.usbcali.jasan.java.dto.response.ObtenerEtiquetaResponse;

import java.util.List;

public class EtiquetaMapper {

    public static ObtenerEtiquetaResponse etiquetaAObtenerEtiquetaResponse(Etiqueta etiqueta) {
        return new ObtenerEtiquetaResponse(
                etiqueta.getId(), etiqueta.getNombre()
        );
    }

    public static List<ObtenerEtiquetaResponse> listaEtiquetasHaciaListaObtenerEtiquetasResponse(List<Etiqueta> etiquetas) {
        return etiquetas.stream().map(EtiquetaMapper::etiquetaAObtenerEtiquetaResponse).toList();
    }

    public static Etiqueta crearEtiquetaRequestAEtiqueta(CrearEtiquetaRequest etiquetaRequest) {
        return Etiqueta.builder()
                .nombre(etiquetaRequest.nombre())
                .build();
    }
}
