package ar.com.avaco.nitrophyl.epservice;

import java.util.List;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.EsquemaDTO;

public interface EsquemaEPService extends CRUDEPService<Long, EsquemaDTO> {

	void reordenar(Long idEsquema, Integer posicion);

	List<EsquemaDTO> listEsquemas(Long idProceso);

}