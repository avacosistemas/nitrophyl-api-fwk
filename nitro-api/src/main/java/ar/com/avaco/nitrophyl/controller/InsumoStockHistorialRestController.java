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
import ar.com.avaco.fwk.core.component.dto.PageDTO;
import ar.com.avaco.fwk.core.component.dto.filter.SortPageDTO;
import ar.com.avaco.fwk.core.domain.filter.FilterData;
import ar.com.avaco.fwk.core.domain.filter.FilterDataType;
import ar.com.avaco.fwk.core.exception.BusinessException;
import ar.com.avaco.nitrophyl.dto.InsumoStockHistorialDTO;
import ar.com.avaco.nitrophyl.epservice.InsumoStockHistorialEPService;
import ar.com.avaco.nitrophyl.filter.SimplePager;

@RestController
public class InsumoStockHistorialRestController extends AbstractDTORestController<InsumoStockHistorialDTO, Long, InsumoStockHistorialEPService> {

	@RequestMapping(value = "/insumoStockHistorial/{idInsumo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> list(SortPageDTO pager, @PathVariable Long idInsumo) {
		SimplePager sp = new SimplePager(pager);
		sp.getFilterDatas().add(new FilterData("insumo.id", idInsumo, FilterDataType.EQUALS));
		PageDTO<InsumoStockHistorialDTO> page = this.service.listFilterCount(sp);
		return OKDATA(page);
	}

	@Override
	@RequestMapping(value = "/insumoStockHistorial", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> create(@RequestBody InsumoStockHistorialDTO dto) throws BusinessException {
		return super.create(dto);
	}

	@Resource(name = "insumoStockHistorialEPService")
	public void setService(InsumoStockHistorialEPService insumoStockHistorialEPService) {
		super.service = insumoStockHistorialEPService;
	}

}