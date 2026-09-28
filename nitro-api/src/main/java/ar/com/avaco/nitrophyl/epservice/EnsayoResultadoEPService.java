package ar.com.avaco.nitrophyl.epservice;

import java.util.List;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.EnsayoResultadoDTO;

public interface EnsayoResultadoEPService extends CRUDEPService<Long, EnsayoResultadoDTO> {

	List<EnsayoResultadoDTO> listByEnsayo(Long idEnsayo);

}
