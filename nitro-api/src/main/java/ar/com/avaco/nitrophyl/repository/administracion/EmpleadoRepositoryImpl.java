package ar.com.avaco.nitrophyl.repository.administracion;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.fwk.security.domain.Usuario;

@Repository("empleadoRepository")
public class EmpleadoRepositoryImpl extends NJBaseRepository<Long, Usuario> implements EmpleadoRepositoryCustom {

	public EmpleadoRepositoryImpl(EntityManager entityManager) {
		super(Usuario.class, entityManager);
	}

}