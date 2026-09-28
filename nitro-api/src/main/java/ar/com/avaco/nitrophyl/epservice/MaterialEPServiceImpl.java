package ar.com.avaco.nitrophyl.epservice;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ar.com.avaco.nitrophyl.domain.entities.formula.Material;
import ar.com.avaco.nitrophyl.dto.MaterialDTO;
import ar.com.avaco.nitrophyl.service.formula.MaterialService;
import ar.com.avaco.fwk.core.component.epservice.CRUDEPBaseService;

@Service("materialEPService")
public class MaterialEPServiceImpl extends CRUDEPBaseService<Long, MaterialDTO, Material, MaterialService>
		implements MaterialEPService {

	public MaterialEPServiceImpl() {
		super(Material.class, MaterialDTO.class);
	}

	@Override
	@Resource(name = "materialService")
	protected void setService(MaterialService service) {
		this.service = service;
	}

	@Override
	protected Material convertToEntity(MaterialDTO dto) {
		Material material = new Material();
		material.setId(dto.getId());
		material.setNombre(dto.getNombre());
		material.setCodigo(dto.getCodigo());
		return material;
	}

	@Override
	protected MaterialDTO convertToDto(Material entity) {
		MaterialDTO dto = new MaterialDTO();
		dto.setId(entity.getId());
		dto.setNombre(entity.getNombre());
		dto.setCodigo(entity.getCodigo());
		return dto;
	}

}
