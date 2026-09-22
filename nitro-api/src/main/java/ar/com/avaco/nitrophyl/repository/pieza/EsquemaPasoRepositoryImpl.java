package ar.com.avaco.nitrophyl.repository.pieza;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.nitrophyl.domain.entities.pieza.esquema.EsquemaPaso;

@Repository("esquemaPasoRepository")
public class EsquemaPasoRepositoryImpl extends NJBaseRepository<Long, EsquemaPaso> implements EsquemaPasoRepositoryCustom {

	public EsquemaPasoRepositoryImpl(EntityManager entityManager) {
		super(EsquemaPaso.class, entityManager);
	}

}