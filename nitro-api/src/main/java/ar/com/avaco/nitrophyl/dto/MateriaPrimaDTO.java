package ar.com.avaco.nitrophyl.dto;

import ar.com.avaco.fwk.core.component.dto.entity.DTOAuditableEntity;
import ar.com.avaco.nitrophyl.domain.entities.pieza.insumo.UnidadMedidaMateriaPrima;

public class MateriaPrimaDTO extends DTOAuditableEntity<Long> {

	private Long id;

	private String nombre;

	private Double cantidadStock;

	private UnidadMedidaMateriaPrima unidadMedidaStock;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getCantidadStock() {
		return cantidadStock;
	}

	public void setCantidadStock(Double cantidadStock) {
		this.cantidadStock = cantidadStock;
	}

	public UnidadMedidaMateriaPrima getUnidadMedidaStock() {
		return unidadMedidaStock;
	}

	public void setUnidadMedidaStock(UnidadMedidaMateriaPrima unidadMedidaStock) {
		this.unidadMedidaStock = unidadMedidaStock;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
