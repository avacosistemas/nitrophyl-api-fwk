package ar.com.avaco.nitrophyl.epservice;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ar.com.avaco.nitrophyl.domain.entities.pieza.insumo.Tratamiento;
import ar.com.avaco.nitrophyl.dto.TratamientoDTO;
import ar.com.avaco.nitrophyl.service.pieza.TratamientoService;
import ar.com.avaco.fwk.core.component.epservice.CRUDAuditableEPBaseService;

@Service("tratamientoEPService")
public class TratamientoEPServiceImpl
		extends CRUDAuditableEPBaseService<Long, TratamientoDTO, Tratamiento, TratamientoService>
		implements TratamientoEPService {

	public TratamientoEPServiceImpl() {
		super(Tratamiento.class, TratamientoDTO.class);
	}

	@Override
	@Resource(name = "tratamientoService")
	protected void setService(TratamientoService service) {
		this.service = service;
	}

}