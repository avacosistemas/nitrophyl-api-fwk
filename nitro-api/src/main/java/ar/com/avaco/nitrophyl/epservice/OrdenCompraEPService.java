package ar.com.avaco.nitrophyl.epservice;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.OrdenCompraDTO;

public interface OrdenCompraEPService extends CRUDEPService<Long, OrdenCompraDTO> {

	void cancelar(Long id, String motivo);

	void generarOrdenFabrica(Long idOC);

}