package ar.com.avaco.nitrophyl.epservice;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.avaco.nitrophyl.domain.entities.pieza.Proceso;
import ar.com.avaco.nitrophyl.domain.entities.pieza.esquema.Esquema;
import ar.com.avaco.nitrophyl.domain.entities.pieza.esquema.EsquemaPaso;
import ar.com.avaco.nitrophyl.dto.EsquemaDTO;
import ar.com.avaco.nitrophyl.service.pieza.EsquemaService;
import ar.com.avaco.nitrophyl.service.pieza.ProcesoService;
import ar.com.avaco.fwk.core.component.epservice.CRUDAuditableEPBaseService;

@Service("esquemaEPService")
public class EsquemaEPServiceImpl extends CRUDAuditableEPBaseService<Long, EsquemaDTO, Esquema, EsquemaService>
		implements EsquemaEPService {

	@Autowired
	private ProcesoService procesoService;
	
	public EsquemaEPServiceImpl() {
		super(Esquema.class, EsquemaDTO.class);
	}
	
	

	@Override
	protected Esquema convertToEntityForUpdate(EsquemaDTO dto) {
		Esquema entity = this.service.get(dto.getId());
		entity.setImagen(dto.getImagen());
		entity.setPosicion(dto.getPosicion());
		entity.setTitulo(dto.getTitulo());
		entity.getPasos().clear();
		dto.getPasos().forEach(pdto -> {
			EsquemaPaso paso = new EsquemaPaso();
			paso.setDescripcion(pdto.getDescripcion());
			paso.setEsquema(entity);
			paso.setPaso(pdto.getPaso());
			paso.setPosicion(pdto.getPosicion());
			entity.getPasos().add(paso);
		});
		return entity;
	}

	@Override
	protected Esquema convertToEntityForSave(EsquemaDTO dto) {
		Proceso proceso = procesoService.get(dto.getIdProceso());
		Esquema entity = new Esquema();
		entity.setProceso(proceso);
		entity.setId(dto.getId());
		entity.setImagen(dto.getImagen());
		entity.setPosicion(dto.getPosicion());
		entity.setTitulo(dto.getTitulo());
		entity.getPasos().forEach(paso -> paso.setEsquema(entity));
		return entity;
	}

	@Override
	public void reordenar(Long idEsquema, Integer posicion) {
		this.service.reordenar(idEsquema, posicion);
	}

	@Override
	@Resource(name = "esquemaService")
	protected void setService(EsquemaService service) {
		this.service = service;
	}

	@Override
	public List<EsquemaDTO> listEsquemas(Long idProceso) {
		List<Esquema> listEsquemas = this.service.listEsquemas(idProceso);
		return convertToDtos(listEsquemas);
	}

}