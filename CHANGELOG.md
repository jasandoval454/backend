# Changelog

## [2026_2]

### Added

- Scripts PostgreSQL para crear la base, el esquema y datos de prueba.
- README y documentación inicial del backend.
- Validación transaccional y prevención de nombres duplicados al crear
  etiquetas.
- Compatibilidad con `POST /etiquetas/crear`, además de `POST /etiquetas`.

### Preserved

- Se conservaron los controladores adicionales ya existentes en el proyecto
  actual; `2026_2` solo incluía controladores de usuarios y etiquetas.
- Se añadió la validación de contenido obligatorio al crear publicaciones,
  presente en la implementación de referencia de `2026_2`.
- Se integraron los requisitos funcionales de la red social y documentación
  técnica/onboarding adaptados al proyecto actual.
