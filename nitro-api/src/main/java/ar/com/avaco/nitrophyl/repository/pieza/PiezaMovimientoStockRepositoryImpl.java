package ar.com.avaco.nitrophyl.repository.pieza;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStockLoteMovimiento;

@Repository("piezaMovimientoStockRepository")
public class PiezaMovimientoStockRepositoryImpl extends NJBaseRepository<Long, PiezaStockLoteMovimiento> implements PiezaMovimientoStockRepositoryCustom {

	public PiezaMovimientoStockRepositoryImpl(EntityManager entityManager) {
		super(PiezaStockLoteMovimiento.class, entityManager);
	}

		
}
