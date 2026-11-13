package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.domain.Perfil;
import co.edu.usbcali.jasan.java.domain.Usuario;
import co.edu.usbcali.jasan.java.domain.enums.VisibilidadPerfil;
import co.edu.usbcali.jasan.java.domain.repository.PerfilRepository;
import co.edu.usbcali.jasan.java.domain.repository.UsuarioRepository;
import co.edu.usbcali.jasan.java.dto.request.CrearPerfilRequest;
import co.edu.usbcali.jasan.java.dto.request.ModificarPerfilRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PerfilServiceImplTest {
    @Mock
    private PerfilRepository perfilRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private PerfilServiceImpl perfilService;

    @Test
    void crearPerfilUsaVisibilidadPublicaPorDefecto() {
        Usuario usuario = Usuario.builder().id(1).build();
        when(perfilRepository.existsByUsuario_Id(1)).thenReturn(false);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(perfilRepository.save(any(Perfil.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Perfil perfil = perfilService.crearPerfil(new CrearPerfilRequest(1, " Ana ", null, null, null, null, null));

        assertEquals("Ana", perfil.getNombre());
        assertEquals(VisibilidadPerfil.PUBLICO, perfil.getVisibilidad());
        assertEquals(usuario, perfil.getUsuario());
        verify(perfilRepository).save(perfil);
    }

    @Test
    void modificarPerfilActualizaPrivacidad() {
        Perfil perfil = Perfil.builder().id(1).visibilidad(VisibilidadPerfil.PUBLICO).build();
        when(perfilRepository.findById(1)).thenReturn(Optional.of(perfil));
        when(perfilRepository.save(perfil)).thenReturn(perfil);

        Perfil actualizado = perfilService.modificarPerfil(1,
                new ModificarPerfilRequest(null, null, null, null, null, "privado"));

        assertEquals(VisibilidadPerfil.PRIVADO, actualizado.getVisibilidad());
        verify(perfilRepository).save(perfil);
    }
}
