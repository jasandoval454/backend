package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.domain.Perfil;
import co.edu.usbcali.jasan.java.domain.Usuario;
import co.edu.usbcali.jasan.java.domain.enums.VisibilidadPerfil;
import co.edu.usbcali.jasan.java.domain.repository.PerfilRepository;
import co.edu.usbcali.jasan.java.domain.repository.UsuarioRepository;
import co.edu.usbcali.jasan.java.dto.request.CrearPerfilRequest;
import co.edu.usbcali.jasan.java.dto.request.ModificarPerfilRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PerfilServiceImpl implements PerfilService {
    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;

    public PerfilServiceImpl(PerfilRepository perfilRepository, UsuarioRepository usuarioRepository) {
        this.perfilRepository = perfilRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Perfil> obtenerPerfiles() {
        return perfilRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Perfil obtenerPerfilPorId(Integer id) {
        validarId(id);
        return perfilRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se ha encontrado el perfil con el id: " + id));
    }

    @Override
    @Transactional
    public Perfil crearPerfil(CrearPerfilRequest request) {
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La solicitud no puede ser nula");
        }
        if (request.usuarioId() == null || request.usuarioId() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El id del usuario debe ser mayor que cero");
        }
        validarNombre(request.nombre());
        validarCampos(request.bio(), request.avatarUrl(), request.ubicacion(), request.enlace());

        VisibilidadPerfil visibilidad = convertirVisibilidad(request.visibilidad(), true);
        if (perfilRepository.existsByUsuario_Id(request.usuarioId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya tiene un perfil creado");
        }

        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se ha encontrado el usuario con el id: " + request.usuarioId()));
        Perfil perfil = Perfil.builder()
                .usuario(usuario)
                .nombre(request.nombre().trim())
                .bio(limpiarOpcional(request.bio()))
                .avatarUrl(limpiarOpcional(request.avatarUrl()))
                .ubicacion(limpiarOpcional(request.ubicacion()))
                .enlace(limpiarOpcional(request.enlace()))
                .visibilidad(visibilidad)
                .build();
        return perfilRepository.save(perfil);
    }

    @Override
    @Transactional
    public Perfil modificarPerfil(Integer id, ModificarPerfilRequest request) {
        validarId(id);
        if (request == null || (request.nombre() == null && request.bio() == null
                && request.avatarUrl() == null && request.ubicacion() == null
                && request.enlace() == null && request.visibilidad() == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Debe indicar al menos un campo para modificar");
        }

        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se ha encontrado el perfil con el id: " + id));

        validarCampos(request.bio(), request.avatarUrl(), request.ubicacion(), request.enlace());
        if (request.nombre() != null) {
            validarNombre(request.nombre());
            perfil.setNombre(request.nombre().trim());
        }
        if (request.bio() != null) perfil.setBio(limpiarOpcional(request.bio()));
        if (request.avatarUrl() != null) perfil.setAvatarUrl(limpiarOpcional(request.avatarUrl()));
        if (request.ubicacion() != null) perfil.setUbicacion(limpiarOpcional(request.ubicacion()));
        if (request.enlace() != null) perfil.setEnlace(limpiarOpcional(request.enlace()));
        if (request.visibilidad() != null) {
            perfil.setVisibilidad(convertirVisibilidad(request.visibilidad(), false));
        }
        return perfilRepository.save(perfil);
    }

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El id debe ser mayor que cero");
        }
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank() || nombre.trim().length() > 150) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El nombre es obligatorio y no puede superar los 150 caracteres");
        }
    }

    private void validarCampos(String bio, String avatarUrl, String ubicacion, String enlace) {
        validarLongitud(bio, 500, "La biografía");
        validarLongitud(avatarUrl, 500, "La URL del avatar");
        validarLongitud(ubicacion, 150, "La ubicación");
        validarLongitud(enlace, 255, "El enlace");
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

    private VisibilidadPerfil convertirVisibilidad(String valor, boolean permitirNula) {
        if (valor == null || valor.isBlank()) {
            if (permitirNula) return VisibilidadPerfil.PUBLICO;
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La visibilidad no puede estar vacía");
        }
        VisibilidadPerfil visibilidad = VisibilidadPerfil.getVisibilidadPerfil(valor.trim());
        if (visibilidad == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La visibilidad debe ser 'publico' o 'privado'");
        }
        return visibilidad;
    }
}
