package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.domain.Publicacion;
import co.edu.usbcali.jasan.java.domain.repository.MedioRepository;
import co.edu.usbcali.jasan.java.domain.repository.PublicacionRepository;
import co.edu.usbcali.jasan.java.dto.request.CrearMedioRequest;
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
class MedioServiceImplTest {
    @Mock
    private MedioRepository medioRepository;

    @Mock
    private PublicacionRepository publicacionRepository;

    @InjectMocks
    private MedioServiceImpl medioService;

    @Test
    void crearMedioAplicaTipoYOrdenPorDefecto() {
        Publicacion publicacion = Publicacion.builder().id(1).build();
        when(publicacionRepository.findByIdAndDeletedAtIsNull(1)).thenReturn(Optional.of(publicacion));
        when(medioRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var medio = medioService.crearMedio(new CrearMedioRequest(1, null, " https://example.com/image.jpg ",
                null, null, null, null));

        assertEquals(publicacion, medio.getPublicacion());
        assertEquals("https://example.com/image.jpg", medio.getUrl());
        assertEquals("IMAGEN", medio.getTipo().name());
        assertEquals((short) 0, medio.getOrden());
        verify(medioRepository).save(medio);
    }
}
