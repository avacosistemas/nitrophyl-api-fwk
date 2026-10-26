package ar.com.avaco.nitrophyl.repository.pieza;

import ar.com.avaco.fwk.core.component.repository.NJRepository;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaMolde;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PiezaMoldeRepository extends NJRepository<Long, PiezaMolde>, PiezaMoldeRepositoryCustom {

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Transactional
	@Query("DELETE FROM PiezaMolde pm WHERE pm.id = :id")
	int deleteAssociationById(@Param("id") Long id);

}
