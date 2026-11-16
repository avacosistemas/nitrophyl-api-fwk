package ar.com.avaco.nitrophyl.service.pieza;

import ar.com.avaco.fwk.core.component.service.NJService;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStockReserva;
import ar.com.avaco.nitrophyl.domain.entities.pieza.EstadoReserva;
import java.util.List;

public interface PiezaReservaStockService extends NJService<Long, PiezaStockReserva> {

	Integer cantidadActivaPorOrdenFabricacion(Long idOrdenFabricacion);

	Integer cantidadActivaPorOrdenFabricacion(Long idOrdenFabricacion, EstadoReserva estado);

	Integer cantidadCubiertaPorOrdenFabricacion(Long idOrdenFabricacion);

	List<PiezaStockReserva> listarPorOrdenFabricacion(Long idOrdenFabricacion);

	List<PiezaStockReserva> listarActivasPorOrdenFabricacion(Long idOrdenFabricacion);

	List<PiezaStockReserva> listarActivasPorEntrega(Long idEntrega);
}
