package ar.com.avaco.nitrophyl.repository.pieza;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.nitrophyl.domain.entities.pieza.insumo.MateriaPrimaStockHistorial;

@Repository("materiaPrimaStockHistorialRepository")
public class MateriaPrimaStockHistorialRepositoryImpl extends NJBaseRepository<Long, MateriaPrimaStockHistorial> implements MateriaPrimaStockHistorialRepositoryCustom {

	public MateriaPrimaStockHistorialRepositoryImpl(EntityManager entityManager) {
		super(MateriaPrimaStockHistorial.class, entityManager);
	}

}