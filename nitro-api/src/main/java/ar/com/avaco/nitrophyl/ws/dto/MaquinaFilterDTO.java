package ar.com.avaco.nitrophyl.ws.dto;

import ar.com.avaco.fwk.core.component.dto.SortPageDTO;

public class MaquinaFilterDTO extends SortPageDTO {

	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
