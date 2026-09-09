package co.edu.usbcali.jasan.java.controller;

import co.edu.usbcali.jasan.java.domain.Notificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.usbcali.jasan.java.domain.repository.NotificacionRepository;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @GetMapping("/ping")
    String pingpong() {
        return "pong";
    }

    @GetMapping("/validar-stado")
    String validarEstado() {
        return "ok";
    }


    @GetMapping("/obtener-notificaciones")
    List<Notificacion> obtenerNotificaciones() {
        return notificacionRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<Notificacion> obtenerNotificacionPorId(@PathVariable Integer id) {
        return notificacionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
