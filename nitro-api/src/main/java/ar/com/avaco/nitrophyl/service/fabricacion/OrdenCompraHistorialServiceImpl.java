package ar.com.avaco.nitrophyl.service.fabricacion;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import ar.com.avaco.fwk.core.component.service.NJBaseService;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenCompraHistorial;
import ar.com.avaco.nitrophyl.repository.fabricacion.OrdenCompraHistorialRepository;

@Service("ordenCompraHistorialService")
public class OrdenCompraHistorialServiceImpl
		extends NJBaseService<Long, OrdenCompraHistorial, OrdenCompraHistorialRepository>
		implements OrdenCompraHistorialService {

	@Resource(name = "ordenCompraHistorialRepository")
	void setRepository(OrdenCompraHistorialRepository repository) { this.repository = repository; }

	@Override
	public java.util.List<OrdenCompraHistorial> listarPorOrdenCompra(Long idOrdenCompra) {
		return repository.findByOrdenCompraIdOrderByFechaEventoDesc(idOrdenCompra);
	}
}
