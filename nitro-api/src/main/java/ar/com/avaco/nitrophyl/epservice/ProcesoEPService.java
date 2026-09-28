package ar.com.avaco.nitrophyl.epservice;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.DesmoldantepostcuraPUTDTO;
import ar.com.avaco.nitrophyl.dto.MoldeoPUTDTO;
import ar.com.avaco.nitrophyl.dto.ProcesoDTO;

public interface ProcesoEPService extends CRUDEPService<Long, ProcesoDTO> {

	void updateDesmoldantePostcura(Long id, DesmoldantepostcuraPUTDTO dto);

	void updateMoldeo(Long id, MoldeoPUTDTO dto);

}