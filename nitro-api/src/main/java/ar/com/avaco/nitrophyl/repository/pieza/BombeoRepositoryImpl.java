package ar.com.avaco.nitrophyl.repository.pieza;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.nitrophyl.domain.entities.pieza.moldeo.Bombeo;

@Repository("bombeoRepository")
public class BombeoRepositoryImpl extends NJBaseRepository<Long, Bombeo> implements BombeoRepositoryCustom {

	public BombeoRepositoryImpl(EntityManager entityManager) {
		super(Bombeo.class, entityManager);
	}

}