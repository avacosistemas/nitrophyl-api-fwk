package ar.com.avaco.nitrophyl.epservice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.avaco.fwk.core.component.dto.PageDTO;
import ar.com.avaco.fwk.core.component.epservice.CRUDAuditableEPBaseService;
import ar.com.avaco.fwk.core.exception.ErrorValidationException;
import ar.com.avaco.fwk.core.utils.DateUtils;
import ar.com.avaco.fwk.security.service.UsuarioService;
import ar.com.avaco.nitrophyl.domain.entities.cliente.Cliente;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.EstadoOrdenCompra;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.EstadoOrdenFabricacion;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.MaquinaFabrica;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenCompra;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenCompraDetallePedido;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenCompraHistorial;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenFabricacion;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenFabricacionEntrega;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.SectorFabrica;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.TipoEventoOrdenCompra;
import ar.com.avaco.nitrophyl.domain.entities.formula.Formula;
import ar.com.avaco.nitrophyl.domain.entities.lote.Lote;
import ar.com.avaco.nitrophyl.domain.entities.molde.Molde;
import ar.com.avaco.nitrophyl.domain.entities.molde.Troquel;
import ar.com.avaco.nitrophyl.domain.entities.pieza.EstadoReserva;
import ar.com.avaco.nitrophyl.domain.entities.pieza.OrigenMovimientoStock;
import ar.com.avaco.nitrophyl.domain.entities.pieza.Pieza;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaControl;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaPlano;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStockLote;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStockLoteMovimiento;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStockReserva;
import ar.com.avaco.nitrophyl.domain.entities.pieza.cliente.Cotizacion;
import ar.com.avaco.nitrophyl.dto.AsignacionStockOrdenFabricacionDTO;
import ar.com.avaco.nitrophyl.dto.CabeceraOrdenTabajoDTO;
import ar.com.avaco.nitrophyl.dto.ControlCalidadDTO;
import ar.com.avaco.nitrophyl.dto.ItemOrdenTrabajoDTO;
import ar.com.avaco.nitrophyl.dto.ListadoOrdenFabricacionDTO;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionAsignacionDTO;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionDTO;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionFilterDTO;
import ar.com.avaco.nitrophyl.dto.OrdenTrabajoEntregaDTO;
import ar.com.avaco.nitrophyl.dto.OrdenTrabajoResponseDTO;
import ar.com.avaco.nitrophyl.dto.OrdenTrabajoResumenDTO;
import ar.com.avaco.nitrophyl.dto.PiezaLoteFabricacionDTO;
import ar.com.avaco.nitrophyl.dto.PiezaStockLoteDisponibleDTO;
import ar.com.avaco.nitrophyl.service.fabricacion.OrdenCompraDetallePedidoService;
import ar.com.avaco.nitrophyl.service.fabricacion.OrdenCompraDetalleService;
import ar.com.avaco.nitrophyl.service.fabricacion.OrdenCompraHistorialService;
import ar.com.avaco.nitrophyl.service.fabricacion.OrdenCompraService;
import ar.com.avaco.nitrophyl.service.fabricacion.OrdenFabricacionEntregaService;
import ar.com.avaco.nitrophyl.service.fabricacion.OrdenFabricacionService;
import ar.com.avaco.nitrophyl.service.lote.LoteService;
import ar.com.avaco.nitrophyl.service.pieza.PiezaControlService;
import ar.com.avaco.nitrophyl.service.pieza.PiezaMovimientoStockService;
import ar.com.avaco.nitrophyl.service.pieza.PiezaReservaStockService;
import ar.com.avaco.nitrophyl.service.pieza.PiezaStockService;

//@Transactional
@Service("ordenFabricacionEPService")
public class OrdenFabricacionEPServiceImpl
		extends CRUDAuditableEPBaseService<Long, OrdenFabricacionDTO, OrdenFabricacion, OrdenFabricacionService>
		implements OrdenFabricacionEPService {

	@Autowired
	private UsuarioService usuarioService;

	public OrdenFabricacionEPServiceImpl() {
		super(OrdenFabricacion.class, OrdenFabricacionDTO.class);
	}

	@Autowired
	private OrdenCompraService ordenCompraService;

	@Autowired
	private PiezaControlService piezaControlService;

	@Autowired
	private PiezaReservaStockService reservaStockService;

	@Autowired
	private PiezaStockService piezaStockService;

	@Autowired
	private PiezaMovimientoStockService movimientoStockService;

	@Autowired
	private OrdenCompraHistorialService historialService;

	@Autowired
	private LoteService loteService;

	@Autowired
	private OrdenFabricacionEntregaService ordenFabricacionEntregaService;

	@Autowired
	private OrdenCompraDetallePedidoService ordenCompraDetallePedidoService;

	@Autowired
	private OrdenCompraDetalleService ordenCompraDetalleService;

	@Override
	@Resource(name = "ordenFabricacionService")
	protected void setService(OrdenFabricacionService service) {
		this.service = service;
	}

	@Override
	public PageDTO<ListadoOrdenFabricacionDTO> listFilterCount(OrdenFabricacionFilterDTO ordenFabricacionFilterDTO) {
		return this.service.listFilterCountCustom(ordenFabricacionFilterDTO);
	}

	@Override
	@Transactional
	public void create(Long idOrdenCompra) {
		// Obtengo la orden de compra
		OrdenCompra oc = ordenCompraService.get(idOrdenCompra);

		// Valido que no tenga OF creadas ya para evitar duplicados
		if (service.existenParaOrdenCompra(idOrdenCompra))
			throw new ErrorValidationException("La OC ya tiene órdenes de fabricación generadas", null);

		// Generlo las ordenes de fabricacion
		service.generarOrdenes(oc.getDetalle());
		List<OrdenFabricacion> generadas = service.listarPorOrdenCompra(idOrdenCompra);

		// Determino el estado de la OC
		oc.setEstado(service.calcularEstadoOrdenCompra(idOrdenCompra));
		ordenCompraService.update(oc);

		// Armo el historial de creacion de la orden de compra
		OrdenCompraHistorial h = new OrdenCompraHistorial();
		h.setOrdenCompra(oc);
		h.setTipoEvento(TipoEventoOrdenCompra.ORDENES_FABRICACION_GENERADAS);
		h.setTipoEntidad("ORDEN_COMPRA");
		h.setIdEntidad(idOrdenCompra);
		// La generación de OF no requiere un motivo ingresado por el usuario.
		h.setMotivo("");
		h.setDetalle("Generadas " + generadas.size() + " OF con las asignaciones de stock informadas.");
		h.setFechaEvento(LocalDateTime.now());
		historialService.save(h);
	}

	@Override
	@Transactional
	public void asignarStock(Long idOrdenFabricacion, AsignacionStockOrdenFabricacionDTO asignacion) {

		// Validaciones para determinar que los datos que llegaron son correctos
		if (asignacion == null || asignacion.getCantidad() == null || asignacion.getCantidad() <= 0
				|| asignacion.getIdPiezaStockLote() == null || StringUtils.isBlank(asignacion.getObservaciones())) {
			throw new ErrorValidationException("Debe indicar el grupo de stock, cantidad positiva y observaciones",
					null);
		}

		// Obtengo la orden de fabricacion
		OrdenFabricacion of = service.get(idOrdenFabricacion);
		if (of.getEstado() == EstadoOrdenFabricacion.CANCELADA || of.getEstado() == EstadoOrdenFabricacion.FACTURADA)
			throw new ErrorValidationException("No se puede asignar stock a una OF cancelada o facturada", null);

		// Obtengo el saldo
		PiezaStockLote piezaLoteStock = piezaStockService.get(asignacion.getIdPiezaStockLote());
		Pieza pieza = of.getOrdenCompraDetallePedido().getOrdenCompraDetalle().getPieza();

		// Validacion de pieza y cantidad de stock
		if (!piezaLoteStock.getPiezaStock().getPieza().getId().equals(pieza.getId()))
			throw new ErrorValidationException("El grupo seleccionado no corresponde a la pieza de esta OF", null);
		if (asignacion.getCantidad() > piezaLoteStock.getStockDisponible())
			throw new ErrorValidationException("La cantidad supera el saldo disponible del grupo de lotes", null);

		// Busco la cantidad actual asignada por stock a la orden de fabricacion
		// Obtengo la cantidad total a completar
		int requerida = of.getOrdenCompraDetallePedido().getCantidad();

		// Reviso si no me paso de lo solicitado
		int cubierta = reservaStockService.cantidadCubiertaPorOrdenFabricacion(idOrdenFabricacion);
		if (asignacion.getCantidad() > requerida - cubierta) {
			throw new ErrorValidationException("La asignación supera la cantidad pendiente de la OF", null);
		}

		// Armo la reserva
		PiezaStockReserva reserva = new PiezaStockReserva();
		reserva.setOrdenFabricacion(of);
		reserva.setCantidadReservada(asignacion.getCantidad());
		reserva.setLotes(piezaLoteStock.getFirmaLotes());
		reserva.setFechaReserva(LocalDateTime.now());
		reserva.setEstado(EstadoReserva.ACTIVA);
		reservaStockService.save(reserva);

		// Una reserva afecta el saldo reservado del grupo y su cabecera; no el físico.
		piezaStockService.modificarStockReservado(piezaLoteStock.getId(), asignacion.getCantidad());
		int totalCubierto = cubierta + asignacion.getCantidad();
		of.setEstado(
				totalCubierto >= requerida ? EstadoOrdenFabricacion.FINALIZADA : EstadoOrdenFabricacion.EN_PROCESO);
		service.update(of);

		// Reviso si la OC se completo
		OrdenCompra oc = of.getOrdenCompraDetallePedido().getOrdenCompraDetalle().getOrdenCompra();
		oc.setEstado(service.calcularEstadoOrdenCompra(oc.getId()));
		ordenCompraService.update(oc);

		// Armo el historial de la OC
		OrdenCompraHistorial evento = new OrdenCompraHistorial();
		evento.setOrdenCompra(oc);
		evento.setTipoEvento(TipoEventoOrdenCompra.STOCK_ASIGNADO);
		evento.setTipoEntidad("PIEZA_STOCK_RESERVA");
		evento.setIdEntidad(reserva.getId());
		evento.setMotivo(asignacion.getObservaciones().trim());
		evento.setDetalle(asignacion.getCantidad() + " piezas reservadas a la OF desde el grupo de lotes ["
				+ piezaLoteStock.getFirmaLotes() + "].");
		evento.setFechaEvento(LocalDateTime.now());
		historialService.save(evento);
		movimientoStockService
				.save(PiezaStockLoteMovimiento.builder().piezaStockLote(piezaLoteStock).fecha(java.time.LocalDate.now())
						.cantidad(asignacion.getCantidad()).origen(OrigenMovimientoStock.RESERVA_PEDIDO)
						.observacion("Reserva " + reserva.getId() + " para OF " + of.getNumeroOT()).build());
	}

	@Override
	public List<PiezaStockLoteDisponibleDTO> listarStockDisponible(Long idPieza) {
		return piezaStockService.listarPorPieza(idPieza).stream().map(saldo -> {
			int disponible = Math.max(0, saldo.getStockDisponible());
			if (disponible == 0)
				return null;
			PiezaStockLoteDisponibleDTO dto = new PiezaStockLoteDisponibleDTO();
			dto.setIdPiezaStockLote(saldo.getId());
			dto.setCantidadDisponible(disponible);
			dto.setFirmaLotes(saldo.getFirmaLotes());
			return dto;
		}).filter(java.util.Objects::nonNull).collect(Collectors.toList());
	}

	@Override
	public List<PiezaLoteFabricacionDTO> listarPiezasProducidasAsociadasAlLote(Long idLote) {
		loteService.get(idLote);
		return ordenFabricacionEntregaService.listarPiezasProducidasAsociadasAlLote(idLote);
	}

	@Override
	@Transactional
	public void asignar(Long idOrdenFabricacion, OrdenFabricacionAsignacionDTO asignacion) {

		OrdenFabricacion ordenFabricacion = this.service.get(idOrdenFabricacion);
		if (ordenFabricacion.getEstado() == EstadoOrdenFabricacion.CANCELADA
				|| ordenFabricacion.getEstado() == EstadoOrdenFabricacion.FACTURADA)
			throw new ErrorValidationException("No se puede asignar una OF cancelada o facturada", null);

		// Guardamos la asignación anterior
		Long idSectorAnterior = ordenFabricacion.getSector() != null ? ordenFabricacion.getSector().getId() : null;

		Long idMaquinaAnterior = ordenFabricacion.getMaquina() != null ? ordenFabricacion.getMaquina().getId() : null;

		// Aplicamos la nueva asignación
		if (asignacion.getIdMaquina() != null) {
			ordenFabricacion.setMaquina(MaquinaFabrica.ofId(asignacion.getIdMaquina()));
		}

		if (asignacion.getIdUsuario() != null) {

			ordenFabricacion.setOperario(usuarioService.get(asignacion.getIdUsuario()));
		}

		if (asignacion.getIdSector() != null) {
			ordenFabricacion.setSector(SectorFabrica.ofId(asignacion.getIdSector()));
		} else {
			ordenFabricacion.setSector(null);
		}

		// Nueva asignación
		Long idSectorNuevo = ordenFabricacion.getSector() != null ? ordenFabricacion.getSector().getId() : null;

		Long idMaquinaNueva = ordenFabricacion.getMaquina() != null ? ordenFabricacion.getMaquina().getId() : null;

		boolean mismaAsignacion = Objects.equals(idSectorAnterior, idSectorNuevo)
				&& Objects.equals(idMaquinaAnterior, idMaquinaNueva);

		// Si cambió de grupo, reacomodamos el grupo anterior
		if (idSectorAnterior != null && !mismaAsignacion) {
			this.service.reordenarGrupo(idSectorAnterior, idMaquinaAnterior, idOrdenFabricacion);
		}

		// Primera asignación o cambio de grupo:
		// la OF queda al final del grupo nuevo
		if (idSectorAnterior == null || !mismaAsignacion) {

			Integer ultimaPosicion = this.service.obtenerUltimaPosicion(idSectorNuevo, idMaquinaNueva);

			ordenFabricacion.setPosicion(ultimaPosicion + 1);
		}

		int cubierta = reservaStockService.cantidadCubiertaPorOrdenFabricacion(idOrdenFabricacion);
		ordenFabricacion.setEstado(cubierta >= ordenFabricacion.getOrdenCompraDetallePedido().getCantidad()
				? EstadoOrdenFabricacion.FINALIZADA
				: EstadoOrdenFabricacion.EN_PROCESO);

		this.service.update(ordenFabricacion);

		OrdenCompra ordenCompra = ordenFabricacion.getOrdenCompraDetallePedido().getOrdenCompraDetalle()
				.getOrdenCompra();

		ordenCompra.setEstado(service.calcularEstadoOrdenCompra(ordenCompra.getId()));

		this.ordenCompraService.update(ordenCompra);
	}

	@Override
	public OrdenTrabajoResponseDTO generarOrdenTrabajo(Long idOrdenFabricacion) {

		OrdenFabricacion ordenFabricacion = this.service.get(idOrdenFabricacion);

		List<OrdenTrabajoEntregaDTO> entregas = new ArrayList<OrdenTrabajoEntregaDTO>();

		ordenFabricacion.getEntregas().stream().forEach(entrega -> {
			boolean primero = true;
			Iterator<Lote> iterator = entrega.getLotes().iterator();
			int efectiva = entrega.getCantidad();
			if (!iterator.hasNext()) {
				OrdenTrabajoEntregaDTO ote = new OrdenTrabajoEntregaDTO();
				ote.setFecha(entrega.getFecha());
				ote.setCantidad(efectiva);
				ote.setUsuario(entrega.getOperario().getNombreApellido());
				entregas.add(ote);
			}
			while (iterator.hasNext()) {
				Lote lote = iterator.next();
				OrdenTrabajoEntregaDTO ote = new OrdenTrabajoEntregaDTO();
				if (primero) {
					ote.setFecha(entrega.getFecha());
					ote.setCantidad(efectiva);
				}
				ote.setLote(lote.getNroLote());
				ote.setUsuario(entrega.getOperario().getNombreApellido());
				entregas.add(ote);
				primero = false;
			}
		});

		// entregas.sort(Comparator.comparing(OrdenTrabajoEntregaDTO::getFecha));

		OrdenCompra oc = ordenFabricacion.getOrdenCompraDetallePedido().getOrdenCompraDetalle().getOrdenCompra();

		Cliente clienteOF = oc.getCliente();
		String cliente = clienteOF.getNombre();
		String fechaEmision = ordenFabricacion.getFecha().format(DateUtils.DATE_TIME_FORMATTER_DD_MM_YYYY);
		String fechaEntrega = ordenFabricacion.getOrdenCompraDetallePedido().getFechaEntregaSolicitada()
				.format(DateUtils.DATE_TIME_FORMATTER_DD_MM_YYYY);
		String numeroOT = ordenFabricacion.getNumeroOT();
		String observaciones = oc.getObservaciones();
		String ordenCompra = oc.getComprobante();
		String prensa = ordenFabricacion.getMaquina() != null ? ordenFabricacion.getMaquina().getNombre() : "";
		List<String> mediosEnvio = StringUtils.isNotBlank(oc.getMediosEnvio())
				? Arrays.asList(oc.getMediosEnvio().split(","))
				: new ArrayList<String>();
		CabeceraOrdenTabajoDTO cabecera = CabeceraOrdenTabajoDTO.builder().cliente(cliente).fechaEmision(fechaEmision)
				.fechaEntrega(fechaEntrega).numeroOt(numeroOT).observaciones(observaciones).oc(ordenCompra)
				.prensa(prensa).sector(ordenFabricacion.getSector().getNombre())
				.telefonoCliente(clienteOF.getTelefono()).emailCliente(clienteOF.getEmail())
				.tipoDespacho(oc.getTipoDespacho())
				.empresaTransporte(oc.getEmpresaTransporte() != null ? oc.getEmpresaTransporte().getNombre() : null)
				.mediosEnvio(mediosEnvio)
				.domicilioEnvio(oc.getDomicilioEnvio() != null ? oc.getDomicilioEnvio().getDomicilio() : null).build();

		Pieza piezaOC = ordenFabricacion.getOrdenCompraDetallePedido().getOrdenCompraDetalle().getPieza();

		List<PiezaControl> controlesConfigurados = piezaControlService.listControlesConfigurados(piezaOC.getId());

		List<ControlCalidadDTO> controles = new ArrayList<>();
		controlesConfigurados.stream().forEach(c -> {
			ControlCalidadDTO cc = ControlCalidadDTO.builder().tipo(c.getTipo().getLabel()).valor(c.getControl())
					.build();
			controles.add(cc);
		});

		Molde moldePieza = piezaOC.getMoldes().iterator().next().getMolde();

		Integer cantidad = ordenFabricacion.getOrdenCompraDetallePedido().getCantidad();
		List<PiezaStockReserva> reservasOF = reservaStockService.listarPorOrdenFabricacion(idOrdenFabricacion);
		Integer cantidadStock = reservasOF.stream()
				.filter(r -> r.getEstado() != EstadoReserva.CANCELADA && r.getEntregaOrigen() == null)
				.mapToInt(PiezaStockReserva::getCantidadReservada).sum();
		Integer cantidadEntregada = reservasOF.stream()
				.filter(r -> r.getEstado() != EstadoReserva.CANCELADA && r.getEntregaOrigen() != null)
				.mapToInt(PiezaStockReserva::getCantidadReservada).sum();
		Integer cantidadAFabricar = Math.max(0, cantidad - cantidadStock - cantidadEntregada);
		Formula formulaPieza = piezaOC.getDetalleFormula().getFormula();
		String formula = formulaPieza.getNombre();
		String hojaProceso = piezaOC.getProceso().getHojaProceso();
		String pieza = piezaOC.getDescripcion();
		String material = formulaPieza.getMaterial().getNombre();
		String molde = moldePieza.getCodigo();
		String postCuraValor = piezaOC.getProceso().getPostCura();
		String postCura = StringUtils.isNoneBlank(postCuraValor)
				&& !postCuraValor.trim().toUpperCase().equalsIgnoreCase("NO") ? postCuraValor : null;

		String plano = "";

		if (piezaOC.getPlanos() != null && !piezaOC.getPlanos().isEmpty()) {
			PiezaPlano piezaPlano = piezaOC.getPlanos().iterator().next();
			plano = piezaPlano.getCodigo() + "/" + piezaPlano.getRevision();
		}

		String ubicacion = moldePieza.getUbicacion();

		String identificacion = piezaOC.getProceso().getTerminacion() != null
				? piezaOC.getProceso().getTerminacion().getIdentificacion()
				: null;
		String observacionPieza = ordenFabricacion.getOrdenCompraDetallePedido().getOrdenCompraDetalle()
				.getObservacion();

		Cotizacion vigente = ordenFabricacion.getOrdenCompraDetallePedido().getOrdenCompraDetalle().getCotizacion();

		String observacionDescuento = ordenFabricacion.getOrdenCompraDetallePedido().getOrdenCompraDetalle()
				.getObservacionDescuento();
		Double descuento = ordenFabricacion.getOrdenCompraDetallePedido().getOrdenCompraDetalle().getDescuento();
		String descuentoString = null;
		String descuentoValorString = null;
		if (descuento != null) {
			descuentoString = descuento.toString();
			Double valor = vigente.getValor() - (vigente.getValor() * descuento / 100D);
			descuentoValorString = valor.toString();
		}

		Troquel troquel = piezaOC.getProceso().getTroquel();
		String troquelNombre = troquel != null ? troquel.getNombre() : null;

		String insumos = null;
		if (piezaOC.getInsumos() != null && !piezaOC.getInsumos().isEmpty()) {
			insumos = piezaOC.getInsumos().stream()
					.map(it -> it.getInsumo().getNombre() + " (" + it.getInsumo().getTipo().getNombre() + ")")
					.collect(Collectors.joining(" - "));
		}
		String codigoPiezaCliente = vigente.getPiezaCliente() != null
				? vigente.getPiezaCliente().getNombrePiezaPersonalizado()
				: null;
		String tituloPieza = StringUtils.isNotBlank(codigoPiezaCliente) ? pieza + " - " + codigoPiezaCliente : pieza;

		ItemOrdenTrabajoDTO item = ItemOrdenTrabajoDTO.builder().identficacion(identificacion).cantidadTotal(cantidad)
				.cantidadStock(cantidadStock).cantidadEntregada(cantidadEntregada).cantidadAFabricar(cantidadAFabricar)
				.troquel(troquelNombre).controlCalidad(controles).formula(formula).hp(hojaProceso).idItem(1L)
				.material(material).matriz(molde).pc(postCura).planoRev(plano).observacion(observacionPieza)
				.insumos(insumos).descuento(descuentoString).precioDescuento(descuentoValorString)
				.observacionDescuento(observacionDescuento).titulo(tituloPieza).ubicacion(ubicacion)
				.cotizacion(vigente.getValor()).fechaCotizacion(vigente.getFecha()).entregas(entregas).build();
		List<ItemOrdenTrabajoDTO> itemsOT = new ArrayList<ItemOrdenTrabajoDTO>();
		itemsOT.add(item);

		OrdenTrabajoResponseDTO ot = OrdenTrabajoResponseDTO.builder().cabecera(cabecera).items(itemsOT).build();

		return ot;

	}

	@Override
	public Map<String, List<OrdenTrabajoResumenDTO>> generarResumen(List<Long> ids) {
		List<OrdenFabricacion> ordenes = this.service.listByIds(ids);
		Map<String, List<OrdenTrabajoResumenDTO>> resumenes = new HashMap<String, List<OrdenTrabajoResumenDTO>>();

		ordenes.forEach(o -> {

			String cliente = o.getOrdenCompraDetallePedido().getOrdenCompraDetalle().getOrdenCompra().getCliente()
					.getNombre();

			List<OrdenTrabajoResumenDTO> listaPorCliente = resumenes.get(cliente);
			if (listaPorCliente == null)
				listaPorCliente = new ArrayList<OrdenTrabajoResumenDTO>();

			Integer cantidadFabricada = o.getEntregas().stream().mapToInt(OrdenFabricacionEntrega::getCantidad).sum();

			OrdenCompraDetallePedido detalle = o.getOrdenCompraDetallePedido();
			Pieza pieza = detalle.getOrdenCompraDetalle().getPieza();
			OrdenTrabajoResumenDTO resumen = OrdenTrabajoResumenDTO.builder().cantidadFabricada(cantidadFabricada)
					.cantidadTotal(detalle.getCantidad())
					.fechaEntrega(DateUtils.toString(detalle.getFechaEntregaSolicitada(), "dd/MM/yyyy"))
					.formula(pieza.getDetalleFormula().getFormula().getNombre()).hp(pieza.getProceso().getHojaProceso())
					.maquina(o.getMaquina() != null ? o.getMaquina().getNombre() : "").numeroOt(o.getNumeroOT())
					.ordenCompra(
							o.getOrdenCompraDetallePedido().getOrdenCompraDetalle().getOrdenCompra().getComprobante())
					.pieza(pieza.getDenominacion()).sector(o.getSector() != null ? o.getSector().getNombre() : "")
					.build();
			listaPorCliente.add(resumen);
			resumenes.put(cliente, listaPorCliente);
		});
		return resumenes;

	}

	@Override
	public void reordenar(Long idOrdenFabricacion, Integer nuevaPosicion) {
		this.service.reordenar(idOrdenFabricacion, nuevaPosicion);
	}

	@Override
	@Transactional
	public void facturar(Long idOrdenFabricacion) {
		OrdenFabricacion of = service.get(idOrdenFabricacion);
		if (of.getEstado() != EstadoOrdenFabricacion.FINALIZADA)
			throw new ErrorValidationException("Solo se puede facturar una OF finalizada", null);
		List<PiezaStockReserva> reservas = reservaStockService.listarActivasPorOrdenFabricacion(idOrdenFabricacion);
		if (reservas.isEmpty())
			throw new ErrorValidationException("La OF no tiene reservas activas para consumir", null);
		Pieza pieza = of.getOrdenCompraDetallePedido().getOrdenCompraDetalle().getPieza();
		for (PiezaStockReserva reserva : reservas) {
			PiezaStockLote saldo = piezaStockService.buscar(pieza.getId(), reserva.getLotes());
			piezaStockService.aplicarVariaciones(saldo.getId(), -reserva.getCantidadReservada(),
					-reserva.getCantidadReservada());
			reserva.setEstado(EstadoReserva.CONSUMIDA);
			reservaStockService.update(reserva);
			movimientoStockService.save(PiezaStockLoteMovimiento.builder().piezaStockLote(saldo)
					.ordenFabricacionEntrega(reserva.getEntregaOrigen()).cantidad(reserva.getCantidadReservada())
					.fecha(java.time.LocalDate.now()).origen(OrigenMovimientoStock.FACTURACION)
					.observacion("Consumo por facturación de OF " + of.getNumeroOT()).build());
		}
		of.setEstado(EstadoOrdenFabricacion.FACTURADA);
		service.update(of);
		OrdenCompra oc = of.getOrdenCompraDetallePedido().getOrdenCompraDetalle().getOrdenCompra();
		oc.setEstado(service.calcularEstadoOrdenCompra(oc.getId()));
		ordenCompraService.update(oc);
		registrarHistorial(oc, TipoEventoOrdenCompra.FACTURACION_REGISTRADA, "ORDEN_FABRICACION", of.getId(),
				"Se consumieron " + reservas.stream().mapToInt(PiezaStockReserva::getCantidadReservada).sum()
						+ " piezas de stock al facturar la OF " + of.getNumeroOT() + ".");
	}

	@Override
	@Transactional
	public void cancelarPedido(Long idPedido, String motivo) {

		// Validacion del motivo de cancelacion
		if (StringUtils.isBlank(motivo))
			throw new ErrorValidationException("Debe indicar el motivo de cancelación", null);

		// Obtengo la Orden de Fabricacion asociado a la orden de compra detalle pedido.
		OrdenFabricacion of = service.buscarPorPedidoId(idPedido)
				.orElseThrow(() -> new ErrorValidationException("El pedido no tiene una OF asociada", null));

		// Valido que no este cancelada y ni facturada
		// FIXME ajustar a futuros estados
		if (of.getEstado() == EstadoOrdenFabricacion.CANCELADA)
			throw new ErrorValidationException("La OF asociada al pedido ya está cancelada", null);
		if (of.getEstado() == EstadoOrdenFabricacion.FACTURADA)
			throw new ErrorValidationException("No se puede cancelar un pedido con una OF facturada", null);

		cancelarReservas(of, motivo);

		of.setEstado(EstadoOrdenFabricacion.CANCELADA);
		service.update(of);
		OrdenCompra oc = of.getOrdenCompraDetallePedido().getOrdenCompraDetalle().getOrdenCompra();
		List<OrdenFabricacion> ordenes = service.listarPorOrdenCompra(oc.getId());
		if (ordenes.stream().allMatch(x -> x.getEstado() == EstadoOrdenFabricacion.CANCELADA))
			oc.setEstado(EstadoOrdenCompra.CANCELADA);
		else
			oc.setEstado(service.calcularEstadoOrdenCompra(oc.getId()));
		ordenCompraService.update(oc);
		registrarHistorial(oc, TipoEventoOrdenCompra.ITEM_CANCELADO, "ORDEN_COMPRA_DETALLE_PEDIDO", idPedido,
				motivo.trim(), "Se canceló el pedido y su OF " + of.getNumeroOT() + ".");
	}

	@Override
	@Transactional
	public void cancelarPieza(Long idOrdenCompraDetalle, String motivo) {
		if (StringUtils.isBlank(motivo))
			throw new ErrorValidationException("Debe indicar el motivo de cancelación", null);
		List<OrdenFabricacion> relacionadas = service
				.listarPorOrdenCompra(ordenCompraDetalleService.get(idOrdenCompraDetalle).getOrdenCompra().getId())
				.stream().filter(of -> of.getOrdenCompraDetallePedido().getOrdenCompraDetalle().getId()
						.equals(idOrdenCompraDetalle))
				.collect(Collectors.toList());
		if (relacionadas.isEmpty())
			throw new ErrorValidationException("La pieza no tiene OF generadas", null);
		for (OrdenFabricacion of : relacionadas) {
			if (of.getEstado() != EstadoOrdenFabricacion.CANCELADA)
				cancelarPedido(of.getOrdenCompraDetallePedido().getId(), motivo);
		}
	}

	@Override
	@Transactional
	public void cancelarOrdenCompra(Long idOrdenCompra, String motivo) {
		if (StringUtils.isBlank(motivo))
			throw new ErrorValidationException("Debe indicar el motivo de cancelación", null);
		List<OrdenFabricacion> ordenes = service.listarPorOrdenCompra(idOrdenCompra);
		if (ordenes.isEmpty())
			throw new ErrorValidationException("No se puede cancelar la OC antes de generar sus OF", null);
		if (ordenes.stream().anyMatch(of -> of.getEstado() == EstadoOrdenFabricacion.FACTURADA))
			throw new ErrorValidationException("No se puede cancelar una OC que contiene OF facturadas", null);
		for (OrdenFabricacion of : ordenes) {
			if (of.getEstado() != EstadoOrdenFabricacion.CANCELADA) {
				cancelarReservas(of, motivo);
				of.setEstado(EstadoOrdenFabricacion.CANCELADA);
				service.update(of);
			}
		}
		OrdenCompra oc = ordenCompraService.get(idOrdenCompra);
		oc.setEstado(EstadoOrdenCompra.CANCELADA);
		ordenCompraService.update(oc);
		registrarHistorial(oc, TipoEventoOrdenCompra.ORDEN_COMPRA_CANCELADA, "ORDEN_COMPRA", idOrdenCompra,
				motivo.trim(),
				"Cancelación total de la OC y cancelación de sus OF. Las reservas activas fueron liberadas.");
	}

	@Override
	@Transactional
	public void modificarCantidadPedido(Long idPedido, Integer cantidadNueva, String motivo) {

		// Valido que el motivo tenga cargado algo
		if (StringUtils.isBlank(motivo))
			throw new ErrorValidationException("Debe indicar el motivo de modificación", null);

		// Validaciones de cantidades
		if (cantidadNueva == null || cantidadNueva < 0)
			throw new ErrorValidationException("La cantidad solicitada no puede ser negativa", null);

		// Obtengo la orden de fabricacion asociada
		OrdenFabricacion of = service.buscarPorPedidoId(idPedido)
				.orElseThrow(() -> new ErrorValidationException("El pedido no tiene una OF asociada", null));

		// Valido el estado de la OF
		if (of.getEstado() == EstadoOrdenFabricacion.CANCELADA || of.getEstado() == EstadoOrdenFabricacion.FACTURADA)
			throw new ErrorValidationException("No se puede modificar la cantidad de un pedido cancelado o facturado",
					null);

		// Obtengo el pedido de la OF
		OrdenCompraDetallePedido pedido = of.getOrdenCompraDetallePedido();

		// Obtengo la OC
		OrdenCompra oc = pedido.getOrdenCompraDetalle().getOrdenCompra();

		// Obtengo la cantidad anterior
		int anterior = pedido.getCantidad();

		// Si me bajaron la cantidad
		if (cantidadNueva < anterior) {

			// Calculo cuantas piezas tengo que liberar
			int aLiberar = anterior - cantidadNueva;

			// Busco las reservas asociadas y las ordeno primero por las que son de una
			// entrega y luego por fecha y luego por id
			List<PiezaStockReserva> reservas = reservaStockService.listarActivasPorOrdenFabricacion(of.getId()).stream()
					.sorted(java.util.Comparator.comparing((PiezaStockReserva r) -> r.getEntregaOrigen() == null)
							.thenComparing(PiezaStockReserva::getFechaReserva).thenComparing(PiezaStockReserva::getId))
					.collect(Collectors.toList());

			PiezaStockReserva ultimaReserva = null;

			int restante = aLiberar;

			// Obtengo la pieza
			Pieza pieza = pedido.getOrdenCompraDetalle().getPieza();

			// Por cada reserva
			for (PiezaStockReserva reserva : reservas) {

				// Si lo restante a liberar es menor o igual a 0 ya corto
				if (restante <= 0)
					break;
				
				ultimaReserva = reserva;
				
				// Busco el saldo de la pieza tomando en cuenta los lotes
				PiezaStockLote saldo = piezaStockService.buscar(pieza.getId(), reserva.getLotes());
				
				// Actualizo el stock reservado quitando de la reserva
				piezaStockService.modificarStockReservado(saldo.getId(), -reserva.getCantidadReservada());
				
				// cancelo y actualizo la reserva
				reserva.setEstado(EstadoReserva.CANCELADA);
				reservaStockService.update(reserva);

				// Registro el movimiento
				movimientoStockService.save(PiezaStockLoteMovimiento.builder().piezaStockLote(saldo)
						.ordenFabricacionEntrega(reserva.getEntregaOrigen()).cantidad(reserva.getCantidadReservada())
						.fecha(java.time.LocalDate.now()).origen(OrigenMovimientoStock.CANCELACION_RESERVA)
						.observacion(motivo.trim()).build());
				
				// Calculo el restante				
				restante -= reserva.getCantidadReservada();
			}
			
			// Si me pase de lo que tengo que devolver y tengo la ultima reserva
			if (restante < 0 && ultimaReserva != null) {
				
				// Calculo que tengo que volver a asingar o conservar
				int conservar = -restante;
				
				// Busco el stock por pieza y lote
				PiezaStockLote saldo = piezaStockService.buscar(pieza.getId(), ultimaReserva.getLotes());
				
				// Armo la nueva reserva
				PiezaStockReserva nueva = new PiezaStockReserva();
				nueva.setOrdenFabricacion(of);
				nueva.setEntregaOrigen(ultimaReserva.getEntregaOrigen());
				nueva.setLotes(ultimaReserva.getLotes());
				nueva.setCantidadReservada(conservar);
				nueva.setFechaReserva(LocalDateTime.now());
				nueva.setEstado(EstadoReserva.ACTIVA);
				reservaStockService.save(nueva);
				
				// Actualizo el stock
				piezaStockService.modificarStockReservado(saldo.getId(), conservar);
				
				// Registro el movimiento
				movimientoStockService.save(PiezaStockLoteMovimiento.builder().piezaStockLote(saldo)
						.ordenFabricacionEntrega(nueva.getEntregaOrigen()).cantidad(conservar)
						.fecha(java.time.LocalDate.now()).origen(OrigenMovimientoStock.RESERVA_PEDIDO)
						.observacion("Reserva remanente luego de modificar cantidad: " + motivo.trim()).build());
			}
		}
		
		// Actualizo la cantidad nueva
		pedido.setCantidad(cantidadNueva);
		ordenCompraDetallePedidoService.update(pedido);

		// Actualizo estado
		if (of.getEstado() != EstadoOrdenFabricacion.CANCELADA) {
			int cubierta = reservaStockService.cantidadCubiertaPorOrdenFabricacion(of.getId());
			of.setEstado(
					cubierta >= cantidadNueva ? EstadoOrdenFabricacion.FINALIZADA : EstadoOrdenFabricacion.EN_PROCESO);
			service.update(of);
		}
		
		// Marco la OC como modificada
		oc.setModificada(true);
		oc.setEstado(service.calcularEstadoOrdenCompra(oc.getId()));
		ordenCompraService.update(oc);
		
		// Registro historial de movimiento
		registrarHistorial(oc, TipoEventoOrdenCompra.CANTIDAD_MODIFICADA, "ORDEN_COMPRA_DETALLE_PEDIDO", idPedido,
				motivo.trim(), "Cantidad solicitada modificada de " + anterior + " a " + cantidadNueva + ".");
	}

	private void cancelarReservas(OrdenFabricacion of, String motivo) {

		// Reviso que las reservas asociadas no esten consumidas, es decir que la OF
		// haya pasado a otra instancia posterior
		if (reservaStockService.listarPorOrdenFabricacion(of.getId()).stream()
				.anyMatch(r -> r.getEstado() == EstadoReserva.CONSUMIDA))
			throw new ErrorValidationException("No se puede cancelar una OF con reservas consumidas", null);

		// Obtengo la pieza
		Pieza pieza = of.getOrdenCompraDetallePedido().getOrdenCompraDetalle().getPieza();

		// Busco las reservas
		List<PiezaStockReserva> listarActivasPorOrdenFabricacion = reservaStockService
				.listarActivasPorOrdenFabricacion(of.getId());

		// Por cada reserva
		for (PiezaStockReserva reserva : listarActivasPorOrdenFabricacion) {

			// Obtengo el saldo en base al id y los lotes
			PiezaStockLote saldo = piezaStockService.buscar(pieza.getId(), reserva.getLotes());

			// Actualizo el stock reservado quitando la reerva
			piezaStockService.modificarStockReservado(saldo.getId(), -reserva.getCantidadReservada());

			// Cancelo la reserva
			reserva.setEstado(EstadoReserva.CANCELADA);

			// Actualizo
			reservaStockService.update(reserva);

			// Registro movimiento de stock
			movimientoStockService.save(PiezaStockLoteMovimiento.builder().piezaStockLote(saldo)
					.ordenFabricacionEntrega(reserva.getEntregaOrigen()).cantidad(reserva.getCantidadReservada())
					.fecha(java.time.LocalDate.now()).origen(OrigenMovimientoStock.CANCELACION_RESERVA)
					.observacion(motivo.trim()).build());
		}
	}

	private void registrarHistorial(OrdenCompra oc, TipoEventoOrdenCompra tipo, String entidad, Long idEntidad,
			String detalle) {
		registrarHistorial(oc, tipo, entidad, idEntidad, "", detalle);
	}

	private void registrarHistorial(OrdenCompra oc, TipoEventoOrdenCompra tipo, String entidad, Long idEntidad,
			String motivo, String detalle) {
		OrdenCompraHistorial h = new OrdenCompraHistorial();
		h.setOrdenCompra(oc);
		h.setTipoEvento(tipo);
		h.setTipoEntidad(entidad);
		h.setIdEntidad(idEntidad);
		h.setMotivo(motivo);
		h.setDetalle(detalle);
		h.setFechaEvento(LocalDateTime.now());
		historialService.save(h);
	}
}
