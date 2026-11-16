package ar.com.avaco.nitrophyl.repository.pieza;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStockReserva;

@Repository("piezaReservaStockRepository")
public class PiezaReservaStockRepositoryImpl extends NJBaseRepository<Long, PiezaStockReserva>
		implements PiezaReservaStockRepositoryCustom {

	public PiezaReservaStockRepositoryImpl(EntityManager entityManager) {
		super(PiezaStockReserva.class, entityManager);
	}
}
