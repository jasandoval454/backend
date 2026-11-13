package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.dto.request.CrearUsuarioRequest;
import co.edu.usbcali.jasan.java.dto.response.ObtenerUsuarioResponse;

import java.util.List;

public interface UsuarioService {
    List<ObtenerUsuarioResponse> obtenerUsuarios();

    ObtenerUsuarioResponse obtenerUsuarioPorId(Integer id);

    ObtenerUsuarioResponse crearUsuario(CrearUsuarioRequest request);
}
