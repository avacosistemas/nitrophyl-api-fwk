package ar.com.avaco.nitrophyl.epservice;

import ar.com.avaco.fwk.core.component.dto.PageDTO;
import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.CotizacionDTO;
import ar.com.avaco.nitrophyl.dto.CotizacionFilterDTO;

public interface CotizacionEPService extends CRUDEPService<Long, CotizacionDTO> {

	CotizacionDTO getCotizacionVigente(Long idPiezaCliente);

	PageDTO<CotizacionDTO> list(CotizacionFilterDTO filterDTO);
	
}