package co.edu.usbcali.jasan.java.controller;

import co.edu.usbcali.jasan.java.dto.request.CrearEtiquetaRequest;
import co.edu.usbcali.jasan.java.dto.response.ObtenerEtiquetaResponse;
import co.edu.usbcali.jasan.java.service.EtiquetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/etiquetas")
public class EtiquetaController {

    // Inyección de dependencias hacia el Service
    @Autowired
    private EtiquetaService etiquetaService;

    @GetMapping("/ping")
    String pingPong() {
        return "pong";
    }

    @GetMapping("/validar-estado")
    String validarEstado() {
        return "ok";
    }

    @GetMapping("/obtener-etiquetas")
    List<ObtenerEtiquetaResponse> obtenerEtiquetas() {
        return etiquetaService.obtenerEtiquetas();
    }

    @GetMapping("/{id}")
    ResponseEntity<ObtenerEtiquetaResponse> obtenerEtiquetaPorId(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(etiquetaService.obtenerEtiquetaPorId(id));
    }

    @PostMapping
    ResponseEntity<ObtenerEtiquetaResponse> crearEtiqueta(@RequestBody CrearEtiquetaRequest crearEtiqueta)
            throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(etiquetaService.crearEtiqueta(crearEtiqueta));
    }

    @PostMapping("/crear")
    ResponseEntity<ObtenerEtiquetaResponse> crearEtiquetaCompatibilidad(
            @RequestBody CrearEtiquetaRequest crearEtiqueta) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(etiquetaService.crearEtiqueta(crearEtiqueta));
    }
}
