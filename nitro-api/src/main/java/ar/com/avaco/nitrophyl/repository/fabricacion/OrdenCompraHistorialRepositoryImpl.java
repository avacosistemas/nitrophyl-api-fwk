package ar.com.avaco.nitrophyl.repository.fabricacion;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenCompraHistorial;

@Repository("ordenCompraHistorialRepository")
public class OrdenCompraHistorialRepositoryImpl extends NJBaseRepository<Long, OrdenCompraHistorial>
		implements OrdenCompraHistorialRepositoryCustom {

	public OrdenCompraHistorialRepositoryImpl(EntityManager entityManager) {
		super(OrdenCompraHistorial.class, entityManager);
	}
}
