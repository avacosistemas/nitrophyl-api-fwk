package ar.com.avaco.nitrophyl.repository.lote;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.nitrophyl.domain.entities.reporte.RegistroEnvioInformeCalidad;

@Repository("registroEnvioInformCalidadRepository")
public class RegistroEnvioInformeCalidadRepositoryImpl extends NJBaseRepository<Long, RegistroEnvioInformeCalidad>
		implements RegistroEnvioInformeCalidadRepositoryCustom {

	public RegistroEnvioInformeCalidadRepositoryImpl(EntityManager entityManager) {
		super(RegistroEnvioInformeCalidad.class, entityManager);
	}

}
