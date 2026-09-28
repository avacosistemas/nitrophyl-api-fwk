package ar.com.avaco.nitrophyl.epservice;

import java.util.List;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.ComboDTO;
import ar.com.avaco.nitrophyl.dto.TipoInsumoDTO;

public interface TipoInsumoEPService extends CRUDEPService<Long, TipoInsumoDTO> {

	List<ComboDTO> listHijos();

}
