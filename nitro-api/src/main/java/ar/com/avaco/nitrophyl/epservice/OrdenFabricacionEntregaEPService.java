package ar.com.avaco.nitrophyl.epservice;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionEntregaDTO;

public interface OrdenFabricacionEntregaEPService extends CRUDEPService<Long, OrdenFabricacionEntregaDTO> {

	void registrarEntrega(Long idOrdenFabricacion, OrdenFabricacionEntregaDTO entrega);

}