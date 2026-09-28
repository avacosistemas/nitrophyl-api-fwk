package ar.com.avaco.nitrophyl.dto;

import ar.com.avaco.fwk.core.component.dto.entity.DTOAuditableEntity;

public class AdhesivoDTO extends DTOAuditableEntity<Long> {

	private Long id;
	private String nombre;

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
