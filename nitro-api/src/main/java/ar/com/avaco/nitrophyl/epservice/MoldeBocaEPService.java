
package ar.com.avaco.nitrophyl.epservice;

import java.util.List;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.MoldeBocaDTO;

public interface MoldeBocaEPService extends CRUDEPService<Long, MoldeBocaDTO> {

	List<MoldeBocaDTO> listByMoldeId(Long idMolde);

}
