-- Actualiza, sin borrar registros, la base creada con el esquema anterior.
-- Ejecutar una sola vez en backend_db; las operaciones de columnas son idempotentes.
BEGIN;

DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = current_schema()
          AND table_name = 'bloqueos' AND column_name = 'usuario_bloqueador_id'
    ) AND NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = current_schema()
          AND table_name = 'bloqueos' AND column_name = 'usuario_id'
    ) THEN
        ALTER TABLE bloqueos RENAME COLUMN usuario_bloqueador_id TO usuario_id;
    END IF;

    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = current_schema()
          AND table_name = 'comentarios' AND column_name = 'autor_id'
    ) AND NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = current_schema()
          AND table_name = 'comentarios' AND column_name = 'usuario_id'
    ) THEN
        ALTER TABLE comentarios RENAME COLUMN autor_id TO usuario_id;
    END IF;

    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = current_schema()
          AND table_name = 'denuncias' AND column_name = 'denunciante_id'
    ) AND NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = current_schema()
          AND table_name = 'denuncias' AND column_name = 'usuario_reportante_id'
    ) THEN
        ALTER TABLE denuncias RENAME COLUMN denunciante_id TO usuario_reportante_id;
    END IF;

    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = current_schema()
          AND table_name = 'medios' AND column_name = 'url'
    ) AND NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = current_schema()
          AND table_name = 'medios' AND column_name = 'url_original'
    ) THEN
        ALTER TABLE medios RENAME COLUMN url TO url_original;
    END IF;

    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = current_schema()
          AND table_name = 'notificaciones' AND column_name = 'destinatario_id'
    ) AND NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = current_schema()
          AND table_name = 'notificaciones' AND column_name = 'usuario_id'
    ) THEN
        ALTER TABLE notificaciones RENAME COLUMN destinatario_id TO usuario_id;
    END IF;

    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = current_schema()
          AND table_name = 'notificaciones' AND column_name = 'origen_id'
    ) AND NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = current_schema()
          AND table_name = 'notificaciones' AND column_name = 'origen_usuario_id'
    ) THEN
        ALTER TABLE notificaciones RENAME COLUMN origen_id TO origen_usuario_id;
    END IF;

    IF to_regclass(current_schema() || '.publicacion_etiquetas') IS NULL
       AND to_regclass(current_schema() || '.publicaciones_etiquetas') IS NOT NULL THEN
        ALTER TABLE publicaciones_etiquetas RENAME TO publicacion_etiquetas;
    END IF;
END $$;

ALTER TABLE comentarios
    ALTER COLUMN contenido TYPE VARCHAR(1000);

ALTER TABLE denuncias
    ADD COLUMN IF NOT EXISTS usuario_reportado_id INTEGER
        REFERENCES usuarios (id) ON DELETE CASCADE;

ALTER TABLE medios
    ADD COLUMN IF NOT EXISTS url_thumbnail VARCHAR(500),
    ADD COLUMN IF NOT EXISTS formato VARCHAR(20),
    ADD COLUMN IF NOT EXISTS tamano_bytes INTEGER,
    ADD COLUMN IF NOT EXISTS orden SMALLINT NOT NULL DEFAULT 0;

ALTER TABLE notificaciones
    ADD COLUMN IF NOT EXISTS publicacion_id INTEGER
        REFERENCES publicaciones (id) ON DELETE CASCADE,
    ADD COLUMN IF NOT EXISTS comentario_id INTEGER
        REFERENCES comentarios (id) ON DELETE CASCADE;

DO $$
BEGIN
    IF to_regclass(current_schema() || '.publicacion_etiquetas') IS NOT NULL
       AND to_regclass(current_schema() || '.publicaciones_etiquetas') IS NOT NULL THEN
        INSERT INTO publicacion_etiquetas (publicacion_id, etiqueta_id)
        SELECT publicacion_id, etiqueta_id
        FROM publicaciones_etiquetas
        ON CONFLICT (publicacion_id, etiqueta_id) DO NOTHING;
    END IF;
END $$;

COMMIT;
