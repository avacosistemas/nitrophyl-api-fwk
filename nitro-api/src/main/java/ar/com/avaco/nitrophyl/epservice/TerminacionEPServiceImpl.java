package ar.com.avaco.nitrophyl.epservice;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ar.com.avaco.nitrophyl.domain.entities.pieza.Terminacion;
import ar.com.avaco.nitrophyl.dto.TerminacionDTO;
import ar.com.avaco.nitrophyl.service.pieza.TerminacionService;
import ar.com.avaco.fwk.core.component.epservice.CRUDAuditableEPBaseService;

@Service("terminacionEPService")
public class TerminacionEPServiceImpl
		extends CRUDAuditableEPBaseService<Long, TerminacionDTO, Terminacion, TerminacionService>
		implements TerminacionEPService {

	public TerminacionEPServiceImpl() {
		super(Terminacion.class, TerminacionDTO.class);
	}

	@Override
	protected Terminacion convertToEntity(TerminacionDTO dto) {
		Terminacion entity = this.service.get(dto.getId());
		entity.setEmbalaje(dto.getEmbalaje());
		entity.setIdentificacion(dto.getIdentificacion());
		if (dto.getImagenTerminada() != null) {
			entity.setImagenTerminada(dto.getImagenTerminada());
		}
		entity.setRefilado(dto.getRefilado());
		return entity;
	}

	@Override
	@Resource(name = "terminacionService")
	protected void setService(TerminacionService service) {
		this.service = service;
	}

}