package ar.com.avaco.nitrophyl.controller;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.avaco.fwk.core.component.controller.AbstractDTORestController;
import ar.com.avaco.fwk.core.component.dto.JSONResponse;
import ar.com.avaco.fwk.core.component.dto.PageDTO;
import ar.com.avaco.nitrophyl.dto.PiezaMovimientoStockDTO;
import ar.com.avaco.nitrophyl.dto.PiezaMovimientoStockFilterDTO;
import ar.com.avaco.nitrophyl.dto.PiezaMovimientoStockListadoDTO;
import ar.com.avaco.nitrophyl.epservice.PiezaMovimientoStockEPService;
import ar.com.avaco.nitrophyl.filter.PiezaMovimientoStockFilter;

@RestController
public class PiezaMovimientoStockRestController
		extends AbstractDTORestController<PiezaMovimientoStockDTO, Long, PiezaMovimientoStockEPService> {

	@RequestMapping(value = "/pieza/stock/movimiento", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> piezaStock(PiezaMovimientoStockFilterDTO filterDTO) {
		PageDTO<PiezaMovimientoStockListadoDTO> listFilterCount = this.service
				.listFilterCount(new PiezaMovimientoStockFilter(filterDTO), PiezaMovimientoStockListadoDTO.class);
		return OKPAGE(listFilterCount);
	}

	@RequestMapping(value = "/pieza/stock/movimiento/ingreso/manual", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> ingresoManual(@RequestBody PiezaMovimientoStockDTO dto) {
		this.service.registrarIngresoManual(dto.getIdPieza(), dto.getCantidad(), dto.getFecha(), dto.getObservacion());
		return OKDATA(true);
	}

	@Resource(name = "piezaMovimientoStockEPService")
	public void setService(PiezaMovimientoStockEPService piezaMovimientoStockEPService) {
		super.service = piezaMovimientoStockEPService;
	}

}