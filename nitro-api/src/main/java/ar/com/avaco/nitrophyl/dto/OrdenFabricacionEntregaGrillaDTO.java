package ar.com.avaco.nitrophyl.dto;

import java.time.LocalDate;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import ar.com.avaco.fwk.core.component.dto.CustomDTO;
import ar.com.avaco.fwk.core.component.dto.entity.DTOEntity;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenFabricacionEntrega;
import ar.com.avaco.nitrophyl.domain.entities.lote.Lote;

public class OrdenFabricacionEntregaGrillaDTO extends DTOEntity<Long> implements CustomDTO<OrdenFabricacionEntrega> {

	private Long id;

	private Integer cantidad;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate fecha;

	private String lotes;

	private String operario;

	private String numeroOT;

	private String pieza;

	private String cliente;

	private String comprobante;

	private Long idOrdenFabricacion;

	@Override
	public void fromEntity(OrdenFabricacionEntrega entity) {
		this.id = entity.getId();
		this.cantidad = entity.getCantidad();
		this.fecha = entity.getFecha();
		this.lotes = entity.getLotes().stream().map(Lote::getNroLote).collect(Collectors.joining(", "));
		this.operario = entity.getOperario().getNombreApellido();
		this.numeroOT = entity.getOrdenFabricacion().getNumeroOT();
		this.idOrdenFabricacion = entity.getOrdenFabricacion().getId();
		this.pieza = entity.getOrdenFabricacion().getOrdenCompraDetallePedido().getOrdenCompraDetalle().getPieza()
				.getDescripcion();
		this.cliente = entity.getOrdenFabricacion().getOrdenCompraDetallePedido().getOrdenCompraDetalle()
				.getOrdenCompra().getCliente().getNombre();
		this.comprobante = entity.getOrdenFabricacion().getOrdenCompraDetallePedido().getOrdenCompraDetalle()
				.getOrdenCompra().getComprobante();
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

	public String getNumeroOT() {
		return numeroOT;
	}

	public void setNumeroOT(String numeroOT) {
		this.numeroOT = numeroOT;
	}

	public String getPieza() {
		return pieza;
	}

	public void setPieza(String pieza) {
		this.pieza = pieza;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getComprobante() {
		return comprobante;
	}

	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}

	public void setLotes(String lotes) {
		this.lotes = lotes;
	}

	public void setOperario(String operario) {
		this.operario = operario;
	}

	public Long getIdOrdenFabricacion() {
		return idOrdenFabricacion;
	}

	public void setIdOrdenFabricacion(Long idOrdenFabricacion) {
		this.idOrdenFabricacion = idOrdenFabricacion;
	}

}
