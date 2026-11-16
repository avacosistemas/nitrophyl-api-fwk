package ar.com.avaco.nitrophyl.service.fabricacion;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import ar.com.avaco.fwk.core.component.service.NJBaseService;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenFabricacionEntrega;
import ar.com.avaco.nitrophyl.repository.fabricacion.OrdenFabricacionEntregaRepository;
import java.util.List;
import ar.com.avaco.nitrophyl.dto.PiezaLoteFabricacionDTO;

//@Transactional
@Service("ordenFabricacionEntregaService")
public class OrdenFabricacionEntregaServiceImpl extends NJBaseService<Long, OrdenFabricacionEntrega, OrdenFabricacionEntregaRepository>
		implements OrdenFabricacionEntregaService {

	@Resource(name = "ordenFabricacionEntregaRepository")
	void setRepository(OrdenFabricacionEntregaRepository ordenFabricacionEntregaRepository) {
		this.repository = ordenFabricacionEntregaRepository;
	}

	@Override
	public List<PiezaLoteFabricacionDTO> listarPiezasProducidasAsociadasAlLote(Long idLote) {
		return repository.listarPiezasProducidasAsociadasAlLote(idLote);
	}

}
