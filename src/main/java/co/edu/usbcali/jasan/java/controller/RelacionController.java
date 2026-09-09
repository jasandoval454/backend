package co.edu.usbcali.jasan.java.controller;

import co.edu.usbcali.jasan.java.domain.Relacion;
import co.edu.usbcali.jasan.java.domain.repository.RelacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/relaciones")
public class RelacionController {

    @Autowired
    private RelacionRepository relacionRepository;

    @GetMapping("/ping")
    String pingpong() {
        return "pong";
    }

    @GetMapping("/validar-stado")
    String validarEstado() {
        return "ok";
    }

    @GetMapping("/obtener-relaciones")
    List<Relacion> obtenerRelaciones() {
        return relacionRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<Relacion> obtenerRelacionPorId(@PathVariable Integer id) {
        return relacionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
