package ar.com.avaco.nitrophyl.repository.fabricacion;

import ar.com.avaco.fwk.core.component.repository.NJRepository;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenFabricacionEntrega;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ar.com.avaco.nitrophyl.dto.PiezaLoteFabricacionDTO;

public interface OrdenFabricacionEntregaRepository
		extends NJRepository<Long, OrdenFabricacionEntrega>, OrdenFabricacionEntregaRepositoryCustom {

	@Query("SELECT new ar.com.avaco.nitrophyl.dto.PiezaLoteFabricacionDTO(p.id, p.codigo, p.denominacion, SUM(e.cantidad)) "
			+ "FROM OrdenFabricacionEntrega e JOIN e.lotes l "
			+ "JOIN e.ordenFabricacion ofab JOIN ofab.ordenCompraDetallePedido pedido "
			+ "JOIN pedido.ordenCompraDetalle detalle JOIN detalle.pieza p "
			+ "WHERE l.id = :idLote GROUP BY p.id, p.codigo, p.denominacion ORDER BY p.denominacion")
	List<PiezaLoteFabricacionDTO> listarPiezasProducidasAsociadasAlLote(@Param("idLote") Long idLote);

}
