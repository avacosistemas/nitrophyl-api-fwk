package ar.com.avaco.nitrophyl.repository.pieza;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.nitrophyl.domain.entities.pieza.insumo.Adhesivo;

@Repository("adhesivoRepository")
public class AdhesivoRepositoryImpl extends NJBaseRepository<Long, Adhesivo> implements AdhesivoRepositoryCustom {

	public AdhesivoRepositoryImpl(EntityManager entityManager) {
		super(Adhesivo.class, entityManager);
	}

}
