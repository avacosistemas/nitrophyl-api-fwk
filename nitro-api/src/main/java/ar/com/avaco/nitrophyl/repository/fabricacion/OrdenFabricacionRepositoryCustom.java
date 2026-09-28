package ar.com.avaco.nitrophyl.repository.fabricacion;

import ar.com.avaco.fwk.core.component.dto.PageDTO;
import ar.com.avaco.nitrophyl.dto.ListadoOrdenFabricacionDTO;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionFilterDTO;

public interface OrdenFabricacionRepositoryCustom {

	Long obtenerSiguienteNumero(Integer anio);

	PageDTO<ListadoOrdenFabricacionDTO> listFilterCount(OrdenFabricacionFilterDTO filter);

}
