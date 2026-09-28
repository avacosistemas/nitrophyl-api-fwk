package ar.com.avaco.nitrophyl.controller;

import java.util.List;
import java.util.Map;

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
import ar.com.avaco.nitrophyl.dto.ListadoOrdenFabricacionDTO;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionAsignacionDTO;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionDTO;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionEntregaDTO;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionFilterDTO;
import ar.com.avaco.nitrophyl.dto.OrdenTrabajoResponseDTO;
import ar.com.avaco.nitrophyl.dto.OrdenTrabajoResumenDTO;
import ar.com.avaco.nitrophyl.epservice.OrdenFabricacionEPService;

@RestController
public class OrdenFabricacionRestController
		extends AbstractDTORestController<OrdenFabricacionDTO, Long, OrdenFabricacionEPService> {

	@RequestMapping(value = "/ordenFabricacion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> listOrdenesFabricacion(OrdenFabricacionFilterDTO ordenFabricacionFilterDTO) {
		PageDTO<ListadoOrdenFabricacionDTO> listFilterCount = this.service.listFilterCount(ordenFabricacionFilterDTO);
		JSONResponse response = new JSONResponse();
		response.setData(listFilterCount);
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/ordenFabricacion/{idOrdenCompra}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> listOrdenesFabricacion(@PathVariable Long idOrdenCompra) {
		this.service.create(idOrdenCompra);
		JSONResponse response = new JSONResponse();
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/ordenFabricacion/asignar/{idOrdenFabricacion}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> asignar(@PathVariable Long idOrdenFabricacion,
			@RequestBody OrdenFabricacionAsignacionDTO asignacion) {
		this.service.asignar(idOrdenFabricacion, asignacion);
		JSONResponse response = new JSONResponse();
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/ordenFabricacion/reordenar/{idOrdenFabricacion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> asignar(@PathVariable Long idOrdenFabricacion,
			Integer nuevaPosicion) {
		this.service.reordenar(idOrdenFabricacion, nuevaPosicion);
		JSONResponse response = new JSONResponse();
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/ordenFabricacion/registrarEntrega/{idOrdenFabricacion}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> registrarEntrega(@PathVariable Long idOrdenFabricacion,
			@RequestBody OrdenFabricacionEntregaDTO entrega) {
		this.service.registrarEntrega(idOrdenFabricacion, entrega);
		JSONResponse response = new JSONResponse();
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/ordenFabricacion/ordenTrabajo/descargar/{idOrdenFabricacion}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> descargarOrdenTrabajo(@PathVariable Long idOrdenFabricacion) throws Exception {
		OrdenTrabajoResponseDTO ot = this.service.generarOrdenTrabajo(idOrdenFabricacion);
		JSONResponse response = new JSONResponse();
		response.setData(ot);
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/ordenFabricacion/ordenTrabajo/resumen", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> generarResumenOrdenesTrabajo(@RequestBody Map<String, List<Long>> body)
			throws Exception {
		List<Long> ids = body.get("ids");
		Map<String, List<OrdenTrabajoResumenDTO>> resumen = this.service.generarResumen(ids);
		JSONResponse response = new JSONResponse();
		response.setData(resumen);
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	@Resource(name = "ordenFabricacionEPService")
	public void setService(OrdenFabricacionEPService ordenFabricacionEPService) {
		super.service = ordenFabricacionEPService;
	}

}