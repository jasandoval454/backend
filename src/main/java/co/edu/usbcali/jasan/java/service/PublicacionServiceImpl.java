package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.domain.Publicacion;
import co.edu.usbcali.jasan.java.domain.Usuario;
import co.edu.usbcali.jasan.java.domain.enums.PrivacidadPublicacion;
import co.edu.usbcali.jasan.java.domain.repository.PublicacionRepository;
import co.edu.usbcali.jasan.java.domain.repository.UsuarioRepository;
import co.edu.usbcali.jasan.java.dto.request.CrearPublicacionRequest;
import co.edu.usbcali.jasan.java.dto.response.ObtenerPublicacionResponse;
import co.edu.usbcali.jasan.java.mapper.PublicacionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PublicacionServiceImpl implements PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ObtenerPublicacionResponse> obtenerPublicaciones() {
        return PublicacionMapper.listaPublicacionesHaciaListaObtenerPublicacionesResponse(
                publicacionRepository.findAll()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ObtenerPublicacionResponse obtenerPublicacionPorId(Integer id) throws Exception {
        if (id == null) {
            throw new Exception("El id no puede ser nulo");
        }
        if (id <= 0) {
            throw new Exception("El valor del id no puede ser inferior o igual a cero para buscar");
        }

        Optional<Publicacion> publicacionOptional = publicacionRepository.findById(id);
        if (publicacionOptional.isEmpty()) {
            throw new Exception("No existe la publicación");
        }

        return PublicacionMapper.publicacionAObtenerPublicacionResponse(publicacionOptional.get());
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ObtenerPublicacionResponse crearPublicacion(CrearPublicacionRequest crearPublicacionRequest) throws Exception {
        if (crearPublicacionRequest == null) {
            throw new Exception("La petición de creación no puede ser nula");
        }

        if (crearPublicacionRequest.autorId() == null) {
            throw new Exception("El id del autor no puede ser nulo");
        }

        if (crearPublicacionRequest.autorId() <= 0) {
            throw new Exception("El id del autor debe ser mayor que cero");
        }

        if (crearPublicacionRequest.privacidadPublicacion() == null
                || crearPublicacionRequest.privacidadPublicacion().isBlank()) {
            throw new Exception("La privacidad de la publicación no puede ser nula o vacía");
        }

        Usuario autor = usuarioRepository.findById(crearPublicacionRequest.autorId())
                .orElseThrow(() -> new Exception(
                        "No se encontró el autor con el id: " + crearPublicacionRequest.autorId() + "."));

        if (PrivacidadPublicacion.getPrivacidadPublicacion(crearPublicacionRequest.privacidadPublicacion()) == null) {
            throw new Exception("No se ha encontrado la privacidad-publicacion: "
                    + crearPublicacionRequest.privacidadPublicacion() + ".");
        }

        Publicacion publicacion = PublicacionMapper.crearPublicacionRequestAPublicacion(crearPublicacionRequest);
        publicacion.setAutor(autor);

        publicacion = publicacionRepository.save(publicacion);

        return PublicacionMapper.publicacionAObtenerPublicacionResponse(publicacion);
    }
}
