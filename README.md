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
actividad histórica.

## Ejecución

Configura las credenciales de PostgreSQL en
`src/main/resources/application.properties` y ejecuta:

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd spring-boot:run
```

Endpoints iniciales:

- `/usuarios`
- `/etiquetas`
- `/bloqueos`
- `/comentarios`
- `/denuncias`
- `/medios`
- `/notificaciones`
- `/perfiles`
- `/publicacion-etiquetas`
- `/reacciones`
- `/relaciones`

La creación de etiquetas acepta tanto `POST /etiquetas` como
`POST /etiquetas/crear` para mantener compatibilidad con `2026_2`.
