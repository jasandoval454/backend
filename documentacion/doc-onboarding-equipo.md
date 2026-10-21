# Onboarding del equipo

## Requisitos previos

- JDK 25, acorde con `java.version` en `pom.xml`.
- PostgreSQL disponible localmente.
- Git. No es necesario instalar Maven: el repositorio incluye Maven Wrapper.

Verifica el JDK desde PowerShell:

```powershell
java -version
```

## Preparar PostgreSQL

Revisa `database/create-db.sql`, `database/schema_red_social.sql` y
`database/data_test_red_social.sql`. Ejecútalos en ese orden; el primer script
crea el usuario/base de datos y requiere permisos administrativos. La
configuración local de conexión se encuentra en
`src/main/resources/application.properties`.

No guardes credenciales reales en Git ni las compartas en documentación.

## Compilar, probar y ejecutar

Desde la raíz del repositorio:

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd spring-boot:run
```

La suite y la aplicación requieren que las dependencias externas usadas por
la configuración local estén disponibles.

## Estructura para contribuir

El código está bajo `src/main/java/co/edu/usbcali/jasan/java/`. Mantén los
controladores, servicios, entidades/repositorios, DTOs y mappers en sus capas
correspondientes. Para cambios de persistencia, actualiza y verifica el
esquema y los datos de prueba en `database/`; para cambios de comportamiento,
añade pruebas junto con la lógica afectada.

Consulta el [README principal](../README.md), la
[documentación técnica](doc-tecnica-backend.md) y los
[requisitos funcionales](requisitos-funcionales.md) antes de iniciar un cambio.
