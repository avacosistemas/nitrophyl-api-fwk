package ar.com.avaco.nitrophyl.repository.pieza;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.nitrophyl.domain.entities.pieza.cliente.PiezaCliente;

@Repository("piezaClienteRepository")
public class PiezaClienteRepositoryImpl extends NJBaseRepository<Long, PiezaCliente> implements PiezaClienteRepositoryCustom {

	public PiezaClienteRepositoryImpl(EntityManager entityManager) {
		super(PiezaCliente.class, entityManager);
	}

}