package ar.com.avaco.nitrophyl.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.avaco.fwk.core.component.controller.AbstractDTORestController;
import ar.com.avaco.fwk.core.component.dto.JSONResponse;
import ar.com.avaco.fwk.core.component.dto.PageDTO;
import ar.com.avaco.fwk.core.exception.BusinessException;
import ar.com.avaco.nitrophyl.dto.PiezaBaseDTO;
import ar.com.avaco.nitrophyl.dto.PiezaComboDTO;
import ar.com.avaco.nitrophyl.dto.PiezaCreacionDTO;
import ar.com.avaco.nitrophyl.dto.PiezaDTO;
import ar.com.avaco.nitrophyl.dto.PiezaEdicionDTO;
import ar.com.avaco.nitrophyl.dto.PiezaFilterDTO;
import ar.com.avaco.nitrophyl.dto.PiezaGrillaDTO;
import ar.com.avaco.nitrophyl.dto.PiezaPUTDTO;
import ar.com.avaco.nitrophyl.epservice.PiezaEPService;

@RestController
public class PiezaRestController extends AbstractDTORestController<PiezaDTO, Long, PiezaEPService> {

	@RequestMapping(value = "/pieza/hojadeproceso", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> hojadeproceso(@RequestParam Long idPieza) {
		JSONResponse response = new JSONResponse();
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/pieza", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> list(PiezaFilterDTO filterDTO) {
		PageDTO<PiezaGrillaDTO> page = this.service.listGrilla(filterDTO);
		JSONResponse response = new JSONResponse();
		response.setData(page);
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/pieza/combo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> listombo(@RequestParam(required = false) String nombre,
			@RequestParam(required = false) Long idCliente) {
		List<PiezaComboDTO> list = this.service.listCombo(nombre, idCliente);
		JSONResponse response = new JSONResponse();
		response.setData(list);
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/pieza/{idPieza}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> getByIdEdicion(@PathVariable Long idPieza) {
		PiezaEdicionDTO pieza = this.service.getByIdEdicion(idPieza);
		JSONResponse response = new JSONResponse();
		response.setData(pieza);
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/pieza", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> create(@RequestBody PiezaCreacionDTO dto) {
		PiezaCreacionDTO piezaCreacionDTO = this.service.create(dto);
		JSONResponse response = new JSONResponse();
		response.setData(piezaCreacionDTO);
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/pieza/copiar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> copiar(@RequestBody PiezaBaseDTO dto) throws BusinessException {
		this.service.copiar(dto);
		JSONResponse response = new JSONResponse();
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/pieza/nuevaRevision/{idPieza}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> nuevaRevision(@PathVariable Long idPieza) {
		this.service.nuevaRevision(idPieza);
		JSONResponse response = new JSONResponse();
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/pieza/marcarvigente/{idPieza}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> marcarVigente(@PathVariable Long idPieza) {
		this.service.marcarVigente(idPieza);
		JSONResponse response = new JSONResponse();
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/pieza/{idPieza}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> update(@PathVariable Long idPieza, @RequestBody PiezaPUTDTO piezaFormula) {
		this.service.update(idPieza, piezaFormula);
		JSONResponse response = new JSONResponse();
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@Resource(name = "piezaEPService")
	public void setService(PiezaEPService piezaEPService) {
		super.service = piezaEPService;
	}

}