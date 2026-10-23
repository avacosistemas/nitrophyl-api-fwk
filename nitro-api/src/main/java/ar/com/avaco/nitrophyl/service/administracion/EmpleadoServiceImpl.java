package ar.com.avaco.nitrophyl.service.administracion;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ar.com.avaco.fwk.core.component.service.NJBaseService;
import ar.com.avaco.fwk.security.domain.Usuario;
import ar.com.avaco.nitrophyl.repository.administracion.EmpleadoRepository;

@Service("empleadoService")
public class EmpleadoServiceImpl extends NJBaseService<Long, Usuario, EmpleadoRepository> implements EmpleadoService {

	@Resource(name = "empleadoRepository")
	void setRepository(EmpleadoRepository empleadoRepository) {
		this.repository = empleadoRepository;
	}

}
