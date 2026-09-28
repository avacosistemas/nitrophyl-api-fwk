package ar.com.avaco.nitrophyl.epservice;

import java.util.List;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.EnsayoDTO;

public interface EnsayoEPService extends CRUDEPService<Long, EnsayoDTO> {

	List<EnsayoDTO> listByLote(Long idLote);

}
