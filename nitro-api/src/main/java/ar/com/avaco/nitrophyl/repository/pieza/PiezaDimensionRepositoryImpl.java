package ar.com.avaco.nitrophyl.repository.pieza;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaDimension;

@Repository("piezaDimensionRepository")
public class PiezaDimensionRepositoryImpl extends NJBaseRepository<Long, PiezaDimension> implements PiezaDimensionRepositoryCustom {

	public PiezaDimensionRepositoryImpl(EntityManager entityManager) {
		super(PiezaDimension.class, entityManager);
	}

}