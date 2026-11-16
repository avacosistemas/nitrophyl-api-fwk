package ar.com.avaco.nitrophyl.epservice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.avaco.fwk.core.component.epservice.CRUDAuditableEPBaseService;
import ar.com.avaco.fwk.core.exception.ErrorValidationException;
import ar.com.avaco.fwk.security.service.UsuarioService;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.EstadoOrdenFabricacion;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenCompra;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenCompraHistorial;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenFabricacion;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenFabricacionEntrega;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.TipoEventoOrdenCompra;
import ar.com.avaco.nitrophyl.domain.entities.pieza.EstadoReserva;
import ar.com.avaco.nitrophyl.domain.entities.pieza.OrigenMovimientoStock;
import ar.com.avaco.nitrophyl.domain.entities.pieza.Pieza;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStockLote;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStockLoteMovimiento;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStockReserva;
import ar.com.avaco.nitrophyl.dto.AccionEntregaOrdenFabricacionDTO;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionEntregaDTO;
import ar.com.avaco.nitrophyl.service.fabricacion.OrdenCompraHistorialService;
import ar.com.avaco.nitrophyl.service.fabricacion.OrdenCompraService;
import ar.com.avaco.nitrophyl.service.fabricacion.OrdenFabricacionEntregaService;
import ar.com.avaco.nitrophyl.service.fabricacion.OrdenFabricacionService;
import ar.com.avaco.nitrophyl.service.lote.LoteService;
import ar.com.avaco.nitrophyl.service.pieza.PiezaMovimientoStockService;
import ar.com.avaco.nitrophyl.service.pieza.PiezaReservaStockService;
import ar.com.avaco.nitrophyl.service.pieza.PiezaStockService;

//@Transactional
@Service("ordenFabricacionEntregaEPService")
public class OrdenFabricacionEntregaEPServiceImpl extends
		CRUDAuditableEPBaseService<Long, OrdenFabricacionEntregaDTO, OrdenFabricacionEntrega, OrdenFabricacionEntregaService>
		implements OrdenFabricacionEntregaEPService {

	@Autowired
	private OrdenCompraService ordenCompraService;

	@Autowired
	private OrdenFabricacionService ordenFabricacionService;

	@Autowired
	private LoteService loteService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PiezaMovimientoStockService movimientoStockService;
	@Autowired
	private PiezaReservaStockService reservaStockService;
	@Autowired
	private PiezaStockService piezaStockService;
	@Autowired
	private OrdenCompraHistorialService historialService;

	public OrdenFabricacionEntregaEPServiceImpl() {
		super(OrdenFabricacionEntrega.class, OrdenFabricacionEntregaDTO.class);
	}

	@Override
	@Transactional
	public void registrarEntrega(Long idOrdenFabricacion, OrdenFabricacionEntregaDTO entrega) {

		// Validaciones sobre los valores del dto
		if (entrega.getCantidad() == null || entrega.getCantidad() <= 0 || entrega.getIdUsuario() == null
				|| entrega.getIdLote() == null || entrega.getIdLote().isEmpty()
				|| StringUtils.isBlank(entrega.getObservaciones()))
			throw new ErrorValidationException("Indique cantidad positiva, operario, lotes y observaciones", null);

		// Obtengo la orden de fabriacion de la entrega
		OrdenFabricacion ordenFabricacion = this.ordenFabricacionService.get(idOrdenFabricacion);

		// Valido que el estado sea el correcto para registrar la entrega
		if (ordenFabricacion.getEstado() == EstadoOrdenFabricacion.CANCELADA
				|| ordenFabricacion.getEstado() == EstadoOrdenFabricacion.FACTURADA)
			throw new ErrorValidationException("No se pueden registrar entregas en una OF cancelada o facturada", null);

		// Genero la entrega
		OrdenFabricacionEntrega generarEntrega = generarEntrega(idOrdenFabricacion, entrega);
		generarEntrega.setOrdenFabricacion(ordenFabricacion);
		this.service.save(generarEntrega);

		// Obtengo la pieza de la OF
		Pieza pieza = piezaDe(ordenFabricacion);

		// Armo la clave de lotes
		String lotes = firmaLotes(generarEntrega);

		// Registro el ingreso de stock fisico
		PiezaStockLote saldo = piezaStockService.ingresar(pieza.getId(), lotes, entrega.getCantidad());

		// Regisro el movimiento
		movimientoStockService.save(PiezaStockLoteMovimiento.builder().piezaStockLote(saldo)
				.ordenFabricacionEntrega(generarEntrega).cantidad(entrega.getCantidad()).fecha(LocalDate.now())
				.observacion("Ingreso por entrega " + generarEntrega.getId()).origen(OrigenMovimientoStock.PRODUCCION)
				.build());

		// Obtengo el total de las reservas activas
		int cubierta = reservaStockService.cantidadCubiertaPorOrdenFabricacion(idOrdenFabricacion);

		// Compruebo si con la entrega no pase de piezas pedidas
		int efectiva = Math.min(entrega.getCantidad(),
				Math.max(0, ordenFabricacion.getOrdenCompraDetallePedido().getCantidad() - cubierta));

		// Calculo el excedente
		int excedente = entrega.getCantidad() - efectiva;

		// Hago la reserva solo por la cantidad para no pasarme de asignacion. Si hay
		// excedente no lo tomo.
		if (efectiva > 0) {
			PiezaStockReserva reserva = new PiezaStockReserva();
			reserva.setOrdenFabricacion(ordenFabricacion);
			reserva.setEntregaOrigen(generarEntrega);
			reserva.setLotes(lotes);
			reserva.setCantidadReservada(efectiva);
			reserva.setFechaReserva(LocalDateTime.now());
			reserva.setEstado(ar.com.avaco.nitrophyl.domain.entities.pieza.EstadoReserva.ACTIVA);
			reservaStockService.save(reserva);
			piezaStockService.modificarStockReservado(saldo.getId(), efectiva);
			movimientoStockService.save(PiezaStockLoteMovimiento.builder().piezaStockLote(saldo)
					.ordenFabricacionEntrega(generarEntrega).cantidad(efectiva).fecha(LocalDate.now())
					.observacion("Reserva " + reserva.getId() + " por entrega " + generarEntrega.getId())
					.origen(OrigenMovimientoStock.RESERVA_PEDIDO).build());
		}
		actualizarEstado(ordenFabricacion);
		ordenFabricacionService.update(ordenFabricacion);
		actualizarEstadoOrdenCompra(ordenFabricacion);
		registrarHistorial(ordenFabricacion, TipoEventoOrdenCompra.ENTREGA_REGISTRADA, "ORDEN_FABRICACION_ENTREGA",
				generarEntrega.getId(), entrega.getObservaciones(),
				"Producidas " + entrega.getCantidad() + ", efectivas " + efectiva + ", excedente a stock " + excedente);
	}

	@Override
	@Transactional
	public void cancelar(Long idEntrega, AccionEntregaOrdenFabricacionDTO accion) {

		// Validaciones de datos
		if (accion == null || StringUtils.isBlank(accion.getObservaciones()))
			throw new ErrorValidationException("Debe indicar el motivo de cancelación", null);

		// Obtengo la entrega de la orden de fabricacion
		OrdenFabricacionEntrega entrega = service.get(idEntrega);

		// Si esta cancelada no puedo cancelarla
		if (estaCancelada(entrega))
			throw new ErrorValidationException("La entrega ya está cancelada", null);

		// Libero las reservas asignadas a esa entrega
		liberarReservas(entrega, accion.getObservaciones(), OrigenMovimientoStock.CANCELACION_ENTREGA);
		
		// Obtengo la OF 
		OrdenFabricacion of = entrega.getOrdenFabricacion();
		
		// actualizo el estado de la OF por si estaba finalizada que vuelva a pendiente
		actualizarEstado(of);
		ordenFabricacionService.update(of);

		// actualizo el estado de la OC por si estaba finalizada que vuelva a pendiente
		actualizarEstadoOrdenCompra(of);
		
		// Registro el historial
		registrarHistorial(of, TipoEventoOrdenCompra.ENTREGA_CANCELADA, "ORDEN_FABRICACION_ENTREGA", entrega.getId(),
				accion.getObservaciones(),
				"Entrega marcada como cancelada. La producción registrada permanece en stock disponible.");
	}

	@Override
	@Transactional
	public void reasignar(Long idEntrega, AccionEntregaOrdenFabricacionDTO accion) {
		
		// Validaciones de datos
		if (accion == null || StringUtils.isBlank(accion.getObservaciones())
				|| accion.getIdOrdenFabricacionDestino() == null)
			throw new ErrorValidationException("Indique motivo y OF destino para reasignar", null);
		
		// Obtengo la entrega
		OrdenFabricacionEntrega entrega = service.get(idEntrega);

		// Valido que la entrega no este cancelada
		if (estaCancelada(entrega))
			throw new ErrorValidationException("La entrega ya está cancelada", null);
		
		if (cantidadEfectiva(entrega) == 0)
			throw new ErrorValidationException("La entrega no tiene cantidad asignada vigente para reasignar", null);
		
		// Obtengo la OF origen
		OrdenFabricacion ordenFabricacionOrigen = entrega.getOrdenFabricacion();
		
		// Obtengo la pieza
		Pieza pieza = piezaDe(ordenFabricacionOrigen);
		
		// Obtengo la OF a donde voy a enviar la entrega
		OrdenFabricacion ordenFabricacionDestino = ordenFabricacionService.get(accion.getIdOrdenFabricacionDestino());
		
		// Valido que la OF destino sea valida
		if (ordenFabricacionOrigen.getId().equals(ordenFabricacionDestino.getId()) || !pieza.getId().equals(piezaDe(ordenFabricacionDestino).getId())
				|| ordenFabricacionDestino.getEstado() == EstadoOrdenFabricacion.CANCELADA
				|| ordenFabricacionDestino.getEstado() == EstadoOrdenFabricacion.FINALIZADA)
			throw new ErrorValidationException("La OF destino debe ser otra OF abierta de la misma pieza", null);
		
		int cantidad = cantidadEfectiva(entrega);

		if (cantidad == 0)
			throw new ErrorValidationException("La entrega no tiene una reserva vigente para reasignar", null);
		
		// Libero la reserva que se hizo de stock a la OF original
		List<PiezaStockReserva> reservasOrigen = liberarReservas(entrega, accion.getObservaciones(),
				OrigenMovimientoStock.CANCELACION_RESERVA);
		
		PiezaStockReserva primera = reservasOrigen.get(0);
		
		PiezaStockLote ingreso = piezaStockService.buscar(pieza.getId(), primera.getLotes());
		int cubierta = reservaStockService.cantidadCubiertaPorOrdenFabricacion(ordenFabricacionDestino.getId());
		int aAsignar = Math.min(cantidad, Math.max(0, ordenFabricacionDestino.getOrdenCompraDetallePedido().getCantidad() - cubierta));
		if (aAsignar == 0)
			throw new ErrorValidationException("La OF destino ya está cubierta", null);

		// Armo la nueva reserva
		PiezaStockReserva reserva = new PiezaStockReserva();
		reserva.setOrdenFabricacion(ordenFabricacionDestino);
		reserva.setCantidadReservada(aAsignar);
		reserva.setLotes(primera.getLotes());
		reserva.setFechaReserva(LocalDateTime.now());
		reserva.setEstado(ar.com.avaco.nitrophyl.domain.entities.pieza.EstadoReserva.ACTIVA);
		reservaStockService.save(reserva);
		
		// Actualizo el stock reservado
		piezaStockService.modificarStockReservado(ingreso.getId(), aAsignar);
		
		// Registro el movimiento
		movimientoStockService.save(PiezaStockLoteMovimiento.builder().piezaStockLote(ingreso).cantidad(aAsignar)
				.fecha(LocalDate.now()).origen(OrigenMovimientoStock.RESERVA_PEDIDO)
				.observacion("Reasignación a OF " + ordenFabricacionDestino.getNumeroOT()).build());
		
		// Actualizo estado
		
		actualizarEstado(ordenFabricacionDestino);
		ordenFabricacionService.update(ordenFabricacionDestino);

		actualizarEstado(ordenFabricacionOrigen);
		ordenFabricacionService.update(ordenFabricacionOrigen);

		actualizarEstadoOrdenCompra(ordenFabricacionDestino);
		registrarHistorial(ordenFabricacionOrigen, TipoEventoOrdenCompra.ENTREGA_REASIGNADA, "ORDEN_FABRICACION_ENTREGA",
				entrega.getId(), accion.getObservaciones(), "Reasignación de entrega " + idEntrega + ": " + aAsignar
						+ " piezas desde el grupo de lotes [" + ingreso.getFirmaLotes() + "].");
	}

	/**
	 * Vuelve para atras las reservas asociadas a la entrega cancelada. El stock reservado vuelve a estar liberado.
	 * @param entrega la entrega
	 * @param motivo el motivo
	 * @param origen
	 * @return
	 */
	private List<PiezaStockReserva> liberarReservas(OrdenFabricacionEntrega entrega, String motivo,
			OrigenMovimientoStock origen) {

		// Busco las reservas asociadas a esa entrega
		List<PiezaStockReserva> reservas = reservaStockService.listarActivasPorEntrega(entrega.getId());

		// Por cada reserva
		for (PiezaStockReserva reserva : reservas) {

			// Obtengo el saldo
			PiezaStockLote saldo = piezaStockService.buscar(piezaDe(entrega.getOrdenFabricacion()).getId(),
					reserva.getLotes());

			// Actualizo el stock reservado
			piezaStockService.modificarStockReservado(saldo.getId(), -reserva.getCantidadReservada());
			
			// Marco la reserva como cancelada
			reserva.setEstado(EstadoReserva.CANCELADA);
			
			// Actualizo la reserva como cancelada
			reservaStockService.update(reserva);
			
			// Registro el movimiento
			movimientoStockService.save(PiezaStockLoteMovimiento.builder().piezaStockLote(saldo)
					.ordenFabricacionEntrega(entrega).cantidad(reserva.getCantidadReservada()).fecha(LocalDate.now())
					.observacion(motivo.trim()).origen(origen).build());
		}
		
		return reservas;
	}

	private int cantidadEfectiva(OrdenFabricacionEntrega entrega) {
		return reservaStockService.listarActivasPorEntrega(entrega.getId()).stream()
				.mapToInt(PiezaStockReserva::getCantidadReservada).sum();
	}

	private Pieza piezaDe(OrdenFabricacion of) {
		return of.getOrdenCompraDetallePedido().getOrdenCompraDetalle().getPieza();
	}

	private void actualizarEstado(OrdenFabricacion of) {
		if (of.getEstado() == EstadoOrdenFabricacion.CANCELADA)
			return;
		int cobertura = reservaStockService.cantidadCubiertaPorOrdenFabricacion(of.getId());
		of.setEstado(cobertura >= of.getOrdenCompraDetallePedido().getCantidad() ? EstadoOrdenFabricacion.FINALIZADA
				: EstadoOrdenFabricacion.EN_PROCESO);
	}

	private String firmaLotes(OrdenFabricacionEntrega entrega) {
		return entrega.getLotes().stream().map(l -> l.getNroLote()).sorted().distinct()
				.collect(java.util.stream.Collectors.joining(","));
	}

	private boolean estaCancelada(OrdenFabricacionEntrega entrega) {
		Long idOc = entrega.getOrdenFabricacion().getOrdenCompraDetallePedido().getOrdenCompraDetalle().getOrdenCompra()
				.getId();
		return historialService.listarPorOrdenCompra(idOc).stream()
				.anyMatch(h -> (h.getTipoEvento() == TipoEventoOrdenCompra.ENTREGA_CANCELADA
						|| h.getTipoEvento() == TipoEventoOrdenCompra.ENTREGA_REASIGNADA)
						&& "ORDEN_FABRICACION_ENTREGA".equals(h.getTipoEntidad())
						&& entrega.getId().equals(h.getIdEntidad()));
	}

	private void actualizarEstadoOrdenCompra(OrdenFabricacion of) {
		OrdenCompra oc = of.getOrdenCompraDetallePedido().getOrdenCompraDetalle().getOrdenCompra();
		oc.setEstado(ordenFabricacionService.calcularEstadoOrdenCompra(oc.getId()));
		ordenCompraService.update(oc);
	}

	private void registrarHistorial(OrdenFabricacion of, TipoEventoOrdenCompra tipo, String entidad, Long idEntidad,
			String motivo, String detalle) {
		OrdenCompraHistorial h = new OrdenCompraHistorial();
		h.setOrdenCompra(of.getOrdenCompraDetallePedido().getOrdenCompraDetalle().getOrdenCompra());
		h.setTipoEvento(tipo);
		h.setTipoEntidad(entidad);
		h.setIdEntidad(idEntidad);
		h.setMotivo(motivo.trim());
		h.setDetalle(detalle);
		h.setFechaEvento(LocalDateTime.now());
		historialService.save(h);
	}

	private OrdenFabricacionEntrega generarEntrega(Long idOrdenFabricacion, OrdenFabricacionEntregaDTO entrega) {
		OrdenFabricacionEntrega ofe = new OrdenFabricacionEntrega();
		ofe.setCantidad(entrega.getCantidad());
		ofe.setFecha(entrega.getFecha());
		Pieza pieza = piezaDe(ordenFabricacionService.get(idOrdenFabricacion));
		entrega.getIdLote().forEach(idlote -> {
			ar.com.avaco.nitrophyl.domain.entities.lote.Lote lote = loteService.get(idlote);
			if ((lote.getEstado() != ar.com.avaco.nitrophyl.domain.entities.lote.EstadoLote.APROBADO
					&& lote.getEstado() != ar.com.avaco.nitrophyl.domain.entities.lote.EstadoLote.APROBADO_OBSERVADO)
					|| !lote.getFormula().getId().equals(pieza.getDetalleFormula().getFormula().getId()))
				throw new ErrorValidationException(
						"Los lotes deben estar aprobados y corresponder a la fórmula de la pieza", null);
			ofe.getLotes().add(lote);
		});

		ofe.setOperario(usuarioService.get(entrega.getIdUsuario()));
		return ofe;
	}

	@Override
	protected OrdenFabricacionEntregaDTO convertToDto(OrdenFabricacionEntrega entity) {
		entity.getLotes();
		return super.convertToDto(entity);
	}

	@Override
	public void remove(Long id) {
		throw new ErrorValidationException("Las entregas no se eliminan; utilice la acción de cancelación con motivo",
				null);
	}

	@Override
	@Resource(name = "ordenFabricacionEntregaService")
	protected void setService(OrdenFabricacionEntregaService service) {
		this.service = service;
	}
}
