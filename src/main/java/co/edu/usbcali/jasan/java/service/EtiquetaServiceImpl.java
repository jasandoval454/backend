package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.domain.Etiqueta;
import co.edu.usbcali.jasan.java.dto.request.CrearEtiquetaRequest;
import co.edu.usbcali.jasan.java.dto.response.ObtenerEtiquetaResponse;
import co.edu.usbcali.jasan.java.mapper.EtiquetaMapper;
import co.edu.usbcali.jasan.java.domain.repository.EtiquetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EtiquetaServiceImpl implements EtiquetaService{
    @Autowired
    private EtiquetaRepository etiquetaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ObtenerEtiquetaResponse> obtenerEtiquetas() {
        List<Etiqueta> todasLasEtiquetas = etiquetaRepository.findAll();
        List<ObtenerEtiquetaResponse> etiquetasResponses =
                EtiquetaMapper.listaEtiquetasHaciaListaObtenerEtiquetasResponse(todasLasEtiquetas);
        return etiquetasResponses;
    }

    @Override
    @Transactional(readOnly = true)
    public ObtenerEtiquetaResponse obtenerEtiquetaPorId(Integer id) throws Exception {
        // Validar que id no sea nulo
        if (id == null) {
            throw new Exception("El id no puede ser nulo");
        }
        // Validar que id no tenga valor inferior a cero
        if (id <= 0) {
            throw new Exception("El valor del id no puede ser inferior o igual a cero para buscar");
        }

        // Buscar la Etiqueta por medio de un Optional en la base de datos usando Repository
        Optional<Etiqueta> etiquetaOptional = etiquetaRepository.findById(id);

        // Si la etiqueta no fue encontrada, lanzar la excepción
        if (etiquetaOptional.isEmpty()) {
            throw new Exception("No se ha encontrado la Etiqueta con el id: "+id);
        }

        // Si la etiqueta fue encontrada, se debe mapear hacia el Response usando el Mapper
        ObtenerEtiquetaResponse etiquetaResponse =
                EtiquetaMapper.etiquetaAObtenerEtiquetaResponse(etiquetaOptional.get());

        // Retornar el record (objeto inmutable) ObtenerEtiquetaResponse
        return etiquetaResponse;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ObtenerEtiquetaResponse crearEtiqueta(CrearEtiquetaRequest crearEtiqueta) throws Exception {
        if (crearEtiqueta == null || crearEtiqueta.nombre() == null || crearEtiqueta.nombre().isBlank()) {
            throw new Exception("El nombre de la etiqueta es obligatorio");
        }

        String nombre = crearEtiqueta.nombre().trim();
        if (nombre.length() > 100) {
            throw new Exception("El nombre de la etiqueta no puede superar los 100 caracteres");
        }
        if (etiquetaRepository.existsByNombre(nombre)) {
            throw new Exception("El nombre de la etiqueta ya existe");
        }

        Etiqueta etiquetaGuardada = etiquetaRepository.save(
                EtiquetaMapper.crearEtiquetaRequestAEtiqueta(new CrearEtiquetaRequest(nombre))
        );
        return EtiquetaMapper.etiquetaAObtenerEtiquetaResponse(etiquetaGuardada);
    }
}
