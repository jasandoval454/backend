package co.edu.usbcali.jasan.java.controller;

import co.edu.usbcali.jasan.java.domain.Reaccion;
import co.edu.usbcali.jasan.java.domain.repository.ReaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reacciones")
public class ReaccionController {

    @Autowired
    private ReaccionRepository reaccionRepository;

    @GetMapping("/ping")
    String pingpong() {
        return "pong";
    }

    @GetMapping("/validar-stado")
    String validarEstado() {
        return "ok";
    }

    @GetMapping("/obtener-reacciones")
    List<Reaccion> obtenerReacciones() {
        return reaccionRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<Reaccion> obtenerReaccionPorId(@PathVariable Integer id) {
        return reaccionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
