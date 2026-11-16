package ar.com.avaco.nitrophyl.repository.pieza;

import ar.com.avaco.fwk.core.component.repository.NJRepository;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStockLoteMovimiento;

public interface PiezaMovimientoStockRepository extends NJRepository<Long, PiezaStockLoteMovimiento>, PiezaMovimientoStockRepositoryCustom {

}
