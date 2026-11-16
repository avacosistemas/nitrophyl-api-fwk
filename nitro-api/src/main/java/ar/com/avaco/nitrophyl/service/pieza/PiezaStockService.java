package ar.com.avaco.nitrophyl.service.pieza;

import ar.com.avaco.fwk.core.component.service.NJService;
import java.util.List;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStockLote;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStock;

public interface PiezaStockService extends NJService<Long, PiezaStockLote> {

	PiezaStockLote ingresar(Long idPieza, String lotes, Integer cantidad);

	PiezaStock obtenerCabecera(Long idPieza);

	PiezaStockLote buscar(Long idPieza, String lotes);

	void modificarStockReservado(Long idPiezaStockLote, Integer variacion);

	void aplicarVariaciones(Long idPiezaStockLote, Integer variacionFisico, Integer variacionReservado);

	List<PiezaStockLote> listarPorPieza(Long idPieza);

	List<PiezaStockLote> listarPorLote(String codigoLote);

}
