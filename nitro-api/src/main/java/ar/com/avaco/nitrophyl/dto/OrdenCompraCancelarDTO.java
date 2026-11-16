package ar.com.avaco.nitrophyl.dto;

import ar.com.avaco.fwk.core.component.dto.entity.DTOEntity;

public class OrdenCompraCancelarDTO extends DTOEntity<Long> {

	private Long id;

	private String observaciones;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

}
