package ar.com.avaco.nitrophyl.epservice;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionEntregaDTO;
import ar.com.avaco.nitrophyl.dto.AccionEntregaOrdenFabricacionDTO;

public interface OrdenFabricacionEntregaEPService extends CRUDEPService<Long, OrdenFabricacionEntregaDTO> {

	void registrarEntrega(Long idOrdenFabricacion, OrdenFabricacionEntregaDTO entrega);

	void cancelar(Long idEntrega, AccionEntregaOrdenFabricacionDTO accion);

	void reasignar(Long idEntrega, AccionEntregaOrdenFabricacionDTO accion);

}
