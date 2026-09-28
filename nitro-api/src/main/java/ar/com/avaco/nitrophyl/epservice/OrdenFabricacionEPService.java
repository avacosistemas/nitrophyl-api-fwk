package ar.com.avaco.nitrophyl.epservice;

import java.util.List;
import java.util.Map;

import ar.com.avaco.fwk.core.component.dto.PageDTO;
import ar.com.avaco.nitrophyl.dto.ListadoOrdenFabricacionDTO;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionAsignacionDTO;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionDTO;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionEntregaDTO;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionFilterDTO;
import ar.com.avaco.nitrophyl.dto.OrdenTrabajoResponseDTO;
import ar.com.avaco.nitrophyl.dto.OrdenTrabajoResumenDTO;
import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;

public interface OrdenFabricacionEPService extends CRUDEPService<Long, OrdenFabricacionDTO> {

	PageDTO<ListadoOrdenFabricacionDTO> listFilterCount(OrdenFabricacionFilterDTO ordenFabricacionFilterDTO);

	void create(Long idOrdenCompra);

	void asignar(Long idOrdenFabricacion, OrdenFabricacionAsignacionDTO asignacion);

	void registrarEntrega(Long idOrdenFabricacion, OrdenFabricacionEntregaDTO entrega);

	OrdenTrabajoResponseDTO generarOrdenTrabajo(Long idOrdenFabricacion);

	Map<String, List<OrdenTrabajoResumenDTO>> generarResumen(List<Long> ids);

	void reordenar(Long idOrdenFabricacion, Integer nuevaPosicion);


}