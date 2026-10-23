package ar.com.avaco.nitrophyl.dto;

import java.time.LocalDate;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import ar.com.avaco.fwk.core.component.dto.CustomDTO;
import ar.com.avaco.fwk.core.component.dto.entity.DTOEntity;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenFabricacionEntrega;
import ar.com.avaco.nitrophyl.domain.entities.lote.Lote;

public class OrdenFabricacionEntregaListadoDTO extends DTOEntity<Long> implements CustomDTO<OrdenFabricacionEntrega> {

	private Long id;

	private Integer cantidad;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate fecha;

	private String lotes;

	private String operario;

	@Override
	public void fromEntity(OrdenFabricacionEntrega entity) {
		this.id = entity.getId();
		this.cantidad = entity.getCantidad();
		this.fecha = entity.getFecha();
		this.lotes = entity.getLotes().stream().map(Lote::getNroLote).collect(Collectors.joining(", "));
		this.operario = entity.getOperario().getNombreApellido();
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getLotes() {
		return lotes;
	}

	public String getOperario() {
		return operario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLotes(String lotes) {
		this.lotes = lotes;
	}

	public void setOperario(String operario) {
		this.operario = operario;
	}

}
