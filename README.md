# Backend Jasan

Backend REST de la red social desarrollado con Java 25, Spring Boot 4.1.0,
Spring Data JPA y PostgreSQL.

## Estructura

- `src/main/java/.../controller`: endpoints REST.
- `src/main/java/.../service`: lógica de negocio.
- `src/main/java/.../domain`: entidades, enums, converters y repositorios.
- `src/main/java/.../dto` y `mapper`: contratos y transformación de datos.
- `database`: creación del usuario/base de datos, esquema y datos de prueba.
- `documentacion`: guía técnica y onboarding.

## Base de datos

Ejecuta los scripts en PostgreSQL en este orden:

1. `database/create-db.sql`
2. `database/schema_red_social.sql`
3. `database/data_test_red_social.sql`

El esquema incluye usuarios, perfiles, relaciones, publicaciones, medios,
etiquetas, reacciones, comentarios, notificaciones, bloqueos, denuncias y
actividad histórica y mensajes directos.
Si ya tienes una base creada con la versión anterior del proyecto, haz una
copia de seguridad y ejecuta `database/migrate_legacy_schema_to_red_social.sql`
en esa base; no vuelvas a ejecutar los scripts iniciales sobre datos existentes.

## Ejecución

Configura las credenciales de PostgreSQL en
`src/main/resources/application.properties` y ejecuta:

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd spring-boot:run
```

Consulta la [documentación técnica](documentacion/doc-tecnica-backend.md),
la [guía de onboarding](documentacion/doc-onboarding-equipo.md) y los
[requisitos funcionales](documentacion/requisitos-funcionales.md).

Endpoints iniciales:

- `/usuarios`
- `/etiquetas`
- `/bloqueos`
- `/comentarios`
- `/denuncias`
- `/medios`
- `/notificaciones`
- `/mensajes`
- `/conversaciones`
- `/perfiles`
- `/publicacion-etiquetas`
- `/reacciones`
- `/relaciones`

La creación de etiquetas acepta tanto `POST /etiquetas` como
`POST /etiquetas/crear` para mantener compatibilidad con `2026_2`.
El registro acepta `POST /usuarios` o `POST /usuarios/crear`; las contraseñas
se guardan con BCrypt. El inicio de sesión acepta `POST /usuarios/login` o
`POST /usuarios/iniciar-sesion` mediante email o username. Los perfiles se pueden crear con `POST /perfiles` y
editar parcialmente con `PUT /perfiles/{id}`. También se pueden asociar
metadatos multimedia con `POST /medios` y registrar notificaciones con
`POST /notificaciones`.
