
package ar.com.avaco.nitrophyl.epservice;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.PiezaStockDTO;
import ar.com.avaco.nitrophyl.dto.PiezaStockResumenDTO;

public interface PiezaStockEPService extends CRUDEPService<Long, PiezaStockDTO> {
	PiezaStockResumenDTO obtenerResumen(Long idPieza);
	java.util.List<PiezaStockDTO> listarPorLote(String codigoLote);


}
