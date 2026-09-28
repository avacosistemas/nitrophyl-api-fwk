package ar.com.avaco.nitrophyl.controller;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.avaco.fwk.core.component.controller.AbstractDTORestController;
import ar.com.avaco.fwk.core.component.dto.JSONResponse;
import ar.com.avaco.fwk.core.component.dto.PageDTO;
import ar.com.avaco.nitrophyl.dto.RegistroEnvioInformeCalidadDTO;
import ar.com.avaco.nitrophyl.dto.RegistroEnvioInformeCalidadFilterDTO;
import ar.com.avaco.nitrophyl.epservice.RegistroEnvioInformeCalidadEPService;
import ar.com.avaco.nitrophyl.filter.RegistroEnvioInformeCalidadFilter;

@RestController
public class RegistroEnvioInformeCalidadController extends
		AbstractDTORestController<RegistroEnvioInformeCalidadDTO, Long, RegistroEnvioInformeCalidadEPService> {

	@RequestMapping(value = "/registroEnvioInformeCalidad", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> list(RegistroEnvioInformeCalidadFilterDTO filter) {
		PageDTO<RegistroEnvioInformeCalidadDTO> pageDTO = this.service
				.listFilterCount(new RegistroEnvioInformeCalidadFilter(filter));
		return OKPAGE(pageDTO);
	}

	@Override
	@Resource(name = "registroEnvioInformeCalidadEPService")
	public void setService(RegistroEnvioInformeCalidadEPService service) {
		this.service = service;
	}

}