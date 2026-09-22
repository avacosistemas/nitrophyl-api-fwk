package ar.com.avaco.nitrophyl.repository.pieza;

import ar.com.avaco.fwk.core.component.repository.NJRepository;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStock;

public interface PiezaStockRepository extends NJRepository<Long, PiezaStock>, PiezaStockRepositoryCustom {

}
