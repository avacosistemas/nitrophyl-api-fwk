package ar.com.avaco.nitrophyl.controller;

import javax.annotation.Resource;

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
import ar.com.avaco.nitrophyl.dto.OrdenCompraDetalleDTO;
import ar.com.avaco.nitrophyl.epservice.OrdenCompraDetalleEPService;

@RestController
public class OrdenCompraDetalleRestController extends AbstractDTORestController<OrdenCompraDetalleDTO, Long, OrdenCompraDetalleEPService> {

	@Override
	@RequestMapping(value = "/ordenCompraDetalle", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> list() {
		return super.list();
	}

	@Override
	@RequestMapping(value = "/ordenCompraDetalle", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> create(@RequestBody OrdenCompraDetalleDTO dto) throws BusinessException {
		return super.create(dto);
	}

	@Override
	@RequestMapping(value = "/ordenCompraDetalle/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> update(@PathVariable Long id, @RequestBody OrdenCompraDetalleDTO dto)
			throws BusinessException {
		return super.update(id, dto);
	}

	@Override
	@RequestMapping(value = "/ordenCompraDetalle/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> delete(@PathVariable Long id) throws BusinessException {
		return super.delete(id);
	}

	@Resource(name = "ordenCompraDetalleEPService")
	public void setService(OrdenCompraDetalleEPService ordenCompraDetalleEPService) {
		super.service = ordenCompraDetalleEPService;
	}

}