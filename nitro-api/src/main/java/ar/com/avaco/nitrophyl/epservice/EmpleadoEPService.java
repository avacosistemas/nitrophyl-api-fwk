package ar.com.avaco.nitrophyl.epservice;

import java.util.List;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.EmpleadoDTO;

public interface EmpleadoEPService extends CRUDEPService<Long, EmpleadoDTO> {

	List<EmpleadoDTO> listOperarios();

}
