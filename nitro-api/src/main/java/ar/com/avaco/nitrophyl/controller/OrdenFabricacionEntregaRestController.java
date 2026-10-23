package ar.com.avaco.nitrophyl.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.avaco.fwk.core.component.controller.AbstractDTORestController;
import ar.com.avaco.fwk.core.component.dto.JSONResponse;
import ar.com.avaco.fwk.core.component.dto.PageDTO;
import ar.com.avaco.fwk.core.exception.BusinessException;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionEntregaDTO;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionEntregaFilterDTO;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionEntregaGrillaDTO;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionEntregaListadoDTO;
import ar.com.avaco.nitrophyl.epservice.OrdenFabricacionEntregaEPService;
import ar.com.avaco.nitrophyl.filter.OrdenFabricacionEntregaFilter;

@RestController
public class OrdenFabricacionEntregaRestController
		extends AbstractDTORestController<OrdenFabricacionEntregaDTO, Long, OrdenFabricacionEntregaEPService> {

	@RequestMapping(value = "/ordenFabricacion/entregas/listado", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> listado(OrdenFabricacionEntregaFilterDTO filter) {
		PageDTO<OrdenFabricacionEntregaListadoDTO> listFilterCountCustom = this.service.listFilterCountCustom(
				new OrdenFabricacionEntregaFilter(filter), OrdenFabricacionEntregaListadoDTO.class);
		return OKPAGE(listFilterCountCustom);
	}

	@RequestMapping(value = "/ordenFabricacion/entregas/grilla", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> grilla(OrdenFabricacionEntregaFilterDTO filter) {
		PageDTO<OrdenFabricacionEntregaGrillaDTO> listFilterCountCustom = this.service.listFilterCountCustom(
				new OrdenFabricacionEntregaFilter(filter), OrdenFabricacionEntregaGrillaDTO.class);
		return OKPAGE(listFilterCountCustom);
	}

	@RequestMapping(value = "/ordenFabricacion/registrarEntrega/{idOrdenFabricacion}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> registrarEntrega(@PathVariable Long idOrdenFabricacion,
			@RequestBody OrdenFabricacionEntregaDTO entrega) {
		this.service.registrarEntrega(idOrdenFabricacion, entrega);
		JSONResponse response = new JSONResponse();
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@Override
	@RequestMapping(value = "/ordenFabricacion/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> delete(@PathVariable Long id) throws BusinessException {
		return super.delete(id);
	}

	@Override
	@Resource(name = "ordenFabricacionEntregaEPService")
	public void setService(OrdenFabricacionEntregaEPService service) {
		this.service = service;
	}

}