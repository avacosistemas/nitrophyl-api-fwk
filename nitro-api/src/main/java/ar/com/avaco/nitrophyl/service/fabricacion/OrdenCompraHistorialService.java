package ar.com.avaco.nitrophyl.service.fabricacion;

import ar.com.avaco.fwk.core.component.service.NJService;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenCompraHistorial;

public interface OrdenCompraHistorialService extends NJService<Long, OrdenCompraHistorial> {
	java.util.List<OrdenCompraHistorial> listarPorOrdenCompra(Long idOrdenCompra);
}
