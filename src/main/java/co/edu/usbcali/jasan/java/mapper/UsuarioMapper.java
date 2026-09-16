package co.edu.usbcali.jasan.java.mapper;

import co.edu.usbcali.jasan.java.domain.Usuario;
import co.edu.usbcali.jasan.java.dto.response.ObtenerUsuarioResponse;

import java.util.List;

public class UsuarioMapper {

    // Hace el mapeo para obtener los usuarios por Id y no traer el objeto de la base de datos
    public static ObtenerUsuarioResponse usuarioAObtenerUsuarioResponse(Usuario usuario) {
        return new ObtenerUsuarioResponse(
                usuario.getId(), usuario.getEmail(), usuario.getUsername(), usuario.getLastLogin());
    }

    public static List<ObtenerUsuarioResponse> listaUsuariosHaciaListaObtenerUsuariosResponse(List<Usuario> usuarios) {
        /*
        * List<ObtenerUsuarioResponse> usuariosResponse = new ArrayList<>();

        for(int i = 0; i<usuarios.size(); i++) {
            ObtenerUsuarioResponse usuarioResponse = usuarioAObtenerUsuarioResponse(usuarios.get(i));
            usuariosResponse.add(usuarioResponse);
        }

        return usuariosResponse;*/
        return usuarios.stream().map(UsuarioMapper::usuarioAObtenerUsuarioResponse).toList();
    }

}
