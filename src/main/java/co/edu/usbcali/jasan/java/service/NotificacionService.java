package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.domain.Notificacion;
import co.edu.usbcali.jasan.java.dto.request.CrearNotificacionRequest;

public interface NotificacionService {
    Notificacion crearNotificacion(CrearNotificacionRequest request);
}
