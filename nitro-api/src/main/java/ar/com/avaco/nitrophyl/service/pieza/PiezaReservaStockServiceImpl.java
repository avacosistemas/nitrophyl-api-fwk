package ar.com.avaco.nitrophyl.service.pieza;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ar.com.avaco.fwk.core.component.service.NJBaseService;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStockReserva;
import ar.com.avaco.nitrophyl.repository.pieza.PiezaReservaStockRepository;
import ar.com.avaco.nitrophyl.domain.entities.pieza.EstadoReserva;
import java.util.List;

@Service("piezaReservaStockService")
public class PiezaReservaStockServiceImpl extends NJBaseService<Long, PiezaStockReserva, PiezaReservaStockRepository>
		implements PiezaReservaStockService {

	@Resource(name = "piezaReservaStockRepository")
	void setRepository(PiezaReservaStockRepository repository) {
		this.repository = repository;
	}

	@Override
	public Integer cantidadActivaPorOrdenFabricacion(Long idOrdenFabricacion) {
		return repository.cantidadActivaPorOrdenFabricacion(idOrdenFabricacion, EstadoReserva.ACTIVA);
	}

	@Override
	public Integer cantidadActivaPorOrdenFabricacion(Long idOrdenFabricacion, EstadoReserva estado) {
		return repository.cantidadActivaPorOrdenFabricacion(idOrdenFabricacion, estado);
	}

	@Override
	public Integer cantidadCubiertaPorOrdenFabricacion(Long idOrdenFabricacion) {
		return repository.cantidadCubiertaPorOrdenFabricacion(idOrdenFabricacion);
	}

	@Override
	public List<PiezaStockReserva> listarPorOrdenFabricacion(Long idOrdenFabricacion) {
		return repository.findByOrdenFabricacionIdOrderByFechaReservaAscIdAsc(idOrdenFabricacion);
	}

	@Override
	public List<PiezaStockReserva> listarActivasPorOrdenFabricacion(Long idOrdenFabricacion) {
		return repository.findByOrdenFabricacionIdAndEstadoOrderByFechaReservaAscIdAsc(idOrdenFabricacion,
				EstadoReserva.ACTIVA);
	}

	@Override
	public List<PiezaStockReserva> listarActivasPorEntrega(Long idEntrega) {
		return repository.findByEntregaOrigenIdAndEstado(idEntrega, EstadoReserva.ACTIVA);
	}
}
