package ar.com.avaco.nitrophyl.epservice;

import java.util.List;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.MaquinaPruebaDTO;

public interface MaquinaPruebaEPService extends CRUDEPService<Long, MaquinaPruebaDTO> {

	List<MaquinaPruebaDTO> listPruebasByMaquina(Long idMaquina);

}
