package ar.com.avaco.nitrophyl.epservice;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.LoteObservacionDTO;

public interface LoteObservacionEPService extends CRUDEPService<Long, LoteObservacionDTO> {

	void updateCheck(Long idLoteObservacion);

}
