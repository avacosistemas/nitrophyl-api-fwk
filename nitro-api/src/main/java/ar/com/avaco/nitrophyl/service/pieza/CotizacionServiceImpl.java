package ar.com.avaco.nitrophyl.service.pieza;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import ar.com.avaco.fwk.core.component.service.NJBaseService;
import ar.com.avaco.nitrophyl.domain.entities.pieza.Pieza;
import ar.com.avaco.nitrophyl.domain.entities.pieza.cliente.Cotizacion;
import ar.com.avaco.nitrophyl.dto.CotizacionDTO;
import ar.com.avaco.nitrophyl.dto.CotizacionFilterDTO;
import ar.com.avaco.nitrophyl.repository.pieza.CotizacionRepository;
import ar.com.avaco.nitrophyl.repository.pieza.PiezaRepository;
import ar.com.avaco.fwk.core.component.dto.PageDTO;

//@Transactional
@Service("cotizacionService")
public class CotizacionServiceImpl extends NJBaseService<Long, Cotizacion, CotizacionRepository>
		implements CotizacionService {

	private PiezaRepository piezaRepository;

	@Override
	public Cotizacion getCotizacionVigente(Long idPiezaCliente) {
		return this.repository.findFirstByPiezaClienteIdOrderByFechaDesc(idPiezaCliente).orElse(null);
	}

	@Override
	public boolean existsByPiezaCliente(Long idPiezaCliente) {
		return this.repository.existsByPiezaCliente_Id(idPiezaCliente);
	}

	@Override
	public PageDTO<CotizacionDTO> listCotizaciones(CotizacionFilterDTO filter) {
		return this.repository.list(filter);
	}
	
	@Resource(name = "cotizacionRepository")
	void setRepository(CotizacionRepository cotizacionRepository) {
		this.repository = cotizacionRepository;
	}
	
	@Resource(name = "piezaRepository")
	public void setPiezaRepository(PiezaRepository piezaRepository) {
		this.piezaRepository = piezaRepository;
	}
	
	
}
