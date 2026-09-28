package ar.com.avaco.nitrophyl.epservice;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.InsumoTratadoDTO;

public interface InsumoTratadoEPService extends CRUDEPService<Long, InsumoTratadoDTO> {

	void updateCantidades(Long idPieza, Boolean requiereInsumos, Integer cantidadInsumos);

}