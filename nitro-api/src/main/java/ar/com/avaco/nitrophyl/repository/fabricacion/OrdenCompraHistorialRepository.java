package ar.com.avaco.nitrophyl.repository.fabricacion;

import ar.com.avaco.fwk.core.component.repository.NJRepository;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenCompraHistorial;

public interface OrdenCompraHistorialRepository extends NJRepository<Long, OrdenCompraHistorial>, OrdenCompraHistorialRepositoryCustom {
	java.util.List<OrdenCompraHistorial> findByOrdenCompraIdOrderByFechaEventoDesc(Long idOrdenCompra);
}
