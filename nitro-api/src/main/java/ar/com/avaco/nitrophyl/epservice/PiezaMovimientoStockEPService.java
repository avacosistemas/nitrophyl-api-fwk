
package ar.com.avaco.nitrophyl.epservice;

import java.time.LocalDate;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.PiezaMovimientoStockDTO;

public interface PiezaMovimientoStockEPService extends CRUDEPService<Long, PiezaMovimientoStockDTO> {

	void registrarIngresoManual(Long idProducto, Integer cantidad, LocalDate fecha, String observacion);

}
