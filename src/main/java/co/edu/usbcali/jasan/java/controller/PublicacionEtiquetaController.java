package co.edu.usbcali.jasan.java.controller;

import co.edu.usbcali.jasan.java.domain.PublicacionEtiqueta;
import co.edu.usbcali.jasan.java.domain.repository.PublicacionEtiquetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/publicaciones-etiquetas")
public class PublicacionEtiquetaController {

    @Autowired
    private PublicacionEtiquetaRepository publicacionEtiquetaRepository;

    @GetMapping("/ping")
    String pingpong() {
        return "pong";
    }

    @GetMapping("/validar-stado")
    String validarEstado() {
        return "ok";
    }

    @GetMapping("/obtener-publicaciones-etiquetas")
    List<PublicacionEtiqueta> obtenerPublicacionesEtiquetas() {
        return publicacionEtiquetaRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<PublicacionEtiqueta> obtenerPublicacionEtiquetaPorId(@PathVariable Integer id) {
        return publicacionEtiquetaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
