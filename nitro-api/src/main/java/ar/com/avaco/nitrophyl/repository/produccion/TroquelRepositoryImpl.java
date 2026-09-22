package ar.com.avaco.nitrophyl.repository.produccion;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.nitrophyl.domain.entities.molde.Troquel;

@Repository("troquelRepository")
public class TroquelRepositoryImpl extends NJBaseRepository<Long, Troquel> implements TroquelRepositoryCustom {

	public TroquelRepositoryImpl(EntityManager entityManager) {
		super(Troquel.class, entityManager);
	}

}