package ar.com.avaco.nitrophyl.repository.pieza;

import ar.com.avaco.fwk.core.component.dto.PageDTO;
import ar.com.avaco.nitrophyl.dto.CotizacionDTO;
import ar.com.avaco.nitrophyl.dto.CotizacionFilterDTO;

public interface CotizacionRepositoryCustom {

	PageDTO<CotizacionDTO> list(CotizacionFilterDTO filter);

}