package co.edu.usbcali.jasan.java.mapper;

import co.edu.usbcali.jasan.java.domain.Usuario;
import co.edu.usbcali.jasan.java.dto.response.ObtenerUsuarioResponse;

import java.util.List;

public final class UsuarioMapper {

    private UsuarioMapper() {
    }

    // Hace el mapeo para obtener los usuarios por Id y no traer el objeto de la base de datos
    public static ObtenerUsuarioResponse usuarioAObtenerUsuarioResponse(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return new ObtenerUsuarioResponse(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getUsername(),
                usuario.getLastLogin()
        );
    }

    public static List<ObtenerUsuarioResponse> listaUsuariosHaciaListaObtenerUsuariosResponse(List<Usuario> usuarios) {
        if (usuarios == null || usuarios.isEmpty()) {
            return List.of();
        }

        return usuarios.stream()
                .map(UsuarioMapper::usuarioAObtenerUsuarioResponse)
                .toList();
    }
}
