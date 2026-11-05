package ar.com.avaco.nitrophyl.service.pieza;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import ar.com.avaco.fwk.core.component.service.NJBaseService;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaPlano;
import ar.com.avaco.nitrophyl.repository.pieza.PiezaPlanoRepository;

//@Transactional
@Service("piezaPlanoService")
public class PiezaPlanoServiceImpl extends NJBaseService<Long, PiezaPlano, PiezaPlanoRepository> implements PiezaPlanoService {

	@Override
	public void deleteAssociation(Long id) {
		this.repository.deleteAssociationById(id);
	}

	@Resource(name = "piezaPlanoRepository")
	void setRepository(PiezaPlanoRepository piezaPlanoRepository) {
		this.repository = piezaPlanoRepository;
	}

}
