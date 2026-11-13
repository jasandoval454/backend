package co.edu.usbcali.jasan.java.controller;

import co.edu.usbcali.jasan.java.domain.Perfil;
import co.edu.usbcali.jasan.java.dto.request.CrearPerfilRequest;
import co.edu.usbcali.jasan.java.dto.request.ModificarPerfilRequest;
import co.edu.usbcali.jasan.java.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/perfiles")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @GetMapping("/ping")
    String pingpong() {
        return "pong";
    }

    @GetMapping("/validar-stado")
    String validarEstado() {
        return "ok";
    }


    @GetMapping("/obtener-perfiles")
    List<Perfil> obtenerPerfiles() {
        return perfilService.obtenerPerfiles();
    }

    @GetMapping("/{id}")
    ResponseEntity<Perfil> obtenerPerfilPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(perfilService.obtenerPerfilPorId(id));
    }

    @PostMapping({"", "/crear"})
    ResponseEntity<Perfil> crearPerfil(@RequestBody CrearPerfilRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilService.crearPerfil(request));
    }

    @PutMapping("/{id}")
    ResponseEntity<Perfil> modificarPerfil(
            @PathVariable Integer id,
            @RequestBody ModificarPerfilRequest request) {
        return ResponseEntity.ok(perfilService.modificarPerfil(id, request));
    }
}
