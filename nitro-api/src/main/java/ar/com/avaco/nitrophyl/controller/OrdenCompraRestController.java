package ar.com.avaco.nitrophyl.controller;

import java.util.Map;

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
import ar.com.avaco.fwk.core.component.dto.PageDTO;
import ar.com.avaco.fwk.core.exception.BusinessException;
import ar.com.avaco.nitrophyl.dto.OrdenCompraDTO;
import ar.com.avaco.nitrophyl.dto.OrdenCompraListadoDTO;
import ar.com.avaco.nitrophyl.epservice.OrdenCompraEPService;
import ar.com.avaco.nitrophyl.filter.OrdenCompraFilter;
import ar.com.avaco.nitrophyl.filter.OrdenCompraFilterDTO;

@RestController
public class OrdenCompraRestController
		extends AbstractDTORestController<OrdenCompraDTO, Long, OrdenCompraEPService> {

	@RequestMapping(value = "/ordenCompra", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> list(OrdenCompraFilterDTO ordenCompraFilterDTO) {
		PageDTO<OrdenCompraListadoDTO> listFilterCount = this.service.listFilterCount(new OrdenCompraFilter(ordenCompraFilterDTO),
				OrdenCompraListadoDTO.class);
		return OKPAGE(listFilterCount);
	}

	@Override
	@RequestMapping(value = "/ordenCompra", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> create(@RequestBody OrdenCompraDTO dto) throws BusinessException {
		return super.create(dto);
	}

	@RequestMapping(value = "/ordenCompra/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> get(@PathVariable Long id) throws BusinessException {
		return super.get(id);
	}

	@RequestMapping(value = "/ordenCompra/generarOrdenFabrica/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> generarOrdenFabrica(@PathVariable Long id) throws BusinessException {
		this.service.generarOrdenFabrica(id);
		return OKDATA(true);
	}

	@RequestMapping(value = "/ordenCompra/cancelar/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> cancelar(@PathVariable Long id, @RequestBody Map<String, String> body)
			throws BusinessException {
		this.service.cancelar(id, body.get("observaciones"));
		return OKDATA(id);
	}

	@Override
	@RequestMapping(value = "/ordenCompra/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> delete(@PathVariable Long id) throws BusinessException {
		return super.delete(id);
	}

	@Override
	@RequestMapping(value = "/ordenCompra/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> update(@PathVariable Long id, @RequestBody OrdenCompraDTO dto)
			throws BusinessException {
		return super.update(id, dto);
	}

	@Resource(name = "ordenCompraEPService")
	public void setService(OrdenCompraEPService ordenCompraEPService) {
		super.service = ordenCompraEPService;
	}

}