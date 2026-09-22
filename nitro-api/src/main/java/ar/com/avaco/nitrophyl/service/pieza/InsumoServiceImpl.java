package ar.com.avaco.nitrophyl.service.pieza;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.avaco.fwk.core.component.service.NJBaseService;
import ar.com.avaco.nitrophyl.domain.entities.pieza.insumo.Insumo;
import ar.com.avaco.nitrophyl.repository.pieza.InsumoRepository;

@Transactional
@Service("insumoService")
public class InsumoServiceImpl extends NJBaseService<Long, Insumo, InsumoRepository> implements InsumoService {

	@Resource(name = "insumoRepository")
	void setRepository(InsumoRepository insumoRepository) {
		this.repository = insumoRepository;
	}

}
