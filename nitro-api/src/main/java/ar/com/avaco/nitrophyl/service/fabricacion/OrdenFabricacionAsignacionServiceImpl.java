package ar.com.avaco.nitrophyl.service.fabricacion;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ar.com.avaco.fwk.core.component.service.NJBaseService;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenFabricacionAsignacion;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrigenAsignacionOrdenFabricacion;
import ar.com.avaco.nitrophyl.repository.fabricacion.OrdenFabricacionAsignacionRepository;

@Service("ordenFabricacionAsignacionService")
public class OrdenFabricacionAsignacionServiceImpl extends
		NJBaseService<Long, OrdenFabricacionAsignacion, OrdenFabricacionAsignacionRepository>
		implements OrdenFabricacionAsignacionService {

	@Resource(name = "ordenFabricacionAsignacionRepository")
	void setRepository(OrdenFabricacionAsignacionRepository repository) {
		this.repository = repository;
	}

	@Override
	public Integer cantidadActivaPorOrdenFabricacion(Long idOrdenFabricacion) {
		return repository.cantidadActivaPorOrdenFabricacion(idOrdenFabricacion);
	}

	@Override
	public Integer cantidadActivaPorOrigen(Long idOrdenFabricacion, OrigenAsignacionOrdenFabricacion origen) {
		return repository.cantidadActivaPorOrigen(idOrdenFabricacion, origen);
	}

	@Override
	public Optional<OrdenFabricacionAsignacion> findByEntregaOrigen(Long idEntrega) {
		return repository.findByEntregaOrigen(idEntrega);
	}
}
