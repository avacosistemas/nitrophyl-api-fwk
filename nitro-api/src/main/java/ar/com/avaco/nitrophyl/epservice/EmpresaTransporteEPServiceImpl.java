package ar.com.avaco.nitrophyl.epservice;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import ar.com.avaco.fwk.core.component.epservice.CRUDAuditableEPBaseService;
import ar.com.avaco.nitrophyl.domain.entities.administracion.EmpresaTransporte;
import ar.com.avaco.nitrophyl.dto.EmpresaTransporteDTO;
import ar.com.avaco.nitrophyl.service.administracion.EmpresaTransporteService;

@Transactional
@Service("empresaTransporteEPService")
public class EmpresaTransporteEPServiceImpl
		extends CRUDAuditableEPBaseService<Long, EmpresaTransporteDTO, EmpresaTransporte, EmpresaTransporteService>
		implements EmpresaTransporteEPService {

	public EmpresaTransporteEPServiceImpl() {
		super(EmpresaTransporte.class, EmpresaTransporteDTO.class);
	}

	@Override
	protected EmpresaTransporteDTO convertToDto(EmpresaTransporte entity) {
		EmpresaTransporteDTO dto = super.convertToDto(entity);
		dto.setMediosEnvio(Lists.newArrayList(entity.getMediosEnvio().split(",")));
		return dto;
	}

	@Override
	protected EmpresaTransporte convertToEntity(EmpresaTransporteDTO dto) {
		EmpresaTransporte entity = super.convertToEntity(dto);
		entity.setMediosEnvio(String.join(",", dto.getMediosEnvio()));
		return entity;
	}

	@Override
	@Resource(name = "empresaTransporteService")
	protected void setService(EmpresaTransporteService service) {
		this.service = service;
	}

}