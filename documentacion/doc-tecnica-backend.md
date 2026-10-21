# Documentación técnica del backend

## Alcance

Backend REST para una red social, implementado como una aplicación Spring Boot
con PostgreSQL. El alcance funcional de referencia está descrito en
[requisitos-funcionales.md](requisitos-funcionales.md); esos requisitos sirven
como objetivo y no implican que todos los flujos estén implementados.

## Tecnologías y ejecución

- Java 25 y Maven Wrapper.
- Spring Boot 4.1.0, Spring Web y Spring Data JPA.
- PostgreSQL y Hibernate/JPA.
- Lombok.

La configuración de conexión local está en
`src/main/resources/application.properties`. Mantén las credenciales fuera de
documentación, commits y otros archivos versionados. Los scripts de
`database/` se ejecutan en este orden: `create-db.sql`,
`schema_red_social.sql` y `data_test_red_social.sql`.

## Organización

El código de aplicación vive en
`src/main/java/co/edu/usbcali/jasan/java/`:

- `controller`: endpoints REST.
- `service`: contratos y lógica de negocio.
- `domain`: entidades, enums, converters y repositorios Spring Data.
- `dto/request` y `dto/response`: contratos de entrada y salida.
- `mapper`: conversión entre entidades y DTOs.

Las entidades corresponden al esquema de red social: usuarios, perfiles,
relaciones, publicaciones, medios, etiquetas, reacciones, comentarios,
notificaciones, bloqueos, denuncias e historial de actividad.

## Estado funcional

El backend cuenta con controladores para usuarios, perfiles, relaciones,
publicaciones, comentarios, reacciones, etiquetas, medios, notificaciones,
bloqueos, denuncias y la asociación publicación-etiqueta. Publicaciones y
etiquetas tienen servicios con validaciones de negocio; varios de los demás
controladores ofrecen principalmente consultas directas a repositorios.

El servicio de publicaciones permite listar, consultar, crear y modificar
publicaciones, y eliminarlas de forma lógica. Las consultas excluyen las
publicaciones eliminadas. La creación y modificación validan contenido y
privacidad; la creación también valida la existencia del autor. La creación de
etiquetas valida el nombre, su longitud y que no esté duplicado. La
autenticación, el feed, la carga de medios, la paginación y otros requisitos
funcionales aún deben verificarse o completarse antes de considerarlos
disponibles.

## Pruebas

Desde PowerShell, ejecuta la suite con:

```powershell
.\mvnw.cmd test
```

Para preparar el entorno de desarrollo y la base de datos, consulta la
[guía de onboarding](doc-onboarding-equipo.md).
