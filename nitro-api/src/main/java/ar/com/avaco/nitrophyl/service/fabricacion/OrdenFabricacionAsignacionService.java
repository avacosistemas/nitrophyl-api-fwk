package ar.com.avaco.nitrophyl.service.fabricacion;

import java.util.Optional;

import ar.com.avaco.fwk.core.component.service.NJService;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenFabricacionAsignacion;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrigenAsignacionOrdenFabricacion;

public interface OrdenFabricacionAsignacionService extends NJService<Long, OrdenFabricacionAsignacion> {
	Integer cantidadActivaPorOrdenFabricacion(Long idOrdenFabricacion);
	Integer cantidadActivaPorOrigen(Long idOrdenFabricacion, OrigenAsignacionOrdenFabricacion origen);
	Optional<OrdenFabricacionAsignacion> findByEntregaOrigen(Long idEntrega);
}
