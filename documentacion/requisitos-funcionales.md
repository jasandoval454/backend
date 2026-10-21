# Requisitos funcionales — Red social

Este documento incorpora los requisitos de red social incluidos en `2026_2`.
Describe el alcance esperado del sistema; su inclusión no significa que cada
requisito ya esté implementado.

## Alcance

La plataforma permite a sus usuarios crear perfiles, seguir a otros usuarios,
publicar contenido, interactuar mediante reacciones y comentarios y recibir
notificaciones. También contempla búsqueda, privacidad, moderación, medios e
historial de actividad.

## Requisitos

1. **Autenticación:** registro e inicio de sesión con email, usuario y
   contraseña; emitir un token válido al autenticarse.
2. **Perfil:** crear y editar nombre, biografía, avatar, ubicación, enlace y
   visibilidad pública o privada.
3. **Relaciones:** seguir o dejar de seguir; permitir solicitudes y aprobación
   cuando el perfil sea privado.
4. **Publicaciones:** crear contenido de texto y/o imágenes con autor, fecha,
   privacidad y etiquetas, respetando la visibilidad definida.
5. **Edición y eliminación:** permitir al autor actualizar o eliminar sus
   publicaciones.
6. **Feed:** ofrecer un feed personalizado, paginado y ordenado por fecha o
   relevancia.
7. **Reacciones:** permitir like o dislike, un tipo por usuario y publicación,
   con opción de cambiar o retirar la reacción.
8. **Comentarios:** crear, editar y eliminar comentarios; admitir respuestas
   anidadas hasta un nivel.
9. **Notificaciones:** notificar nuevos seguidores, solicitudes, reacciones,
   comentarios y respuestas; permitir marcar notificaciones como leídas.
10. **Búsqueda:** buscar usuarios, hashtags y publicaciones con resultados
    paginados y filtrables; ofrecer exploración de contenido destacado.
11. **Privacidad y moderación:** bloquear y denunciar usuarios y aplicar las
    reglas de visibilidad correspondientes.
12. **Medios:** subir, optimizar y eliminar imágenes; limitar tamaño/formato y
    gestionar errores de almacenamiento.
13. **Historial:** registrar actividades relevantes y permitir al propietario
    consultar su historial.

## Entidades de referencia

El modelo lógico incluye `Usuario`, `Perfil`, `Relacion`, `Publicacion`,
`Medio`, `Etiqueta`, `PublicacionEtiqueta`, `Reaccion`, `Comentario`,
`Notificacion`, `Bloqueo`, `Denuncia` y `ActividadHistorial`. El esquema SQL
vigente en `../database/schema_red_social.sql` es la referencia de columnas,
tipos y relaciones.

## Estado

Los endpoints y operaciones disponibles deben contrastarse con los requisitos
antes de considerar un flujo completo. La documentación técnica resume el
estado actual y las áreas que todavía requieren implementación o verificación.
