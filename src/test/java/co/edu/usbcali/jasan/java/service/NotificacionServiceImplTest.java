package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.domain.Usuario;
import co.edu.usbcali.jasan.java.domain.repository.ComentarioRepository;
import co.edu.usbcali.jasan.java.domain.repository.NotificacionRepository;
import co.edu.usbcali.jasan.java.domain.repository.PublicacionRepository;
import co.edu.usbcali.jasan.java.domain.repository.UsuarioRepository;
import co.edu.usbcali.jasan.java.dto.request.CrearNotificacionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class NotificacionServiceImplTest {
    @Mock
    private NotificacionRepository notificacionRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PublicacionRepository publicacionRepository;

    @Mock
    private ComentarioRepository comentarioRepository;

    @InjectMocks
    private NotificacionServiceImpl notificacionService;

    @Test
    void crearNotificacionGuardaConDestinatarioYSinLeer() {
        Usuario destinatario = Usuario.builder().id(1).build();
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(destinatario));
        when(notificacionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var notificacion = notificacionService.crearNotificacion(
                new CrearNotificacionRequest(1, "nuevo_seguidor", null, null, null));

        assertEquals(destinatario, notificacion.getDestinatario());
        assertEquals("NUEVO_SEGUIDOR", notificacion.getTipo().name());
        assertFalse(notificacion.getLeida());
        verify(notificacionRepository).save(notificacion);
    }
}
