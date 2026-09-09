package co.edu.usbcali.jasan.java.controller;

import co.edu.usbcali.jasan.java.domain.Denuncia;
import co.edu.usbcali.jasan.java.domain.repository.DenunciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/denuncias")
public class DenunciaController {

    @Autowired
    private DenunciaRepository denunciaRepository;

    @GetMapping("/ping")
    String pingpong() {
        return "pong";
    }

    @GetMapping("/validar-stado")
    String validarEstado() {
        return "ok";
    }

    @GetMapping("/obtener-denuncias")
    List<Denuncia> obtenerDenuncias() {
        return denunciaRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<Denuncia> obtenerDenunciaPorId(@PathVariable Integer id) {
        return denunciaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
