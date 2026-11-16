package ar.com.avaco.nitrophyl.repository.pieza;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.com.avaco.fwk.core.component.repository.NJRepository;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStock;

public interface PiezaStockCabeceraRepository extends NJRepository<Long, PiezaStock>, PiezaStockCabeceraRepositoryCustom {

	Optional<PiezaStock> findByPiezaId(Long idPieza);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT s FROM PiezaStock s WHERE s.pieza.id = :idPieza")
	Optional<PiezaStock> buscarPorPiezaParaActualizar(@Param("idPieza") Long idPieza);
}
