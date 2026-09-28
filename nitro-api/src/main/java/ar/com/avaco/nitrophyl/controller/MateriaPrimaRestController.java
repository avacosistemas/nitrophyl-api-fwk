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
import ar.com.avaco.nitrophyl.dto.MateriaPrimaDTO;
import ar.com.avaco.nitrophyl.dto.MateriaPrimaFilterDTO;
import ar.com.avaco.nitrophyl.epservice.MateriaPrimaEPService;
import ar.com.avaco.nitrophyl.filter.MateriaPrimaFilter;

@RestController
public class MateriaPrimaRestController extends AbstractDTORestController<MateriaPrimaDTO, Long, MateriaPrimaEPService> {

	@RequestMapping(value = "/materiaPrima", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> list(MateriaPrimaFilterDTO filter) {
		PageDTO<MateriaPrimaDTO> pageDTO = this.service.listFilterCount(new MateriaPrimaFilter(filter));
		JSONResponse response = new JSONResponse();
		response.setData(pageDTO);
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
		
	}

	@Override
	@RequestMapping(value = "/materiaPrima", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> create(@RequestBody MateriaPrimaDTO dto) throws BusinessException {
		return super.create(dto);
	}

	@Override
	@RequestMapping(value = "/materiaPrima/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> update(@PathVariable Long id, @RequestBody MateriaPrimaDTO dto)
			throws BusinessException {
		return super.update(id, dto);
	}

	@Override
	@RequestMapping(value = "/materiaPrima/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> delete(@PathVariable Long id) throws BusinessException {
		return super.delete(id);
	}

	@Resource(name = "materiaPrimaEPService")
	public void setService(MateriaPrimaEPService materiaPrimaEPService) {
		super.service = materiaPrimaEPService;
	}

}