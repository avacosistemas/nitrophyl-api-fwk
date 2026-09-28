package ar.com.avaco.nitrophyl.epservice;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ar.com.avaco.nitrophyl.domain.entities.pieza.Pieza;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaPlano;
import ar.com.avaco.nitrophyl.dto.ArchivoDTO;
import ar.com.avaco.nitrophyl.dto.PiezaPlanoDTO;
import ar.com.avaco.nitrophyl.service.pieza.PiezaPlanoService;
import ar.com.avaco.fwk.core.component.epservice.CRUDAuditableEPBaseService;

@Service("piezaPlanoEPService")
public class PiezaPlanoEPServiceImpl extends
		CRUDAuditableEPBaseService<Long, PiezaPlanoDTO, PiezaPlano, PiezaPlanoService> implements PiezaPlanoEPService {

	public PiezaPlanoEPServiceImpl() {
		super(PiezaPlano.class, PiezaPlanoDTO.class);
	}

	@Override
	protected PiezaPlanoDTO convertToDto(PiezaPlano entity) {
		PiezaPlanoDTO dto = super.convertToDto(entity);
		dto.setArchivo(null);
		dto.setIdPieza(entity.getPieza().getId());
		return dto;
	}

	@Override
	protected PiezaPlano convertToEntity(PiezaPlanoDTO dto) {
		PiezaPlano entity = super.convertToEntity(dto);
		entity.setPieza(Pieza.ofId(dto.getIdPieza()));
		return entity;
	}

	@Override
	@Resource(name = "piezaPlanoService")
	protected void setService(PiezaPlanoService service) {
		this.service = service;
	}

	@Override
	public ArchivoDTO getPlanoArchivo(Long id) {
		PiezaPlano piezaPlano = this.service.get(id);
		ArchivoDTO archivoDTO = new ArchivoDTO();
		archivoDTO.setArchivo(piezaPlano.getArchivo());
		archivoDTO.setNombre("pieza-plano-" + piezaPlano.getId() + ".pdf");
		return archivoDTO;
	}

}