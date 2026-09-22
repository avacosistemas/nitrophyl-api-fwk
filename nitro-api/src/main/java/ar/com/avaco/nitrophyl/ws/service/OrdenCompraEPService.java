package ar.com.avaco.nitrophyl.ws.service;

import ar.com.avaco.nitrophyl.ws.dto.OrdenCompraDTO;
import ar.com.avaco.fwk.core.component.epservice.CRUDAuditableEPService;

public interface OrdenCompraEPService extends CRUDAuditableEPService<Long, OrdenCompraDTO> {

	void cancelar(Long id, String motivo);

	void generarOrdenFabrica(Long idOC);

}