package ar.com.avaco.nitrophyl.repository.fabricacion;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.com.avaco.fwk.core.component.repository.NJRepository;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenFabricacionAsignacion;

public interface OrdenFabricacionAsignacionRepository
			extends NJRepository<Long, OrdenFabricacionAsignacion>, OrdenFabricacionAsignacionRepositoryCustom {

	@Query("SELECT COALESCE(SUM(a.cantidadAsignada - a.cantidadLiberada), 0) "
			+ "FROM OrdenFabricacionAsignacion a WHERE a.ordenFabricacion.id = :idOrdenFabricacion")
	Integer cantidadActivaPorOrdenFabricacion(@Param("idOrdenFabricacion") Long idOrdenFabricacion);

	@Query("SELECT COALESCE(SUM(a.cantidadAsignada - a.cantidadLiberada), 0) "
			+ "FROM OrdenFabricacionAsignacion a WHERE a.ordenFabricacion.id = :idOrdenFabricacion "
			+ "AND a.origen = :origen")
	Integer cantidadActivaPorOrigen(@Param("idOrdenFabricacion") Long idOrdenFabricacion,
			@Param("origen") ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrigenAsignacionOrdenFabricacion origen);

	@Query("SELECT a FROM OrdenFabricacionAsignacion a WHERE a.entregaOrigen.id = :idEntrega")
	Optional<OrdenFabricacionAsignacion> findByEntregaOrigen(@Param("idEntrega") Long idEntrega);
}
