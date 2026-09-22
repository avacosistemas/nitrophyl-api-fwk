package ar.com.avaco.nitrophyl.ws.service;

import ar.com.avaco.nitrophyl.ws.dto.InsumoTratadoDTO;
import ar.com.avaco.fwk.core.component.epservice.CRUDAuditableEPService;

public interface InsumoTratadoEPService extends CRUDAuditableEPService<Long, InsumoTratadoDTO> {

	void updateCantidades(Long idPieza, Boolean requiereInsumos, Integer cantidadInsumos);

}