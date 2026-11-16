-- Modelo de cobertura de OF separado de los eventos de produccion.
-- No modifica ni elimina entregas existentes. Copia su cantidad efectiva a
-- asignaciones por entrega y conserva las reservas de stock anteriores.
-- Este archivo es solo para revision; NO fue ejecutado.

BEGIN;
SET LOCAL lock_timeout = '5s';
SET LOCAL statement_timeout = '60s';

DO $$
BEGIN
    IF to_regclass('public.orden_fabricacion') IS NULL
       OR to_regclass('public.orden_fabricacion_entrega') IS NULL
       OR to_regclass('public.pieza_reserva_stock') IS NULL THEN
        RAISE EXCEPTION 'Faltan tablas de OF, entregas o reservas; migracion cancelada';
    END IF;
END $$;

CREATE SEQUENCE IF NOT EXISTS public.orden_fab_asig_seq START WITH 1 INCREMENT BY 1;

ALTER TABLE public.pieza_reserva_stock
    ADD COLUMN IF NOT EXISTS fecha_cambio_estado TIMESTAMP WITHOUT TIME ZONE NULL;

-- Backfill an approximate transition timestamp for existing terminal reservations.
UPDATE public.pieza_reserva_stock
SET fecha_cambio_estado = COALESCE(fecha_actualizacion, fecha_reserva)
WHERE estado IN ('CANCELADA', 'CONSUMIDA') AND fecha_cambio_estado IS NULL;

CREATE TABLE IF NOT EXISTS public.orden_fabricacion_asignacion (
    id_orden_fab_asignacion BIGINT NOT NULL DEFAULT nextval('public.orden_fab_asig_seq'),
    id_orden_fabricacion BIGINT NOT NULL,
    origen VARCHAR(20) NOT NULL,
    id_orden_fab_entrega BIGINT NULL,
    id_pieza_reserva_stock BIGINT NULL,
    cantidad_asignada INTEGER NOT NULL,
    cantidad_liberada INTEGER NOT NULL DEFAULT 0,
    fecha_asignacion TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    observaciones VARCHAR(2000) NULL,
    usuario_creacion VARCHAR NOT NULL DEFAULT 'MIGRACION',
    fecha_creacion TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    usuario_actualizacion VARCHAR NOT NULL DEFAULT 'MIGRACION',
    fecha_actualizacion TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_orden_fabricacion_asignacion PRIMARY KEY (id_orden_fab_asignacion),
    CONSTRAINT fk_of_asignacion_of FOREIGN KEY (id_orden_fabricacion)
        REFERENCES public.orden_fabricacion (id_orden_fabricacion) ON DELETE RESTRICT,
    CONSTRAINT fk_of_asignacion_entrega FOREIGN KEY (id_orden_fab_entrega)
        REFERENCES public.orden_fabricacion_entrega (id_orden_fab_ent) ON DELETE RESTRICT,
    CONSTRAINT fk_of_asignacion_reserva FOREIGN KEY (id_pieza_reserva_stock)
        REFERENCES public.pieza_reserva_stock (id_pieza_reserva_stock) ON DELETE RESTRICT,
    CONSTRAINT ck_of_asignacion_origen CHECK (origen IN ('ENTREGA', 'STOCK')),
    CONSTRAINT ck_of_asignacion_cantidades CHECK (
        cantidad_asignada > 0 AND cantidad_liberada >= 0 AND cantidad_liberada <= cantidad_asignada
    ),
    CONSTRAINT ck_of_asignacion_fuente CHECK (
        (origen = 'ENTREGA' AND id_orden_fab_entrega IS NOT NULL
            AND id_pieza_reserva_stock IS NULL)
        OR
        (origen = 'STOCK' AND id_orden_fab_entrega IS NULL
            AND id_pieza_reserva_stock IS NOT NULL)
    )
);

ALTER SEQUENCE public.orden_fab_asig_seq
    OWNED BY public.orden_fabricacion_asignacion.id_orden_fab_asignacion;

CREATE UNIQUE INDEX IF NOT EXISTS ux_of_asignacion_reserva
    ON public.orden_fabricacion_asignacion (id_pieza_reserva_stock)
    WHERE id_pieza_reserva_stock IS NOT NULL;
CREATE UNIQUE INDEX IF NOT EXISTS ux_of_asignacion_entrega
    ON public.orden_fabricacion_asignacion (id_orden_fab_entrega)
    WHERE id_orden_fab_entrega IS NOT NULL;
CREATE INDEX IF NOT EXISTS ix_of_asignacion_of
    ON public.orden_fabricacion_asignacion (id_orden_fabricacion);

-- Convert each legacy stock reservation linked to an OF. Preserve its original
-- quantity and stock group. Canceled reservations are represented as fully released.
INSERT INTO public.orden_fabricacion_asignacion (
    id_orden_fabricacion, origen, id_pieza_reserva_stock,
    cantidad_asignada, cantidad_liberada, fecha_asignacion
)
SELECT r.id_orden_fabricacion, 'STOCK', r.id_pieza_reserva_stock,
       r.cantidad_reservada,
       CASE WHEN r.estado = 'CANCELADA' THEN r.cantidad_reservada ELSE 0 END,
       r.fecha_reserva
FROM public.pieza_reserva_stock r
WHERE r.id_orden_fabricacion IS NOT NULL
  AND NOT EXISTS (
      SELECT 1 FROM public.orden_fabricacion_asignacion a
      WHERE a.id_pieza_reserva_stock = r.id_pieza_reserva_stock
  );

-- Legacy delivery rows remain untouched as production events. An assignment is
-- backfilled only for the effective amount that was not canceled or sent to stock.
INSERT INTO public.orden_fabricacion_asignacion (
    id_orden_fabricacion, origen, id_orden_fab_entrega,
    cantidad_asignada, cantidad_liberada, fecha_asignacion
)
SELECT e.id_orden_fabricacion, 'ENTREGA', e.id_orden_fab_ent,
       e.cantidad - COALESCE(e.cantidad_a_stock, 0), 0,
       COALESCE(e.fecha_creacion, e.fecha::timestamp, CURRENT_TIMESTAMP)
FROM public.orden_fabricacion_entrega e
WHERE NOT COALESCE(e.cancelada, FALSE)
  AND e.cantidad > COALESCE(e.cantidad_a_stock, 0)
  AND NOT EXISTS (
      SELECT 1 FROM public.orden_fabricacion_asignacion a
      WHERE a.id_orden_fab_entrega = e.id_orden_fab_ent
  );

COMMIT;
