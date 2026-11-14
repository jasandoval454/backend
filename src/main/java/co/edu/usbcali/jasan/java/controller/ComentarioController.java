package co.edu.usbcali.jasan.java.controller;

import co.edu.usbcali.jasan.java.domain.Comentario;
import co.edu.usbcali.jasan.java.domain.repository.ComentarioRepository;
import co.edu.usbcali.jasan.java.dto.request.CrearComentarioRequest;
import co.edu.usbcali.jasan.java.dto.response.ObtenerComentarioResponse;
import co.edu.usbcali.jasan.java.service.ComentarioService;
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
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/ping")
    String pingpong() {
        return "pong";
    }

    @GetMapping("/validar-stado")
    String validarEstado() {
        return "ok";
    }

    @GetMapping("/obtener-comentarios")
    List<Comentario> obtenerComentarios() {
        return comentarioRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<Comentario> obtenerComentarioPorId(@PathVariable Integer id) {
        return comentarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/publicacion/{publicacionId}")
    List<ObtenerComentarioResponse> obtenerComentariosPorPublicacion(
            @PathVariable Integer publicacionId
    ) throws Exception {
        return comentarioService.obtenerComentariosPorPublicacion(publicacionId);
    }

    @PostMapping("/crear")
    ResponseEntity<ObtenerComentarioResponse> crearComentario(
            @RequestBody CrearComentarioRequest request
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(comentarioService.crearComentario(request));
    }
}
