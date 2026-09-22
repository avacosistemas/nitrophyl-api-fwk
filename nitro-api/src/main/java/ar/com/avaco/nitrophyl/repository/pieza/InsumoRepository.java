package ar.com.avaco.nitrophyl.repository.pieza;

import ar.com.avaco.fwk.core.component.repository.NJRepository;
import ar.com.avaco.nitrophyl.domain.entities.pieza.insumo.Insumo;

public interface InsumoRepository extends NJRepository<Long, Insumo>, InsumoRepositoryCustom {

}