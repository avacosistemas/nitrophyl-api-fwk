package ar.com.avaco.nitrophyl.epservice;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.avaco.nitrophyl.domain.entities.molde.Troquel;
import ar.com.avaco.nitrophyl.dto.TroquelDTO;
import ar.com.avaco.nitrophyl.service.produccion.TroquelService;
import ar.com.avaco.fwk.core.component.epservice.CRUDEPBaseService;

@Transactional
@Service("troquelEPService")
public class TroquelEPServiceImpl extends CRUDEPBaseService<Long, TroquelDTO, Troquel, TroquelService>
		implements TroquelEPService {

	public TroquelEPServiceImpl() {
		super(Troquel.class, TroquelDTO.class);
	}

	@Override
	@Resource(name = "troquelService")
	protected void setService(TroquelService service) {
		this.service = service;
	}

}