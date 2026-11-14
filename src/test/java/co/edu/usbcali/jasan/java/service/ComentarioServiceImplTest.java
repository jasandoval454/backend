package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.domain.Comentario;
import co.edu.usbcali.jasan.java.domain.Publicacion;
import co.edu.usbcali.jasan.java.domain.Usuario;
import co.edu.usbcali.jasan.java.domain.repository.ComentarioRepository;
import co.edu.usbcali.jasan.java.domain.repository.PublicacionRepository;
import co.edu.usbcali.jasan.java.domain.repository.UsuarioRepository;
import co.edu.usbcali.jasan.java.dto.request.CrearComentarioRequest;
import co.edu.usbcali.jasan.java.dto.request.ModificarComentarioRequest;
import co.edu.usbcali.jasan.java.dto.response.ObtenerComentarioResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ComentarioServiceImplTest {

    @Mock
    private ComentarioRepository comentarioRepository;

    @Mock
    private PublicacionRepository publicacionRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ComentarioServiceImpl comentarioService;

    @Test
    void crearComentarioGuardaAutorPublicacionYContenidoNormalizado() throws Exception {
        Publicacion publicacion = Publicacion.builder().id(10).build();
        Usuario autor = Usuario.builder().id(20).build();
        LocalDateTime fecha = LocalDateTime.now();

        when(publicacionRepository.findByIdAndDeletedAtIsNull(10)).thenReturn(Optional.of(publicacion));
        when(usuarioRepository.findById(20)).thenReturn(Optional.of(autor));
        when(comentarioRepository.save(org.mockito.ArgumentMatchers.any(Comentario.class)))
                .thenAnswer(invocation -> {
                    Comentario comentario = invocation.getArgument(0);
                    comentario.setId(30);
                    comentario.setCreatedAt(fecha);
                    comentario.setUpdatedAt(fecha);
                    return comentario;
                });

        ObtenerComentarioResponse response = comentarioService.crearComentario(
                new CrearComentarioRequest(10, 20, null, "  Buen aporte  ")
        );

        assertEquals(30, response.id());
        assertEquals(10, response.publicacionId());
        assertEquals(20, response.autorId());
        assertEquals("Buen aporte", response.contenido());
        verify(comentarioRepository).save(org.mockito.ArgumentMatchers.any(Comentario.class));
    }

    @Test
    void crearComentarioRechazaComentarioPadreDeOtraPublicacion() throws Exception {
        when(publicacionRepository.findByIdAndDeletedAtIsNull(10))
                .thenReturn(Optional.of(Publicacion.builder().id(10).build()));
        when(usuarioRepository.findById(20)).thenReturn(Optional.of(Usuario.builder().id(20).build()));
        when(comentarioRepository.findByIdAndPublicacion_IdAndDeletedAtIsNull(5, 10))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () ->
                comentarioService.crearComentario(
                        new CrearComentarioRequest(10, 20, 5, "Respuesta")
                ));

        assertEquals("El comentario padre no existe en la publicación indicada", exception.getMessage());
        verify(comentarioRepository, never()).save(org.mockito.ArgumentMatchers.any(Comentario.class));
    }

    @Test
    void modificarComentarioActualizaContenido() throws Exception {
        LocalDateTime fecha = LocalDateTime.now();
        Comentario comentario = Comentario.builder()
                .id(30)
                .publicacion(Publicacion.builder().id(10).build())
                .autor(Usuario.builder().id(20).build())
                .contenido("Contenido anterior")
                .createdAt(fecha)
                .updatedAt(fecha)
                .build();
        when(comentarioRepository.findByIdAndDeletedAtIsNull(30))
                .thenReturn(Optional.of(comentario));
        when(comentarioRepository.save(comentario)).thenReturn(comentario);

        ObtenerComentarioResponse response = comentarioService.modificarComentario(
                30,
                new ModificarComentarioRequest("  Contenido actualizado  ")
        );

        assertEquals("Contenido actualizado", response.contenido());
        verify(comentarioRepository).save(comentario);
    }

    @Test
    void modificarComentarioRechazaContenidoVacio() {
        Exception exception = assertThrows(Exception.class, () ->
                comentarioService.modificarComentario(
                        30,
                        new ModificarComentarioRequest("   ")
                ));

        assertEquals("El contenido del comentario no puede ser vacío", exception.getMessage());
        verify(comentarioRepository, never()).save(org.mockito.ArgumentMatchers.any(Comentario.class));
    }

    @Test
    void modificarComentarioRechazaComentarioInexistente() {
        when(comentarioRepository.findByIdAndDeletedAtIsNull(30)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () ->
                comentarioService.modificarComentario(
                        30,
                        new ModificarComentarioRequest("Contenido actualizado")
                ));

        assertEquals("No existe el comentario con el id: 30", exception.getMessage());
        verify(comentarioRepository, never()).save(org.mockito.ArgumentMatchers.any(Comentario.class));
    }
}
