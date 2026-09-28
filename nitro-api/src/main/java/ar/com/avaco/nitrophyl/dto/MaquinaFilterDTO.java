package ar.com.avaco.nitrophyl.dto;

import ar.com.avaco.fwk.core.component.dto.filter.SortPageDTO;

public class MaquinaFilterDTO extends SortPageDTO {

	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
