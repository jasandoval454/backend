package co.edu.usbcali.jasan.java.controller;

import co.edu.usbcali.jasan.java.dto.request.CrearPublicacionRequest;
import co.edu.usbcali.jasan.java.dto.response.ObtenerPublicacionResponse;
import co.edu.usbcali.jasan.java.service.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @GetMapping
    public List<ObtenerPublicacionResponse> obtenerPublicaciones() {
        return publicacionService.obtenerPublicaciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObtenerPublicacionResponse> obtenerPublicacionPorId(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(publicacionService.obtenerPublicacionPorId(id));
    }

    @PostMapping
    public ResponseEntity<ObtenerPublicacionResponse> crearPublicacion(@RequestBody CrearPublicacionRequest crearPublicacionRequest) throws Exception {
        return ResponseEntity.ok(publicacionService.crearPublicacion(crearPublicacionRequest));
    }
}
