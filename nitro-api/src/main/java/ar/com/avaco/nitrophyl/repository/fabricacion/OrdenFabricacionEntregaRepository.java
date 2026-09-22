package ar.com.avaco.nitrophyl.repository.fabricacion;

import ar.com.avaco.fwk.core.component.repository.NJRepository;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenFabricacionEntrega;

public interface OrdenFabricacionEntregaRepository
		extends NJRepository<Long, OrdenFabricacionEntrega>, OrdenFabricacionEntregaRepositoryCustom {

}