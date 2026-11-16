package ar.com.avaco.nitrophyl.repository.pieza;

import ar.com.avaco.fwk.core.component.repository.NJRepository;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStockLote;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PiezaStockRepository extends NJRepository<Long, PiezaStockLote>, PiezaStockRepositoryCustom {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT s FROM PiezaStockLote s LEFT JOIN FETCH s.piezaStock WHERE s.piezaStock.pieza.id = :idPieza AND s.firmaLotes = :firma")
	Optional<PiezaStockLote> buscarExactoParaActualizar(@Param("idPieza") Long idPieza, @Param("firma") String firma);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT s FROM PiezaStockLote s LEFT JOIN FETCH s.piezaStock WHERE s.id = :id")
	Optional<PiezaStockLote> buscarPorIdParaActualizar(@Param("id") Long id);

	@Query("SELECT DISTINCT s FROM PiezaStockLote s LEFT JOIN FETCH s.piezaStock WHERE s.piezaStock.pieza.id = :idPieza ORDER BY s.id")
	List<PiezaStockLote> listarPorPieza(@Param("idPieza") Long idPieza);

	@Query("SELECT DISTINCT s FROM PiezaStockLote s WHERE CONCAT(',', s.firmaLotes, ',') LIKE CONCAT('%,', :codigoLote, ',%') ORDER BY s.piezaStock.pieza.denominacion, s.id")
	List<PiezaStockLote> listarPorLote(@Param("codigoLote") String codigoLote);

}
