package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.domain.Usuario;
import co.edu.usbcali.jasan.java.domain.repository.UsuarioRepository;
import co.edu.usbcali.jasan.java.dto.request.CrearUsuarioRequest;
import co.edu.usbcali.jasan.java.dto.response.ObtenerUsuarioResponse;
import co.edu.usbcali.jasan.java.mapper.UsuarioMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ObtenerUsuarioResponse> obtenerUsuarios() {
        return UsuarioMapper.listaUsuariosHaciaListaObtenerUsuariosResponse(usuarioRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public ObtenerUsuarioResponse obtenerUsuarioPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El id debe ser mayor que cero");
        }

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se ha encontrado el usuario con el id: " + id));
        return UsuarioMapper.usuarioAObtenerUsuarioResponse(usuario);
    }

    @Override
    @Transactional
    public ObtenerUsuarioResponse crearUsuario(CrearUsuarioRequest request) {
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La solicitud no puede ser nula");
        }

        String email = request.email() == null ? null : request.email().trim().toLowerCase(Locale.ROOT);
        String username = request.username() == null ? null : request.username().trim();
        String password = request.password();

        if (email == null || email.isBlank() || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")
                || email.length() > 150) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email no es válido");
        }
        if (username == null || username.isBlank() || username.length() > 50) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El username es obligatorio y no puede superar los 50 caracteres");
        }
        if (password == null || password.length() < 8
                || password.getBytes(StandardCharsets.UTF_8).length > 72) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La contraseña debe tener al menos 8 caracteres y no superar 72 bytes UTF-8");
        }
        if (usuarioRepository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya se encuentra registrado");
        }
        if (usuarioRepository.existsByUsernameIgnoreCase(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El username ya se encuentra registrado");
        }

        Usuario usuario = Usuario.builder()
                .email(email)
                .username(username)
                .passwordHash(passwordEncoder.encode(password))
                .build();
        return UsuarioMapper.usuarioAObtenerUsuarioResponse(usuarioRepository.save(usuario));
    }
}
