package ar.com.avaco.nitrophyl.epservice;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPBaseService;
import ar.com.avaco.fwk.security.domain.Usuario;
import ar.com.avaco.fwk.security.service.UsuarioService;
import ar.com.avaco.nitrophyl.dto.EmpleadoDTO;
import ar.com.avaco.nitrophyl.service.administracion.EmpleadoService;

@Service("empleadoEPService")
public class EmpleadoEPServiceImpl extends CRUDEPBaseService<Long, EmpleadoDTO, Usuario, EmpleadoService>
		implements EmpleadoEPService {

	@Autowired
	private UsuarioService usuarioService;
	
	public EmpleadoEPServiceImpl() {
		super(Usuario.class, EmpleadoDTO.class);
	}

	@Override
	public List<EmpleadoDTO> listOperarios() {
		return this.listByPermiso("OPERARIO");
	}
	
	private List<EmpleadoDTO> listByPermiso(String permiso) {
		List<Usuario> byPermiso = this.usuarioService.findByPermiso(permiso);
		List<EmpleadoDTO> empleados = new ArrayList<EmpleadoDTO>();
		for (Usuario u : byPermiso) {
			EmpleadoDTO e = new EmpleadoDTO();
			e.setApellido(u.getApellido());
			e.setEmail(u.getEmail());
			e.setId(u.getId());
			e.setNombre(u.getNombre());
			e.setUsername(u.getUsername());
			empleados.add(e);
		}
		return empleados;
				
	}
	
	@Override
	@Resource(name = "empleadoService")
	protected void setService(EmpleadoService service) {
		this.service = service;
	}

}
