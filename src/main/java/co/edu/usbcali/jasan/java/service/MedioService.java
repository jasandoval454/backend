package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.domain.Medio;
import co.edu.usbcali.jasan.java.dto.request.CrearMedioRequest;

public interface MedioService {
    Medio crearMedio(CrearMedioRequest request);
}
