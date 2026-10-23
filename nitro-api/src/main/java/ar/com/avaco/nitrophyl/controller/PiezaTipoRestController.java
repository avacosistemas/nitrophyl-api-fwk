package ar.com.avaco.nitrophyl.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
import ar.com.avaco.nitrophyl.dto.PiezaTipoDTO;
import ar.com.avaco.nitrophyl.epservice.PiezaTipoEPService;

@RestController
public class PiezaTipoRestController extends AbstractDTORestController<PiezaTipoDTO, Long, PiezaTipoEPService> {

	@Override
	@RequestMapping(value = "/piezaTipo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> list() {
		List<PiezaTipoDTO> list = this.service.list();
		list = list.stream().sorted(new Comparator<PiezaTipoDTO>() {
			@Override
			public int compare(PiezaTipoDTO o1, PiezaTipoDTO o2) {
				return o1.getNombre().compareTo(o2.getNombre());
			}
		}).collect(Collectors.toList());
		return OKDATA(list);
	}

	@Override
	@RequestMapping(value = "/piezaTipo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> create(@RequestBody PiezaTipoDTO dto) throws BusinessException {
		return super.create(dto);
	}

	@Override
	@RequestMapping(value = "/piezaTipo/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> update(@PathVariable Long id, @RequestBody PiezaTipoDTO dto)
			throws BusinessException {
		return super.update(id, dto);
	}

	@Override
	@RequestMapping(value = "/piezaTipo/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONResponse> delete(@PathVariable Long id) throws BusinessException {
		return super.delete(id);
	}

	@Resource(name = "piezaTipoEPService")
	public void setService(PiezaTipoEPService piezaTipoEPService) {
		super.service = piezaTipoEPService;
	}

}