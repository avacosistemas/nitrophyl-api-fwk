package ar.com.avaco.nitrophyl.repository.pieza;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.nitrophyl.domain.entities.pieza.insumo.InsumoTratado;

@Repository("insumoTratadoRepository")
public class InsumoTratadoRepositoryImpl extends NJBaseRepository<Long, InsumoTratado> implements InsumoTratadoRepositoryCustom {

	public InsumoTratadoRepositoryImpl(EntityManager entityManager) {
		super(InsumoTratado.class, entityManager);
	}

}