package ar.com.avaco.nitrophyl.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import ar.com.avaco.fwk.core.component.dto.entity.DTOAuditableEntity;

public class OrdenFabricacionEntregaDTO extends DTOAuditableEntity<Long> {

	private Long id;

	private Integer cantidad;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate fecha;

	private List<Long> idLote = new ArrayList<Long>();

	private Long idUsuario;

	private List<LoteEntregaDTO> lotes = new ArrayList<LoteEntregaDTO>();

	private EmpleadoDTO operario;

	public EmpleadoDTO getOperario() {
		return operario;
	}

	public void setOperario(EmpleadoDTO operario) {
		this.operario = operario;
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

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public List<Long> getIdLote() {
		return idLote;
	}

	public void setIdLote(List<Long> idLote) {
		this.idLote = idLote;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<LoteEntregaDTO> getLotes() {
		return lotes;
	}

	public void setLotes(List<LoteEntregaDTO> lotes) {
		this.lotes = lotes;
	}

}
