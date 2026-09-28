package ar.com.avaco.nitrophyl.epservice;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.ArchivoDTO;
import ar.com.avaco.nitrophyl.dto.PiezaPlanoDTO;

public interface PiezaPlanoEPService extends CRUDEPService<Long, PiezaPlanoDTO> {

	ArchivoDTO getPlanoArchivo(Long id);

}