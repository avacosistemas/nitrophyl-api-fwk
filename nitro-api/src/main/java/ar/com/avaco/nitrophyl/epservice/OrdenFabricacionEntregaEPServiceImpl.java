package ar.com.avaco.nitrophyl.epservice;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.avaco.fwk.core.component.epservice.CRUDAuditableEPBaseService;
import ar.com.avaco.fwk.security.service.UsuarioService;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.EstadoOrdenCompra;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.EstadoOrdenFabricacion;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenCompra;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenFabricacion;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenFabricacionEntrega;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionEntregaDTO;
import ar.com.avaco.nitrophyl.service.fabricacion.OrdenCompraService;
import ar.com.avaco.nitrophyl.service.fabricacion.OrdenFabricacionEntregaService;
import ar.com.avaco.nitrophyl.service.fabricacion.OrdenFabricacionService;
import ar.com.avaco.nitrophyl.service.lote.LoteService;

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

	public OrdenFabricacionEntregaEPServiceImpl() {
		super(OrdenFabricacionEntrega.class, OrdenFabricacionEntregaDTO.class);
	}

	@Override
	public void registrarEntrega(Long idOrdenFabricacion, OrdenFabricacionEntregaDTO entrega) {

		// Obtengo la OF
		OrdenFabricacion ordenFabricacion = this.ordenFabricacionService.get(idOrdenFabricacion);

		// Genero la entrega
		OrdenFabricacionEntrega generarEntrega = generarEntrega(idOrdenFabricacion, entrega);

		// Le asocio la OF
		generarEntrega.setOrdenFabricacion(ordenFabricacion);

		this.service.save(generarEntrega);

		// Calculo el total de las entregas de la OF
		int total = generarEntrega.getCantidad() + ordenFabricacion.getEntregas().stream().mapToInt(OrdenFabricacionEntrega::getCantidad).sum();

		OrdenCompra ordenCompra = ordenFabricacion.getOrdenCompraDetallePedido().getOrdenCompraDetalle()
				.getOrdenCompra();

		// Si esta completa, reviso si las otras ordenes de fabricacion asociadas a la
		// orden de compra estan finalizadas
		if (total == ordenFabricacion.getOrdenCompraDetallePedido().getCantidad()) {
			// Seteo la orden de fabricacion como finalizada
			ordenFabricacion.setEstado(EstadoOrdenFabricacion.FINALIZADA);

			// Busco si todas las otras ordenes de fabricacion estan finalizadas
			if (!this.ordenFabricacionService.hayPendientes(idOrdenFabricacion)) {
				ordenCompra.setEstado(EstadoOrdenCompra.FABRICADA);
			} else {
				ordenCompra.setEstado(EstadoOrdenCompra.EN_PROCESO);
			}

		} else {
			ordenFabricacion.setEstado(EstadoOrdenFabricacion.EN_PROCESO);
		}

		this.ordenFabricacionService.update(ordenFabricacion);
		this.ordenCompraService.update(ordenCompra);
	}

	private OrdenFabricacionEntrega generarEntrega(Long idOrdenFabricacion, OrdenFabricacionEntregaDTO entrega) {
		OrdenFabricacionEntrega ofe = new OrdenFabricacionEntrega();
		ofe.setCantidad(entrega.getCantidad());
		ofe.setFecha(entrega.getFecha());

		entrega.getIdLote().forEach(idlote -> ofe.getLotes().add(loteService.get(idlote)));

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
		OrdenFabricacionEntrega entrega = this.service.get(id);
		OrdenFabricacion ordenFabricacion = entrega.getOrdenFabricacion();
		ordenFabricacion.setEstado(EstadoOrdenFabricacion.EN_PROCESO);
		this.ordenFabricacionService.update(ordenFabricacion);
		super.remove(id);
	}

	@Override
	@Resource(name = "ordenFabricacionEntregaService")
	protected void setService(OrdenFabricacionEntregaService service) {
		this.service = service;
	}
}