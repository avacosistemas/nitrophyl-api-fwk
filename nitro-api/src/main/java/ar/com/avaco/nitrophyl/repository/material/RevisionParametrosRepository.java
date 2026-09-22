package ar.com.avaco.nitrophyl.repository.material;

import ar.com.avaco.fwk.core.component.repository.NJRepository;
import ar.com.avaco.nitrophyl.domain.entities.formula.RevisionParametros;

public interface RevisionParametrosRepository extends NJRepository<Long, RevisionParametros>, RevisionParametrosRepositoryCustom {

	void deleteByFormulaId(Long idFormula);

}
