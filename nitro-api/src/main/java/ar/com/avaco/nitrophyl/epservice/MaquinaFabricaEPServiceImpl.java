package ar.com.avaco.nitrophyl.epservice;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.avaco.fwk.core.component.epservice.CRUDAuditableEPBaseService;
import ar.com.avaco.fwk.core.component.epservice.CRUDEPBaseService;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.MaquinaFabrica;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.SectorFabrica;
import ar.com.avaco.nitrophyl.dto.MaquinaFabricaDTO;
import ar.com.avaco.nitrophyl.service.produccion.MaquinaFabricaService;

@Transactional
@Service("maquinaFabricaEPService")
public class MaquinaFabricaEPServiceImpl
		extends CRUDAuditableEPBaseService<Long, MaquinaFabricaDTO, MaquinaFabrica, MaquinaFabricaService>
		implements MaquinaFabricaEPService {

	public MaquinaFabricaEPServiceImpl() {
		super(MaquinaFabrica.class, MaquinaFabricaDTO.class);
	}

	@Override
	@Resource(name = "maquinaFabricaService")
	protected void setService(MaquinaFabricaService service) {
		this.service = service;
	}

	@Override
	protected MaquinaFabrica convertToEntity(MaquinaFabricaDTO dto) {
		MaquinaFabrica maq = new MaquinaFabrica();
		maq.setId(dto.getId());
		maq.setNombre(dto.getNombre());
		maq.setSector(SectorFabrica.ofId(dto.getIdSector()));
		maq.setTipo(dto.getTipo());
		return maq;
	}

	@Override
	protected MaquinaFabricaDTO convertToDto(MaquinaFabrica entity) {
		MaquinaFabricaDTO dto = new MaquinaFabricaDTO();
		dto.setId(entity.getId());
		dto.setNombre(entity.getNombre());
		dto.setIdSector(entity.getSector().getId());
		dto.setSector(entity.getSector().getNombre());
		dto.setTipo(entity.getTipo());
		return dto;
	}

}