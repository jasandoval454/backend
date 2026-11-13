package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.domain.Medio;
import co.edu.usbcali.jasan.java.domain.Publicacion;
import co.edu.usbcali.jasan.java.domain.enums.TipoMedio;
import co.edu.usbcali.jasan.java.domain.repository.MedioRepository;
import co.edu.usbcali.jasan.java.domain.repository.PublicacionRepository;
import co.edu.usbcali.jasan.java.dto.request.CrearMedioRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MedioServiceImpl implements MedioService {
    private final MedioRepository medioRepository;
    private final PublicacionRepository publicacionRepository;

    public MedioServiceImpl(MedioRepository medioRepository, PublicacionRepository publicacionRepository) {
        this.medioRepository = medioRepository;
        this.publicacionRepository = publicacionRepository;
    }

    @Override
    @Transactional
    public Medio crearMedio(CrearMedioRequest request) {
        if (request == null || request.publicacionId() == null || request.publicacionId() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El id de la publicación debe ser mayor que cero");
        }
        String urlOriginal = request.urlOriginal() == null ? null : request.urlOriginal().trim();
        if (urlOriginal == null || urlOriginal.isEmpty() || urlOriginal.length() > 500) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La URL original es obligatoria y no puede superar los 500 caracteres");
        }
        validarLongitud(request.urlThumbnail(), 500, "La URL thumbnail");
        validarLongitud(request.formato(), 20, "El formato");
        if (request.tamanoBytes() != null && request.tamanoBytes() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tamaño no puede ser negativo");
        }
        if (request.orden() != null && request.orden() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El orden no puede ser negativo");
        }

        TipoMedio tipo = request.tipo() == null || request.tipo().isBlank()
                ? TipoMedio.IMAGEN
                : TipoMedio.getTipoMedio(request.tipo().trim());
        if (tipo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tipo debe ser 'imagen' o 'video'");
        }
        Publicacion publicacion = publicacionRepository.findByIdAndDeletedAtIsNull(request.publicacionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se encontró la publicación con el id: " + request.publicacionId()));

        Medio medio = Medio.builder()
                .publicacion(publicacion)
                .tipo(tipo)
                .url(urlOriginal)
                .urlThumbnail(limpiarOpcional(request.urlThumbnail()))
                .formato(limpiarOpcional(request.formato()))
                .tamanoBytes(request.tamanoBytes())
                .orden(request.orden() == null ? (short) 0 : request.orden())
                .build();
        return medioRepository.save(medio);
    }

    private void validarLongitud(String valor, int maximo, String campo) {
        if (valor != null && valor.trim().length() > maximo) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    campo + " no puede superar los " + maximo + " caracteres");
        }
    }

    private String limpiarOpcional(String valor) {
        if (valor == null) return null;
        String limpio = valor.trim();
        return limpio.isEmpty() ? null : limpio;
    }
}
