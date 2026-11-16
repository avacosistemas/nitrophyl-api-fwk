package ar.com.avaco.nitrophyl.service.pieza;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import ar.com.avaco.fwk.core.component.service.NJBaseService;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStockLoteMovimiento;
import ar.com.avaco.nitrophyl.repository.pieza.PiezaMovimientoStockRepository;

//@Transactional
@Service("piezaMovimientoStockService")
public class PiezaMovimientoStockServiceImpl extends NJBaseService<Long, PiezaStockLoteMovimiento, PiezaMovimientoStockRepository>
		implements PiezaMovimientoStockService {

	@Resource(name = "piezaMovimientoStockRepository")
	void setPiezaMovimientoStockRepository(PiezaMovimientoStockRepository piezaMovimientoStockRepository) {
		this.repository = piezaMovimientoStockRepository;
	}

}
