package ar.com.avaco.nitrophyl.repository.material;

import ar.com.avaco.fwk.core.component.repository.NJRepository;
import ar.com.avaco.nitrophyl.domain.entities.formula.Material;

public interface MaterialRepository extends NJRepository<Long, Material>, MaterialRepositoryCustom {

}
