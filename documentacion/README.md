# Documentación del backend

Esta carpeta reúne la documentación incorporada a partir de la revisión de
`2026_2`. El proyecto actual conserva el nombre y el package `jasan.java`,
pero mantiene el mismo dominio funcional de la referencia.

## Referencias

- [README del proyecto](../README.md)
- [Creación de la base de datos](../database/create-db.sql)
- [Esquema PostgreSQL](../database/schema_red_social.sql)
- [Datos de prueba](../database/data_test_red_social.sql)

## Estado actual

El backend ya cuenta con entidades JPA, repositorios, controladores para las
entidades principales, DTOs y un servicio de etiquetas. La validación de
etiquetas incluye control de nombre vacío, longitud máxima y duplicados.

## Pendientes principales

- Completar servicios de negocio para los módulos que aún exponen acceso
  directo desde el controlador.
- Centralizar el manejo de errores HTTP.
- Añadir validación declarativa de requests y pruebas de integración.
- Configurar credenciales mediante variables de entorno en despliegues.
