
package ar.com.avaco.nitrophyl.epservice;

import java.util.List;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.PiezaControlDTO;

public interface PiezaControlEPService extends CRUDEPService<Long, PiezaControlDTO> {

	List<PiezaControlDTO> listControlesConfigurados(Long idPieza);

}
