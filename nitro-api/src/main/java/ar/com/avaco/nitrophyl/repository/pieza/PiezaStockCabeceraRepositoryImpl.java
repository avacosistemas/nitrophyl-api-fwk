package ar.com.avaco.nitrophyl.repository.pieza;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStock;

@Repository("piezaStockCabeceraRepository")
public class PiezaStockCabeceraRepositoryImpl extends NJBaseRepository<Long, PiezaStock>
		implements PiezaStockCabeceraRepositoryCustom {

	public PiezaStockCabeceraRepositoryImpl(EntityManager entityManager) {
		super(PiezaStock.class, entityManager);
	}
}
