package ar.com.avaco.nitrophyl.repository.administracion;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.nitrophyl.domain.entities.administracion.EmpresaTransporte;

@Repository("empresaTransporteRepository")
public class EmpresaTransporteRepositoryImpl extends NJBaseRepository<Long, EmpresaTransporte> implements EmpresaTransporteRepositoryCustom {

	public EmpresaTransporteRepositoryImpl(EntityManager entityManager) {
		super(EmpresaTransporte.class, entityManager);
	}

}