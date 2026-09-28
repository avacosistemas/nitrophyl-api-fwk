package ar.com.avaco.nitrophyl.dto;

import ar.com.avaco.fwk.core.component.dto.filter.SortPageDTO;

public class FormulaFilterDTO extends SortPageDTO {

	private String nombre;

	private Long idMaterial;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getIdMaterial() {
		return idMaterial;
	}

	public void setIdMaterial(Long idMaterial) {
		this.idMaterial = idMaterial;
	}

}
