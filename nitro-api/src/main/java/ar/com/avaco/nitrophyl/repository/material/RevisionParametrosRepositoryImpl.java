package ar.com.avaco.nitrophyl.repository.material;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.nitrophyl.domain.entities.formula.RevisionParametros;

@Repository("revisionParametrosRepository")
public class RevisionParametrosRepositoryImpl extends NJBaseRepository<Long, RevisionParametros> implements RevisionParametrosRepositoryCustom {

	public RevisionParametrosRepositoryImpl(EntityManager entityManager) {
		super(RevisionParametros.class, entityManager);
	}

}
