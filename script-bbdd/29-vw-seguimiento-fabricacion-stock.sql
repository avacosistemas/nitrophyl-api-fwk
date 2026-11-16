-- Actualiza el modelo de lectura de OF para contemplar asignaciones de stock
-- y excluir entregas canceladas. Requiere ejecutar antes 28-stock-of-historial.sql.
-- Agrega columnas al final para conservar el orden y tipo de las columnas ya expuestas.
-- No modifica filas de negocio. Este archivo NO fue ejecutado.

BEGIN;
SET LOCAL lock_timeout = '5s';
SET LOCAL statement_timeout = '60s';

DO $$
BEGIN
    IF to_regclass('public.vw_seguimiento_fabricacion') IS NULL
       OR to_regclass('public.orden_compra_historial') IS NULL
       OR NOT EXISTS (
           SELECT 1 FROM information_schema.columns
           WHERE table_schema = 'public'
             AND table_name = 'orden_fabricacion_entrega'
             AND column_name = 'cancelada'
       ) THEN
        RAISE EXCEPTION 'Falta la vista o la migracion 28; migracion de vista cancelada';
    END IF;
END $$;

CREATE OR REPLACE VIEW public.vw_seguimiento_fabricacion AS
WITH fabricacion AS (
    SELECT ofe.id_orden_fabricacion,
           COALESCE(SUM(ofe.cantidad) FILTER (WHERE NOT ofe.cancelada), 0)::BIGINT AS total_fabricado,
           COALESCE(SUM(COALESCE(ofe.cantidad_a_stock, 0))
               FILTER (WHERE NOT ofe.cancelada), 0)::BIGINT AS total_excedente_fabricado
    FROM public.orden_fabricacion_entrega ofe
    GROUP BY ofe.id_orden_fabricacion
), asignaciones AS (
    SELECT prs.id_orden_fabricacion,
           COALESCE(SUM(prs.cantidad_reservada)
               FILTER (WHERE prs.estado IN ('ACTIVA', 'CONSUMIDA')), 0)::BIGINT AS total_asignado
    FROM public.pieza_reserva_stock prs
    WHERE prs.id_orden_fabricacion IS NOT NULL
    GROUP BY prs.id_orden_fabricacion
), base AS (
    SELECT oc.id_orden_compra,
           oc.estado AS estado_oc,
           oc.fecha AS fecha_oc,
           oc.comprobante AS comprobante_oc,
           ocdp.cantidad AS total_solicitado,
           ocdp.fecha_entrega_solicitada,
           ofab.id_orden_fabricacion,
           ofab.fecha AS fecha_of,
           ofab.numero,
           ofab.anio,
           ofab.estado AS estado_of,
           c.id_cliente,
           c.nombre AS cliente_nombre,
           p.id_pieza,
           p.denominacion AS pieza_codigo,
           f.id_formula,
           f.nombre AS formula_nombre,
           sf.id_seccion_fabrica,
           sf.nombre AS seccion_nombre,
           mf.id_maquina_fabrica,
           mf.nombre AS maquina_nombre,
           COALESCE(fab.total_fabricado, 0::BIGINT) AS total_fabricado,
           COALESCE(fab.total_excedente_fabricado, 0::BIGINT) AS total_excedente_fabricado,
           COALESCE(asi.total_asignado, 0::BIGINT) AS total_asignado
    FROM public.orden_fabricacion ofab
    JOIN public.orden_compra_detalle_pedido ocdp
      ON ofab.id_oc_det_pedido = ocdp.id_orden_compra_detalle_pedido
    JOIN public.orden_compra_detalle ocd
      ON ocdp.id_orden_compra_detalle = ocd.id_orden_compra_detalle
    JOIN public.orden_compra oc
      ON ocd.id_orden_compra = oc.id_orden_compra
    JOIN public.cliente c
      ON oc.id_cliente = c.id_cliente
    JOIN public.pieza p
      ON ocd.id_pieza = p.id_pieza
    JOIN public.formula f
      ON p.id_formula = f.id_formula
    LEFT JOIN public.sector_fabrica sf
      ON ofab.id_sec_fab = sf.id_seccion_fabrica
    LEFT JOIN public.maquina_fabrica mf
      ON ofab.id_maq_fab = mf.id_maquina_fabrica
    LEFT JOIN fabricacion fab
      ON ofab.id_orden_fabricacion = fab.id_orden_fabricacion
    LEFT JOIN asignaciones asi
      ON ofab.id_orden_fabricacion = asi.id_orden_fabricacion
)
SELECT id_orden_compra,
       estado_oc,
       fecha_oc,
       comprobante_oc,
       total_solicitado,
       fecha_entrega_solicitada,
       id_orden_fabricacion,
       fecha_of,
       numero,
       anio,
       estado_of,
       id_cliente,
       cliente_nombre,
       id_pieza,
       pieza_codigo,
       id_formula,
       formula_nombre,
       id_seccion_fabrica,
       seccion_nombre,
       id_maquina_fabrica,
       maquina_nombre,
       total_fabricado,
       GREATEST(total_solicitado::BIGINT - (total_fabricado - total_excedente_fabricado) - total_asignado, 0::BIGINT) AS saldo,
       total_asignado,
       total_excedente_fabricado,
       total_fabricado - total_excedente_fabricado AS total_aplicado_of
FROM base;

COMMIT;
