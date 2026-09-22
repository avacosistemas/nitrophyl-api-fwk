package ar.com.avaco.nitrophyl.service.pieza;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.avaco.fwk.core.component.service.NJBaseService;
import ar.com.avaco.nitrophyl.domain.entities.pieza.esquema.EsquemaPaso;
import ar.com.avaco.nitrophyl.repository.pieza.EsquemaPasoRepository;

@Transactional
@Service("esquemaPasoService")
public class EsquemaPasoServiceImpl extends NJBaseService<Long, EsquemaPaso, EsquemaPasoRepository> implements EsquemaPasoService {

	@Resource(name = "esquemaPasoRepository")
	void setRepository(EsquemaPasoRepository esquemaPasoRepository) {
		this.repository = esquemaPasoRepository;
	}

}
