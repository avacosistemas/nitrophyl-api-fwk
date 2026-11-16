package ar.com.avaco.nitrophyl.repository.pieza;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.com.avaco.fwk.core.component.repository.NJRepository;
import ar.com.avaco.nitrophyl.domain.entities.pieza.EstadoReserva;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStockReserva;

public interface PiezaReservaStockRepository extends NJRepository<Long, PiezaStockReserva>, PiezaReservaStockRepositoryCustom {

	@Query("SELECT COALESCE(SUM(r.cantidadReservada), 0) FROM PiezaStockReserva r "
			+ "WHERE r.ordenFabricacion.id = :idOrdenFabricacion AND r.estado = :estado")
	Integer cantidadActivaPorOrdenFabricacion(@Param("idOrdenFabricacion") Long idOrdenFabricacion,
			@Param("estado") EstadoReserva estado);

	@Query("SELECT COALESCE(SUM(r.cantidadReservada), 0) FROM PiezaStockReserva r "
			+ "WHERE r.ordenFabricacion.id = :idOrdenFabricacion AND r.estado IN "
			+ "(ar.com.avaco.nitrophyl.domain.entities.pieza.EstadoReserva.ACTIVA, ar.com.avaco.nitrophyl.domain.entities.pieza.EstadoReserva.CONSUMIDA)")
	Integer cantidadCubiertaPorOrdenFabricacion(@Param("idOrdenFabricacion") Long idOrdenFabricacion);

	List<PiezaStockReserva> findByOrdenFabricacionIdOrderByFechaReservaAscIdAsc(Long idOrdenFabricacion);

	List<PiezaStockReserva> findByOrdenFabricacionIdAndEstadoOrderByFechaReservaAscIdAsc(
			Long idOrdenFabricacion, EstadoReserva estado);

	List<PiezaStockReserva> findByEntregaOrigenIdAndEstado(Long idEntrega, EstadoReserva estado);
}
