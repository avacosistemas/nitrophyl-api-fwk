package ar.com.avaco.nitrophyl.service.pieza;

import ar.com.avaco.fwk.core.component.dto.PageDTO;
import ar.com.avaco.fwk.core.component.service.NJService;
import ar.com.avaco.nitrophyl.domain.entities.pieza.cliente.Cotizacion;
import ar.com.avaco.nitrophyl.dto.CotizacionDTO;
import ar.com.avaco.nitrophyl.dto.CotizacionFilterDTO;

public interface CotizacionService extends NJService<Long, Cotizacion> {

	Cotizacion getCotizacionVigente(Long idPiezaCliente);

	PageDTO<CotizacionDTO> listCotizaciones(CotizacionFilterDTO filter);
	
}