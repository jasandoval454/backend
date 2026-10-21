package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.dto.request.CrearPublicacionRequest;
import co.edu.usbcali.jasan.java.domain.Publicacion;
import co.edu.usbcali.jasan.java.domain.enums.PrivacidadPublicacion;
import co.edu.usbcali.jasan.java.domain.repository.PublicacionRepository;
import co.edu.usbcali.jasan.java.dto.request.ModificarPublicacionRequest;
import co.edu.usbcali.jasan.java.dto.response.ObtenerPublicacionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublicacionServiceImplTest {

    @Mock
    private PublicacionRepository publicacionRepository;

    @InjectMocks
    private PublicacionServiceImpl publicacionService;

    private Publicacion publicacion;

    @BeforeEach
    void setUp() {
        publicacion = Publicacion.builder()
                .id(1)
                .contenido("Contenido anterior")
                .privacidad(PrivacidadPublicacion.PUBLICO)
                .build();
    }

    @Test
    void crearPublicacionRechazaContenidoNulo() {
        Exception exception = assertThrows(Exception.class, () ->
                publicacionService.crearPublicacion(new CrearPublicacionRequest(1, null, "publico")));

        assertEquals("El contenido de la publicación no puede ser nulo ni vacío", exception.getMessage());
    }

    @Test
    void crearPublicacionRechazaContenidoEnBlanco() {
        Exception exception = assertThrows(Exception.class, () ->
                publicacionService.crearPublicacion(new CrearPublicacionRequest(1, "   ", "publico")));

        assertEquals("El contenido de la publicación no puede ser nulo ni vacío", exception.getMessage());
    }

    @Test
    void modificarPublicacionActualizaContenido() throws Exception {
        when(publicacionRepository.findByIdAndDeletedAtIsNull(1)).thenReturn(Optional.of(publicacion));
        when(publicacionRepository.save(publicacion)).thenReturn(publicacion);

        ObtenerPublicacionResponse response = publicacionService.modificarPublicacion(
                1,
                new ModificarPublicacionRequest("Contenido actualizado", null)
        );

        assertEquals("Contenido actualizado", response.contenido());
        verify(publicacionRepository).save(publicacion);
    }

    @Test
    void eliminarPublicacionMarcaEliminacionLogica() throws Exception {
        when(publicacionRepository.findByIdAndDeletedAtIsNull(1)).thenReturn(Optional.of(publicacion));
        when(publicacionRepository.save(publicacion)).thenReturn(publicacion);

        publicacionService.eliminarPublicacion(1);

        assertNotNull(publicacion.getDeletedAt());
        verify(publicacionRepository).save(publicacion);
    }
}
