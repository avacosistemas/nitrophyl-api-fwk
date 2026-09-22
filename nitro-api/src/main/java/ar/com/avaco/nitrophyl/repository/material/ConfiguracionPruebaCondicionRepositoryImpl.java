package ar.com.avaco.nitrophyl.repository.material;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import ar.com.avaco.fwk.core.component.repository.NJBaseRepository;
import ar.com.avaco.nitrophyl.domain.entities.formula.ConfiguracionPruebaCondicion;

@Repository("configuracionPruebaCondicionRepository")
public class ConfiguracionPruebaCondicionRepositoryImpl extends NJBaseRepository<Long, ConfiguracionPruebaCondicion>
		implements ConfiguracionPruebaCondicionRepositoryCustom {

	public ConfiguracionPruebaCondicionRepositoryImpl(EntityManager entityManager) {
		super(ConfiguracionPruebaCondicion.class, entityManager);
	}

}
