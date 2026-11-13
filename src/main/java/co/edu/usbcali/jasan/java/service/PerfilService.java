package co.edu.usbcali.jasan.java.service;

import co.edu.usbcali.jasan.java.domain.Perfil;
import co.edu.usbcali.jasan.java.dto.request.CrearPerfilRequest;
import co.edu.usbcali.jasan.java.dto.request.ModificarPerfilRequest;

import java.util.List;

public interface PerfilService {
    List<Perfil> obtenerPerfiles();

    Perfil obtenerPerfilPorId(Integer id);

    Perfil crearPerfil(CrearPerfilRequest request);

    Perfil modificarPerfil(Integer id, ModificarPerfilRequest request);
}
