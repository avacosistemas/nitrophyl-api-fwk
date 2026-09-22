package ar.com.avaco.nitrophyl.repository.molde;

import java.util.List;

import ar.com.avaco.fwk.core.component.repository.NJRepository;
import ar.com.avaco.nitrophyl.domain.entities.molde.MoldeObservacion;

public interface MoldeObservacionRepository extends NJRepository<Long, MoldeObservacion>, MoldeObservacionRepositoryCustom {

	List<MoldeObservacion> findByIdMoldeOrderByFechaCreacionDesc(Long idMolde);
	
}
