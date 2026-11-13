package co.edu.usbcali.jasan.java.controller;

import co.edu.usbcali.jasan.java.dto.request.CrearUsuarioRequest;
import co.edu.usbcali.jasan.java.dto.response.ObtenerUsuarioResponse;
import co.edu.usbcali.jasan.java.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/ping")
    String pingPong() {
        return "pong";
    }

    @GetMapping("/validar-estado")
    String validarEstado() {
        return "ok";
    }

    @GetMapping("/obtener-usuarios")
    List<ObtenerUsuarioResponse> obtenerUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    @GetMapping("/{id}")
    ResponseEntity<ObtenerUsuarioResponse> obtenerUsuarioPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
    }

    @PostMapping({"", "/crear"})
    ResponseEntity<ObtenerUsuarioResponse> crearUsuario(@RequestBody CrearUsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crearUsuario(request));
    }
}
