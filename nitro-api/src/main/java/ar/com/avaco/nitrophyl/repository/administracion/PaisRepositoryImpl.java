package ar.com.avaco.nitrophyl.repository.administracion;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.nitrophyl.domain.entities.administracion.Pais;

@Repository("paisRepository")
public class PaisRepositoryImpl extends NJBaseRepository<Long, Pais> implements PaisRepositoryCustom {

	public PaisRepositoryImpl(EntityManager entityManager) {
		super(Pais.class, entityManager);
	}

}