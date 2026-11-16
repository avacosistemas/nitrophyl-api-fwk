package ar.com.avaco.nitrophyl.repository.fabricacion;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenFabricacionAsignacion;

@Repository("ordenFabricacionAsignacionRepository")
public class OrdenFabricacionAsignacionRepositoryImpl
			extends NJBaseRepository<Long, OrdenFabricacionAsignacion>
		implements OrdenFabricacionAsignacionRepositoryCustom {

	public OrdenFabricacionAsignacionRepositoryImpl(EntityManager entityManager) {
		super(OrdenFabricacionAsignacion.class, entityManager);
	}
}
