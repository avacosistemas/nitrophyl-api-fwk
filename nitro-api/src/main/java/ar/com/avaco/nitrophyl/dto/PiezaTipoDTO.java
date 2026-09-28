package ar.com.avaco.nitrophyl.dto;

import ar.com.avaco.fwk.core.component.dto.entity.DTOAuditableEntity;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaTipo;

public class PiezaTipoDTO extends DTOAuditableEntity<Long> {

	private Long id;
	private String nombre;

	public PiezaTipoDTO(PiezaTipo x) {
		this.id = x.getId();
		this.nombre = x.getNombre();
	}

	public PiezaTipoDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
