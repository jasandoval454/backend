package co.edu.usbcali.jasan.java.controller;

import co.edu.usbcali.jasan.java.dto.request.CrearMensajeRequest;
import co.edu.usbcali.jasan.java.dto.response.ObtenerMensajeResponse;
import co.edu.usbcali.jasan.java.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensajes")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    @GetMapping
    public List<ObtenerMensajeResponse> obtenerMensajes() {
        return mensajeService.obtenerMensajes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObtenerMensajeResponse> obtenerMensajePorId(
            @PathVariable Integer id
    ) throws Exception {
        return ResponseEntity.ok(mensajeService.obtenerMensajePorId(id));
    }

    @GetMapping("/conversacion")
    public List<ObtenerMensajeResponse> obtenerConversacion(
            @RequestParam("usuario_id") Integer usuarioId,
            @RequestParam("otro_usuario_id") Integer otroUsuarioId
    ) throws Exception {
        return mensajeService.obtenerConversacion(usuarioId, otroUsuarioId);
    }

    @GetMapping("/no-leidos")
    public List<ObtenerMensajeResponse> obtenerMensajesNoLeidos(
            @RequestParam("destinatario_id") Integer destinatarioId
    ) throws Exception {
        return mensajeService.obtenerMensajesNoLeidos(destinatarioId);
    }

    @PostMapping("/crear")
    public ResponseEntity<ObtenerMensajeResponse> crearMensaje(
            @RequestBody CrearMensajeRequest request
    ) throws Exception {
        return ResponseEntity.ok(mensajeService.crearMensaje(request));
    }

    @PatchMapping("/{id}/leido")
    public ResponseEntity<ObtenerMensajeResponse> marcarComoLeido(
            @PathVariable Integer id
    ) throws Exception {
        return ResponseEntity.ok(mensajeService.marcarComoLeido(id));
    }
}
