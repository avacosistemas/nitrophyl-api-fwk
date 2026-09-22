package ar.com.avaco.nitrophyl.repository.pieza;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.nitrophyl.domain.entities.pieza.insumo.Tratamiento;

@Repository("tratamientoRepository")
public class TratamientoRepositoryImpl extends NJBaseRepository<Long, Tratamiento> implements TratamientoRepositoryCustom {

	public TratamientoRepositoryImpl(EntityManager entityManager) {
		super(Tratamiento.class, entityManager);
	}

}