package co.edu.usbcali.jasan.java.controller;

import co.edu.usbcali.jasan.java.dto.request.CrearConversacionRequest;
import co.edu.usbcali.jasan.java.dto.response.ObtenerConversacionResponse;
import co.edu.usbcali.jasan.java.service.ConversacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversaciones")
public class ConversacionController {

    @Autowired
    private ConversacionService conversacionService;

    @GetMapping
    public List<ObtenerConversacionResponse> obtenerConversaciones() {
        return conversacionService.obtenerConversaciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObtenerConversacionResponse> obtenerConversacionPorId(
            @PathVariable Integer id
    ) throws Exception {
        return ResponseEntity.ok(conversacionService.obtenerConversacionPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<ObtenerConversacionResponse> obtenerConversacionesDeUsuario(
            @PathVariable Integer usuarioId
    ) throws Exception {
        return conversacionService.obtenerConversacionesDeUsuario(usuarioId);
    }

    @PostMapping("/crear")
    public ResponseEntity<ObtenerConversacionResponse> crearConversacion(
            @RequestBody CrearConversacionRequest request
    ) throws Exception {
        return ResponseEntity.ok(conversacionService.crearConversacion(request));
    }
}
