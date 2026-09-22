package ar.com.avaco.nitrophyl.service.formula;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.avaco.fwk.core.component.service.NJBaseService;
import ar.com.avaco.nitrophyl.domain.entities.formula.Material;
import ar.com.avaco.nitrophyl.repository.material.MaterialRepository;

@Transactional
@Service("materialService")
public class MaterialServiceImpl extends NJBaseService<Long, Material, MaterialRepository> implements MaterialService {

	@Resource(name = "materialRepository")
	public void setFormulaRepository(MaterialRepository materialRepository) {
		this.repository = materialRepository;
	}

}
