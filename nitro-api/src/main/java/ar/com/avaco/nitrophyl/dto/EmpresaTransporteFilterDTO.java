package ar.com.avaco.nitrophyl.dto;

import java.util.ArrayList;
import java.util.List;

import ar.com.avaco.fwk.core.component.dto.filter.SortPageDTO;

public class EmpresaTransporteFilterDTO extends SortPageDTO {

	private String nombre;

	private String direccion;

	private List<String> mediosEnvio = new ArrayList<String>();

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public List<String> getMediosEnvio() {
		return mediosEnvio;
	}

	public void setMediosEnvio(List<String> mediosEnvio) {
		this.mediosEnvio = mediosEnvio;
	}

}
