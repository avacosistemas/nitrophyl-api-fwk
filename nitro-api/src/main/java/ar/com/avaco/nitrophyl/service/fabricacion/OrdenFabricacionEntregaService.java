package ar.com.avaco.nitrophyl.service.fabricacion;

import ar.com.avaco.fwk.core.component.service.NJService;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenFabricacionEntrega;
import java.util.List;
import ar.com.avaco.nitrophyl.dto.PiezaLoteFabricacionDTO;

public interface OrdenFabricacionEntregaService extends NJService<Long, OrdenFabricacionEntrega> {
	List<PiezaLoteFabricacionDTO> listarPiezasProducidasAsociadasAlLote(Long idLote);

}
