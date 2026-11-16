package ar.com.avaco.nitrophyl.service.fabricacion;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import ar.com.avaco.fwk.core.component.service.NJBaseService;
import ar.com.avaco.fwk.core.exception.ErrorValidationException;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.EstadoOrdenCompra;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenCompra;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenCompraHistorial;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.TipoEventoOrdenCompra;
import ar.com.avaco.nitrophyl.repository.fabricacion.OrdenCompraRepository;
import java.time.LocalDateTime;

//@Transactional
@Service("ordenCompraService")
public class OrdenCompraServiceImpl extends NJBaseService<Long, OrdenCompra, OrdenCompraRepository>
		implements OrdenCompraService {

	@javax.annotation.Resource(name = "ordenCompraHistorialService")
	private OrdenCompraHistorialService historialService;


	@Override
	@Transactional
	public void cancelar(Long id, String motivo) {
		if (StringUtils.isBlank(motivo)) throw new ErrorValidationException("Debe indicar el motivo de cancelación", null);
		OrdenCompra ordenCompra = this.get(id);
		if (ordenCompra.getEstado() != null && ordenCompra.getEstado().equals(EstadoOrdenCompra.PENDIENTE)) {
			ordenCompra.setEstado(EstadoOrdenCompra.CANCELADA);
			this.update(ordenCompra);
			OrdenCompraHistorial h = new OrdenCompraHistorial();
			h.setOrdenCompra(ordenCompra);
			h.setTipoEvento(TipoEventoOrdenCompra.ORDEN_COMPRA_CANCELADA);
			h.setTipoEntidad("ORDEN_COMPRA");
			h.setIdEntidad(id);
			h.setMotivo(motivo.trim());
			h.setDetalle("Cancelación total de la orden de compra.");
			h.setFechaEvento(LocalDateTime.now());
			historialService.save(h);
		}
		else
			throw new ErrorValidationException("Para cancelar la OC debe estar en estado Pendiente", null);
	}

	@Resource(name = "ordenCompraRepository")
	void setRepository(OrdenCompraRepository ordenCompraRepository) {
		this.repository = ordenCompraRepository;
	}

}
