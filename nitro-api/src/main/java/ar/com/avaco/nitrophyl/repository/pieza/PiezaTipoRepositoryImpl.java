package ar.com.avaco.nitrophyl.repository.pieza;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaTipo;

@Repository("piezaTipoRepository")
public class PiezaTipoRepositoryImpl extends NJBaseRepository<Long, PiezaTipo> implements PiezaTipoRepositoryCustom {

	public PiezaTipoRepositoryImpl(EntityManager entityManager) {
		super(PiezaTipo.class, entityManager);
	}

}
