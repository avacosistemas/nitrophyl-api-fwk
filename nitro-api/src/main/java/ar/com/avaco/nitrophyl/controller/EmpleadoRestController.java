package ar.com.avaco.nitrophyl.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.avaco.fwk.core.component.controller.AbstractDTORestController;
import ar.com.avaco.fwk.core.component.dto.JSONResponse;
import ar.com.avaco.nitrophyl.dto.EmpleadoDTO;
import ar.com.avaco.nitrophyl.epservice.EmpleadoEPService;

@RestController
public class EmpleadoRestController extends AbstractDTORestController<EmpleadoDTO, Long, EmpleadoEPService> {

	@RequestMapping(value = "/empleado/operarios", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> list() {
		List<EmpleadoDTO> usuarios = this.service.listOperarios();
		return OKDATA(usuarios);
	}
	
	@Resource(name = "empleadoEPService")
	public void setService(EmpleadoEPService empleadoEPService) {
		super.service = empleadoEPService;
	}

}