package co.edu.usbcali.jasan.java.controller;

import co.edu.usbcali.jasan.java.domain.Medio;
import co.edu.usbcali.jasan.java.dto.request.CrearMedioRequest;
import co.edu.usbcali.jasan.java.service.MedioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.usbcali.jasan.java.domain.repository.MedioRepository;

import java.util.List;

@RestController
@RequestMapping("/medios")
public class MedioController {

    @Autowired
    private MedioRepository medioRepository;

    @Autowired
    private MedioService medioService;

    @GetMapping("/ping")
    String pingpong() {
        return "pong";
    }

    @GetMapping({"/validar-estado", "/validar-stado"})
    String validarEstado() {
        return "ok";
    }


    @GetMapping("/obtener-medios")
    List<Medio> obtenerMedios() {
        return medioRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<Medio> obtenerMedioPorId(@PathVariable Integer id) {
        return medioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<Medio> crearMedio(@RequestBody CrearMedioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medioService.crearMedio(request));
    }
}
