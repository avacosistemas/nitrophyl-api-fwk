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
import ar.com.avaco.nitrophyl.dto.InsumoDTO;
import ar.com.avaco.nitrophyl.dto.InsumoFilterDTO;
import ar.com.avaco.nitrophyl.epservice.InsumoEPService;
import ar.com.avaco.nitrophyl.filter.InsumoFilter;

@RestController
public class InsumoRestController extends AbstractDTORestController<InsumoDTO, Long, InsumoEPService> {

	@RequestMapping(value = "/insumo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> list(InsumoFilterDTO filterDTO) {
		PageDTO<InsumoDTO> page = this.service.listFilterCount(new InsumoFilter(filterDTO));
		return OKPAGE(page);
	}

	@Override
	@RequestMapping(value = "/insumo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> create(@RequestBody InsumoDTO dto) throws BusinessException {
		return super.create(dto);
	}

	@Override
	@RequestMapping(value = "/insumo/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> update(@PathVariable Long id, @RequestBody InsumoDTO dto)
			throws BusinessException {
		return super.update(id, dto);
	}

	@Override
	@RequestMapping(value = "/insumo/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> delete(@PathVariable Long id) throws BusinessException {
		return super.delete(id);
	}

	@Resource(name = "insumoEPService")
	public void setService(InsumoEPService insumoEPService) {
		super.service = insumoEPService;
	}

}