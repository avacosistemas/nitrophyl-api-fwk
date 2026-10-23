# Estructura de la base de datos

## Alcance y fuente

Esta guía describe el esquema PostgreSQL `nitrophyl` consultado en el entorno local el 23/10/2026. Se relevaron las tablas, columnas, claves primarias y foráneas, además de la vista de seguimiento de fabricación. El relevamiento fue de metadatos: no incluye credenciales ni datos de clientes/usuarios.

El esquema observado contiene las tablas de negocio y la vista `vw_seguimiento_fabricacion`. La base representa el estado físico actual; las entidades Java y esta documentación específica de órdenes sirven como contexto de aplicación, pero pueden corresponder a una versión distinta.

## Mapa funcional

| Área | Tablas principales | Para qué sirve según nombres y relaciones |
|---|---|---|
| Clientes y logística | `cliente`, `cliente_contacto`, `cliente_domicilio`, `pais`, `empresa_transporte` | Datos de clientes, contactos, domicilios y transporte. |
| Órdenes | `orden_compra`, `orden_compra_detalle`, `orden_compra_detalle_pedido`, `orden_compra_archivo` | Cabecera de compra, renglones, pedidos de fabricación derivados y adjuntos. |
| Fabricación | `orden_fabricacion`, `sector_fabrica`, `maquina_fabrica`, `orden_fabricacion_entrega`, `orden_fabricacion_check_control` | Órdenes de fabricación, asignación, entregas y controles. |
| Piezas y catálogo | `pieza`, `pieza_cliente`, `pieza_tipo`, `pieza_dimension`, `pieza_espesor`, `pieza_control`, `pieza_plano`, `pieza_molde` | Catálogo interno de piezas, variantes/configuración y relación comercial por cliente. |
| Stock de piezas | `pieza_stock`, `pieza_reserva_stock`, `pieza_movimiento_stock` | Existencias, reservas y movimientos. `pieza_stock` usa `id_pieza` como clave primaria. |
| Moldes y proceso | `molde`, `molde_cliente`, `molde_tipo_pieza`, `molde_boca`, `moldedimension`, `moldeplano`, `moldefoto`, `molderegistro`, `molde_observacion`, `troquel`, `proceso`, `proceso_prensa`, `prensa`, `bombeo`, `terminacion`, `esquema`, `esquema_paso` | Herramental, características de moldeo y etapas/procesos asociados a piezas. |
| Fórmulas y materiales | `formula`, `formula_rev_param`, `formula_rev_param_conf_prueba`, `material`, `conf_prueba`, `conf_prueba_cond`, `conf_prueba_param` | Fórmulas, revisiones, materiales y parámetros/condiciones de pruebas. |
| Insumos | `insumo`, `tipo_insumo`, `materia_prima`, `insumo_materia_prima`, `insumo_stock_historial`, `materia_prima_stock_historial`, `insumo_tratado`, `insumo_tratado_adhesivo`, `insumo_tratado_tratamiento`, `insumo_tratado_obs_control`, `adhesivo`, `tratamiento` | Insumos, composición, tratamientos y trazabilidad de stock. |
| Lotes y calidad | `lote`, `lote_observacion`, `lote_grafico`, `ensayo`, `ensayo_resultado`, `reporte_lote_conf_cliente`, `reporte_lote_conf_prueba`, `registro_envio_informe_calidad` | Lotes, ensayos, resultados, configuración de reportes y envío de informes. |
| Seguridad | `seg_usuario`, `seg_perfil`, `seg_rol`, `seg_permiso`, `seg_perfil_permiso`, `seg_acceso`, `seg_usr_imper`, `historico_passwords`, `usuario_mailsnotificacion` | Usuarios, perfiles, roles, permisos, accesos e historial asociado. |
| Parámetros y soporte | `parameter`, `parametro_general`, `numerador_ot`, `i18n`, `ayuda_dinamica`, `faq`, `contact_us`, `word` | Configuración general, numeración y contenido auxiliar. |

Los nombres de algunas tablas auxiliares de moldes están escritos sin guion bajo (`moldedimension`, `moldefoto`, `moldeplano`, `molderegistro`); conservar el nombre físico al consultarlas.

## Relaciones de negocio observadas

### Cliente, pieza y código particular

```text
cliente ──< pieza_cliente >── pieza
                    └── cotizacion
```

`pieza_cliente` referencia una pieza y un cliente. La tabla guarda `nombre_pieza_personalizado`, que en la interfaz de negocio se usa como identificación/código particular de la pieza para ese cliente. `pieza.codigo` pertenece al catálogo interno y no debe confundirse con ese identificador comercial. La tabla `cotizacion` también se vincula a la pieza-cliente.

### Compra y fabricación

```text
cliente ──< orden_compra ──< orden_compra_detalle ──< orden_compra_detalle_pedido
                                  │                                  │
                                  ├── pieza                           └──< orden_fabricacion
                                  └── cotizacion                                 │
                                                                                ├── sector_fabrica
                                                                                ├── maquina_fabrica
                                                                                ├── seg_usuario (operario)
                                                                                ├── entregas y controles
                                                                                └── lotes
```

La compra también puede referenciar un domicilio de envío del cliente y una empresa de transporte. `orden_compra_archivo` comparte su identificador con la orden de compra. La relación de fabricación parte del pedido de un renglón, no directamente de la cabecera.

El detalle de compra referencia `pieza` y `cotizacion`; en el esquema físico relevado `id_cotizacion` es obligatorio. El seguimiento de entregas de fabricación se complementa con `orden_fabricacion_entrega_lote`, que relaciona entregas con lotes.

### Fórmulas, materia prima y calidad

`pieza` se relaciona con fórmula; la fórmula se conecta con material y revisiones de parámetros. Las revisiones pueden asociarse a configuraciones de pruebas, condiciones y parámetros. Los lotes están relacionados con piezas y órdenes de fabricación, y se conectan con ensayos/resultados y configuración de reportes de calidad.

### Stock

El modelo separa stock de piezas (`pieza_stock`, reservas y movimientos), stock de insumos y stock de materia prima, cada uno con tablas de historial. Los vínculos de composición de insumos y tratamientos usan tablas asociativas.

## Claves y cardinalidades

- Las relaciones de varias filas usan claves foráneas explícitas en el esquema inspeccionado.
- Hay tablas asociativas con clave primaria compuesta; entre ellas `molde_cliente`, `molde_tipo_pieza`, `proceso_prensa`, `insumo_tratado_adhesivo`, `insumo_tratado_tratamiento`, `orden_fabricacion_entrega_lote` y `formula_rev_param_conf_prueba`.
- `orden_compra_archivo` tiene una relación uno-a-uno con la orden mediante identificador compartido.
- `pieza_stock` tiene una fila identificada por pieza según su PK física; no inferir de ello que todos los movimientos o reservas se modelan igual.
- Muchas tablas mantienen auditoría con columnas como `usuario_creacion`, `fecha_creacion`, `usuario_actualizacion` y `fecha_actualizacion`; verificar cada tabla antes de depender de que estén presentes.

La cardinalidad funcional exacta también puede depender de validaciones del servicio y de restricciones únicas. Esta guía prioriza claves observadas y no reemplaza la inspección de restricciones al implementar una consulta o cambio.

## Vista de seguimiento

`vw_seguimiento_fabricacion` ofrece un modelo de lectura que combina datos de OC, OF, cliente, pieza, fórmula, sector y máquina. Entre sus campos expuestos están `id_orden_compra`, `estado_oc`, `fecha_oc`, `comprobante_oc`, `total_solicitado`, `fecha_entrega_solicitada`, `id_orden_fabricacion`, `fecha_of`, `numero`, `anio`, `estado_of`, identificadores/nombres de cliente, pieza, fórmula, sector y máquina, además de `total_fabricado` y `saldo`.

Antes de recalcular saldos desde tablas base, comparar el criterio de la vista: es la referencia ya definida por la base para seguimiento agregado.

## Diferencias a tener en cuenta frente al código

El código Java inspeccionado y el esquema físico pueden evolucionar en momentos distintos. En particular, la base actual expone `pieza.id_formula` y campos de peso crudo en `pieza`; el modelo Java revisado representa parte de esa información mediante `PiezaFormula`. Para consultas SQL directas, usar las columnas reales del esquema. Para reglas de escritura y comportamiento de la aplicación, revisar también servicios y mapeos ORM.

## Inventario de tablas

<details>
<summary>Ver nombres físicos relevados</summary>

```text
adhesivo, ayuda_dinamica, bombeo, cliente, cliente_contacto, cliente_domicilio,
conf_prueba, conf_prueba_cond, conf_prueba_param, contact_us, cotizacion,
empresa_transporte, ensayo, ensayo_resultado, esquema, esquema_paso, faq,
formula, formula_rev_param, formula_rev_param_conf_prueba, historico_passwords,
i18n, insumo, insumo_materia_prima, insumo_stock_historial, insumo_tratado,
insumo_tratado_adhesivo, insumo_tratado_obs_control, insumo_tratado_tratamiento,
lote, lote_grafico, lote_observacion, maquina, maquina_fabrica, maquina_prueba,
materia_prima, materia_prima_stock_historial, material, molde, molde_boca,
molde_cliente, molde_observacion, molde_tipo_pieza, moldedimension, moldefoto,
moldeplano, molderegistro, numerador_ot, orden_compra, orden_compra_archivo,
orden_compra_detalle, orden_compra_detalle_pedido, orden_fabricacion,
orden_fabricacion_check_control, orden_fabricacion_entrega,
orden_fabricacion_entrega_lote, pais, parameter, parametro_general, pieza,
pieza_cliente, pieza_control, pieza_dimension, pieza_espesor, pieza_molde,
pieza_movimiento_stock, pieza_plano, pieza_reserva_stock, pieza_stock, pieza_tipo,
prensa, proceso, proceso_prensa, registro_envio_informe_calidad,
reporte_lote_conf_cliente, reporte_lote_conf_prueba, sector_fabrica, seg_acceso,
seg_perfil, seg_perfil_permiso, seg_permiso, seg_rol, seg_usr_imper, seg_usuario,
terminacion, tipo_insumo, tratamiento, troquel, usuario_mailsnotificacion, word
```

</details>

