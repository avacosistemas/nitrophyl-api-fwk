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
import ar.com.avaco.fwk.core.exception.BusinessException;
import ar.com.avaco.nitrophyl.dto.DesmoldantepostcuraPUTDTO;
import ar.com.avaco.nitrophyl.dto.MoldeoPUTDTO;
import ar.com.avaco.nitrophyl.dto.ProcesoDTO;
import ar.com.avaco.nitrophyl.epservice.ProcesoEPService;

@RestController
public class ProcesoRestController extends AbstractDTORestController<ProcesoDTO, Long, ProcesoEPService> {

	@Override
	@RequestMapping(value = "/proceso", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> list() {
		return super.list();
	}

	@Override
	@RequestMapping(value = "/proceso", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> create(@RequestBody ProcesoDTO dto) throws BusinessException {
		return super.create(dto);
	}

	@Override
	@RequestMapping(value = "/proceso/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> update(@PathVariable Long id, @RequestBody ProcesoDTO dto)
			throws BusinessException {
		return super.update(id, dto);
	}

	@Override
	@RequestMapping(value = "/proceso/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> delete(@PathVariable Long id) throws BusinessException {
		return super.delete(id);
	}

	@RequestMapping(value = "/proceso/desmoldantepostcura/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> updateDesmoldantePostcura(@PathVariable Long id,
			@RequestBody DesmoldantepostcuraPUTDTO dto) throws BusinessException {
		this.service.updateDesmoldantePostcura(id, dto);
		JSONResponse response = new JSONResponse();
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/proceso/moldeo/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> updateMoldeo(@PathVariable Long id, @RequestBody MoldeoPUTDTO dto)
			throws BusinessException {
		this.service.updateMoldeo(id, dto);
		JSONResponse response = new JSONResponse();
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@Resource(name = "procesoEPService")
	public void setService(ProcesoEPService procesoEPService) {
		super.service = procesoEPService;
	}

}