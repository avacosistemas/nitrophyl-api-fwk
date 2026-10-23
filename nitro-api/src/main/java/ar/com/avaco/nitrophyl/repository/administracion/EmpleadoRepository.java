package ar.com.avaco.nitrophyl.repository.administracion;

import ar.com.avaco.fwk.core.component.repository.NJRepository;
import ar.com.avaco.fwk.security.domain.Usuario;

public interface EmpleadoRepository extends NJRepository<Long, Usuario>, EmpleadoRepositoryCustom {

}