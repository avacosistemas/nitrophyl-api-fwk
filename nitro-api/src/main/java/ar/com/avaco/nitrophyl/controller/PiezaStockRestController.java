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
import ar.com.avaco.nitrophyl.dto.PiezaStockDTO;
import ar.com.avaco.nitrophyl.dto.PiezaStockFilterDTO;
import ar.com.avaco.nitrophyl.dto.PiezaStockResumenDTO;
import org.springframework.web.bind.annotation.PathVariable;
import ar.com.avaco.nitrophyl.epservice.PiezaStockEPService;
import ar.com.avaco.nitrophyl.filter.PiezaStockFilter;

@RestController
public class PiezaStockRestController
		extends AbstractDTORestController<PiezaStockDTO, Long, PiezaStockEPService> {

	@RequestMapping(value = "/pieza/stock", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> piezaStock(PiezaStockFilterDTO filterDTO) {
		PageDTO<PiezaStockDTO> listFilterCount = this.service.listFilterCount(new PiezaStockFilter(filterDTO), PiezaStockDTO.class);
		return OKDATA(listFilterCount);
	}

	@RequestMapping(value = "/pieza/stock/pieza/{idPieza}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> stockPieza(@PathVariable Long idPieza) {
		PiezaStockResumenDTO resumen = this.service.obtenerResumen(idPieza);
		return OKDATA(resumen);
	}

	@RequestMapping(value = "/pieza/stock/lote/{codigoLote}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> stockLote(@PathVariable String codigoLote) {
		return OKDATA(this.service.listarPorLote(codigoLote));
	}

	@Resource(name = "piezaStockEPService")
	public void setService(PiezaStockEPService piezaStockEPService) {
		super.service = piezaStockEPService;
	}

}
