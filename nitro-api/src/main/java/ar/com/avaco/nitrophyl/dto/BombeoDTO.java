package ar.com.avaco.nitrophyl.dto;

import ar.com.avaco.fwk.core.component.dto.entity.DTOAuditableEntity;

public class BombeoDTO extends DTOAuditableEntity<Long> {

	private Long id;

	private String tipo;

	private Integer cantidad;

	private Double presion;

	private Integer posicion;

	public Integer getPosicion() {
		return posicion;
	}

	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPresion() {
		return presion;
	}

	public void setPresion(Double presion) {
		this.presion = presion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
