-- Migracion aditiva para asignar stock a OF y conservar el historial de OC.
-- No elimina datos. Ejecutar primero en una copia de produccion con backup verificado.
-- Este archivo NO fue ejecutado.
-- Las columnas tipo_movimiento de movimientos previos quedan NULL porque el
-- esquema anterior no permitia inferir con seguridad si eran ingresos/egresos.
-- Si una entrega tiene varios lotes, se conserva el conjunto de lotes posibles
-- como origen del stock, sin inventar cantidades por lote.

BEGIN;
SET LOCAL lock_timeout = '5s';
SET LOCAL statement_timeout = '60s';

DO $$
BEGIN
    IF to_regclass('public.orden_compra') IS NULL
       OR to_regclass('public.orden_compra_detalle_pedido') IS NULL
       OR to_regclass('public.orden_fabricacion') IS NULL
       OR to_regclass('public.orden_fabricacion_entrega') IS NULL
       OR to_regclass('public.orden_fabricacion_entrega_lote') IS NULL
       OR to_regclass('public.lote') IS NULL
       OR to_regclass('public.pieza_reserva_stock') IS NULL
       OR to_regclass('public.pieza_movimiento_stock') IS NULL THEN
        RAISE EXCEPTION 'No se encuentran todas las tablas esperadas en el esquema public; migracion cancelada';
    END IF;
END $$;

-- Una fecha solicitada se cancela logicamente; los registros actuales quedan vigentes.
ALTER TABLE public.orden_compra_detalle_pedido
    ADD COLUMN IF NOT EXISTS estado VARCHAR(20) NOT NULL DEFAULT 'VIGENTE';

-- Una entrega cancelada permanece consultable. Las existentes se consideran activas.
ALTER TABLE public.orden_fabricacion_entrega
    ADD COLUMN IF NOT EXISTS cancelada BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE public.orden_fabricacion_entrega
    ADD COLUMN IF NOT EXISTS cantidad_a_stock INTEGER NULL;
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint
        WHERE conname = 'ck_of_entrega_cantidad_a_stock'
          AND conrelid = 'public.orden_fabricacion_entrega'::regclass
    ) THEN
        ALTER TABLE public.orden_fabricacion_entrega
            ADD CONSTRAINT ck_of_entrega_cantidad_a_stock
            CHECK (cantidad_a_stock IS NULL OR (cantidad_a_stock >= 0 AND cantidad_a_stock <= cantidad));
    END IF;
END $$;

-- La asignacion se vincula a la OF. Se conserva la FK historica al detalle de OC
-- para filas previas; una fila nueva debe apuntar a detalle OC o a OF, no a ambos.
ALTER TABLE public.pieza_reserva_stock
    ALTER COLUMN id_orden_compra_detalle DROP NOT NULL;
ALTER TABLE public.pieza_reserva_stock
    ADD COLUMN IF NOT EXISTS id_orden_fabricacion BIGINT NULL;
ALTER TABLE public.pieza_reserva_stock
    ADD COLUMN IF NOT EXISTS id_movimiento_stock_origen BIGINT NULL;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint
        WHERE conname = 'fk_pieza_reserva_stock_of'
          AND conrelid = 'public.pieza_reserva_stock'::regclass
    ) THEN
        ALTER TABLE public.pieza_reserva_stock
            ADD CONSTRAINT fk_pieza_reserva_stock_of
            FOREIGN KEY (id_orden_fabricacion)
            REFERENCES public.orden_fabricacion (id_orden_fabricacion)
            ON DELETE RESTRICT;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint
        WHERE conname = 'ck_pieza_reserva_stock_un_solo_destino'
          AND conrelid = 'public.pieza_reserva_stock'::regclass
    ) THEN
        ALTER TABLE public.pieza_reserva_stock
            ADD CONSTRAINT ck_pieza_reserva_stock_un_solo_destino
            CHECK ((id_orden_compra_detalle IS NULL) <> (id_orden_fabricacion IS NULL));
    END IF;

END $$;

CREATE INDEX IF NOT EXISTS ix_pieza_reserva_stock_of_estado
    ON public.pieza_reserva_stock (id_orden_fabricacion, estado);

-- Tipo nuevo para movimientos de piezas. No se rellena historico ambiguo.
ALTER TABLE public.pieza_movimiento_stock
    ADD COLUMN IF NOT EXISTS tipo_movimiento VARCHAR(20) NULL;
ALTER TABLE public.pieza_movimiento_stock
    ADD COLUMN IF NOT EXISTS id_orden_compra_historial BIGINT NULL;

-- Un movimiento de ingreso puede provenir de uno o varios lotes. En movimientos
-- de cancelacion se asocian los lotes de la entrega completa, sin repartir cantidades.
CREATE TABLE IF NOT EXISTS public.pieza_movimiento_stock_lote (
    id_pieza_movimiento_stock BIGINT NOT NULL,
    id_lote BIGINT NOT NULL,
    CONSTRAINT pk_pieza_movimiento_stock_lote PRIMARY KEY (id_pieza_movimiento_stock, id_lote),
    CONSTRAINT fk_pieza_movimiento_stock_lote_mov FOREIGN KEY (id_pieza_movimiento_stock)
        REFERENCES public.pieza_movimiento_stock (id_pieza_movimiento_stock) ON DELETE RESTRICT,
    CONSTRAINT fk_pieza_movimiento_stock_lote_lote FOREIGN KEY (id_lote)
        REFERENCES public.lote (id_lote) ON DELETE RESTRICT
);
CREATE INDEX IF NOT EXISTS ix_pieza_movimiento_stock_lote_lote
    ON public.pieza_movimiento_stock_lote (id_lote);

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint
        WHERE conname = 'ck_pieza_movimiento_stock_tipo'
          AND conrelid = 'public.pieza_movimiento_stock'::regclass
    ) THEN
        ALTER TABLE public.pieza_movimiento_stock
            ADD CONSTRAINT ck_pieza_movimiento_stock_tipo
            CHECK (tipo_movimiento IS NULL OR tipo_movimiento IN ('INGRESO', 'EGRESO'));
    END IF;
END $$;

CREATE SEQUENCE IF NOT EXISTS public.orden_compra_historial_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS public.orden_compra_historial (
    id_orden_compra_historial BIGINT NOT NULL DEFAULT nextval('public.orden_compra_historial_seq'),
    id_orden_compra BIGINT NOT NULL,
    id_orden_fabricacion BIGINT NULL,
    tipo_evento VARCHAR(60) NOT NULL,
    tipo_entidad VARCHAR(60) NOT NULL,
    id_entidad BIGINT NULL,
    motivo TEXT NULL,
    detalle TEXT NULL,
    fecha_evento TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    usuario_creacion VARCHAR NOT NULL,
    fecha_creacion TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    usuario_actualizacion VARCHAR NOT NULL,
    fecha_actualizacion TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_orden_compra_historial PRIMARY KEY (id_orden_compra_historial),
    CONSTRAINT fk_orden_compra_historial_oc
        FOREIGN KEY (id_orden_compra) REFERENCES public.orden_compra (id_orden_compra) ON DELETE RESTRICT,
    CONSTRAINT fk_orden_compra_historial_of
        FOREIGN KEY (id_orden_fabricacion) REFERENCES public.orden_fabricacion (id_orden_fabricacion) ON DELETE RESTRICT
);

ALTER SEQUENCE public.orden_compra_historial_seq
    OWNED BY public.orden_compra_historial.id_orden_compra_historial;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint
        WHERE conname = 'fk_pieza_movimiento_stock_historial'
          AND conrelid = 'public.pieza_movimiento_stock'::regclass
    ) THEN
        ALTER TABLE public.pieza_movimiento_stock
            ADD CONSTRAINT fk_pieza_movimiento_stock_historial
            FOREIGN KEY (id_orden_compra_historial)
            REFERENCES public.orden_compra_historial (id_orden_compra_historial)
            ON DELETE RESTRICT;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint
        WHERE conname = 'fk_pieza_reserva_stock_mov_origen'
          AND conrelid = 'public.pieza_reserva_stock'::regclass
    ) THEN
        ALTER TABLE public.pieza_reserva_stock
            ADD CONSTRAINT fk_pieza_reserva_stock_mov_origen
            FOREIGN KEY (id_movimiento_stock_origen)
            REFERENCES public.pieza_movimiento_stock (id_pieza_movimiento_stock)
            ON DELETE RESTRICT;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint
        WHERE conname = 'ck_pieza_reserva_stock_of_con_origen'
          AND conrelid = 'public.pieza_reserva_stock'::regclass
    ) THEN
        ALTER TABLE public.pieza_reserva_stock
            ADD CONSTRAINT ck_pieza_reserva_stock_of_con_origen
            CHECK (id_orden_fabricacion IS NULL OR id_movimiento_stock_origen IS NOT NULL);
    END IF;
END $$;

CREATE INDEX IF NOT EXISTS ix_orden_compra_historial_oc_fecha
    ON public.orden_compra_historial (id_orden_compra, fecha_evento, id_orden_compra_historial);
CREATE INDEX IF NOT EXISTS ix_orden_compra_historial_entidad
    ON public.orden_compra_historial (tipo_entidad, id_entidad);
CREATE INDEX IF NOT EXISTS ix_orden_compra_historial_of_fecha
    ON public.orden_compra_historial (id_orden_fabricacion, fecha_evento);
CREATE INDEX IF NOT EXISTS ix_pieza_reserva_stock_mov_origen
    ON public.pieza_reserva_stock (id_movimiento_stock_origen, estado);
CREATE INDEX IF NOT EXISTS ix_pieza_movimiento_stock_historial
    ON public.pieza_movimiento_stock (id_orden_compra_historial);

COMMIT;
