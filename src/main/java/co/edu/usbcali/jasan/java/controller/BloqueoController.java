package co.edu.usbcali.jasan.java.controller;

import co.edu.usbcali.jasan.java.domain.Bloqueo;
import co.edu.usbcali.jasan.java.domain.repository.BloqueoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bloqueos")
public class BloqueoController {

    @Autowired
    private BloqueoRepository bloqueoRepository;

    @GetMapping("/ping")
    String pingpong() {
        return "pong";
    }

    @GetMapping("/validar-stado")
    String validarEstado() {
        return "ok";
    }

    @GetMapping("/obtener-bloqueos")
    List<Bloqueo> obtenerBloqueos() {
        return bloqueoRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<Bloqueo> obtenerBloqueoPorId(@PathVariable Integer id) {
        return bloqueoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}