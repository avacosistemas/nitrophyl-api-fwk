package ar.com.avaco.nitrophyl.epservice;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaTipo;
import ar.com.avaco.nitrophyl.dto.PiezaTipoDTO;
import ar.com.avaco.nitrophyl.service.pieza.PiezaTipoService;
import ar.com.avaco.fwk.core.component.epservice.CRUDAuditableEPBaseService;

@Service("piezaTipoEPService")
public class PiezaTipoEPServiceImpl extends CRUDAuditableEPBaseService<Long, PiezaTipoDTO, PiezaTipo, PiezaTipoService>
		implements PiezaTipoEPService {

	public PiezaTipoEPServiceImpl() {
		super(PiezaTipo.class, PiezaTipoDTO.class);
	}

	@Override
	@Resource(name = "piezaTipoService")
	protected void setService(PiezaTipoService service) {
		this.service = service;
	}


}