package ar.com.avaco.nitrophyl.ws.service;

import java.util.List;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.ws.dto.EnsayoDTO;

public interface EnsayoEPService extends CRUDEPService<Long, EnsayoDTO> {

	List<EnsayoDTO> listByLote(Long idLote);

}
