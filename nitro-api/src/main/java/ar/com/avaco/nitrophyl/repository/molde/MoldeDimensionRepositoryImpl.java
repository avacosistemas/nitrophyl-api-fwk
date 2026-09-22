package ar.com.avaco.nitrophyl.repository.molde;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.nitrophyl.domain.entities.molde.MoldeDimension;

@Repository("moldeDimensionRepository")
public class MoldeDimensionRepositoryImpl extends NJBaseRepository<Long, MoldeDimension> implements MoldeDimensionRepositoryCustom {

	public MoldeDimensionRepositoryImpl(EntityManager entityManager) {
		super(MoldeDimension.class, entityManager);
	}

}
