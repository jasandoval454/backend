package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.domain.Usuario;
import co.edu.usbcali.jasan.java.domain.repository.UsuarioRepository;
import co.edu.usbcali.jasan.java.dto.request.CrearUsuarioRequest;
import co.edu.usbcali.jasan.java.dto.request.IniciarSesionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    void crearUsuarioNormalizaEmailYGuardaContrasenaCodificada() {
        when(usuarioRepository.existsByEmailIgnoreCase("ana@example.com")).thenReturn(false);
        when(usuarioRepository.existsByUsernameIgnoreCase("ana")).thenReturn(false);
        when(passwordEncoder.encode("ContrasenaSegura1")).thenReturn("$2a$hash");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario usuario = invocation.getArgument(0);
            usuario.setId(1);
            return usuario;
        });

        var response = usuarioService.crearUsuario(
                new CrearUsuarioRequest(" Ana@Example.com ", "ana", "ContrasenaSegura1"));

        assertEquals(1, response.id());
        assertEquals("ana@example.com", response.email());
        verify(passwordEncoder).encode("ContrasenaSegura1");
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void crearUsuarioRechazaEmailDuplicado() {
        when(usuarioRepository.existsByEmailIgnoreCase("ana@example.com")).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> usuarioService.crearUsuario(
                        new CrearUsuarioRequest("ana@example.com", "ana", "ContrasenaSegura1")));

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
    }

    @Test
    void crearUsuarioRechazaContrasenaCorta() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> usuarioService.crearUsuario(
                        new CrearUsuarioRequest("ana@example.com", "ana", "corta")));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void iniciarSesionPorEmailActualizaUltimoIngreso() {
        Usuario usuario = Usuario.builder()
                .id(1)
                .email("ana@example.com")
                .username("ana")
                .passwordHash("$2a$hash")
                .build();
        when(usuarioRepository.findByEmailIgnoreCase("ana@example.com"))
                .thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("ContrasenaSegura1", "$2a$hash")).thenReturn(true);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = usuarioService.iniciarSesion(
                new IniciarSesionRequest("ana@example.com", "ContrasenaSegura1"));

        assertNotNull(response.token());
        assertEquals(1, response.usuario().id());
        assertNotNull(usuario.getLastLogin());
    }

    @Test
    void iniciarSesionRechazaContraseñaIncorrecta() {
        Usuario usuario = Usuario.builder()
                .id(1)
                .email("ana@example.com")
                .username("ana")
                .passwordHash("$2a$hash")
                .build();
        when(usuarioRepository.findByEmailIgnoreCase("ana@example.com"))
                .thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("incorrecta", "$2a$hash")).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> usuarioService.iniciarSesion(
                        new IniciarSesionRequest("ana@example.com", "incorrecta")));

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
    }
}
