package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.dto.request.CrearUsuarioRequest;
import co.edu.usbcali.jasan.java.dto.request.IniciarSesionRequest;
import co.edu.usbcali.jasan.java.dto.response.IniciarSesionResponse;
import co.edu.usbcali.jasan.java.dto.response.ObtenerUsuarioResponse;

import java.util.List;

public interface UsuarioService {
    List<ObtenerUsuarioResponse> obtenerUsuarios();

    ObtenerUsuarioResponse obtenerUsuarioPorId(Integer id);

    ObtenerUsuarioResponse crearUsuario(CrearUsuarioRequest request);

    IniciarSesionResponse iniciarSesion(IniciarSesionRequest request);
}
