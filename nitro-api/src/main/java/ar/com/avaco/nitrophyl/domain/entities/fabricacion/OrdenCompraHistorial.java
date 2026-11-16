package ar.com.avaco.nitrophyl.domain.entities.fabricacion;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import ar.com.avaco.fwk.core.domain.AuditableEntity;

/** Semantic audit event anchored to its purchase order; the affected entity is identified generically. */
@Entity
@Table(name = "ORDEN_COMPRA_HISTORIAL")
public class OrdenCompraHistorial extends AuditableEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ORDEN_COMPRA_HISTORIAL_SEQ")
	@GenericGenerator(name = "ORDEN_COMPRA_HISTORIAL_SEQ", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@org.hibernate.annotations.Parameter(name = "sequence_name", value = "ORDEN_COMPRA_HISTORIAL_SEQ"),
			@org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
			@org.hibernate.annotations.Parameter(name = "increment_size", value = "1") })
	@Column(name = "ID_ORDEN_COMPRA_HISTORIAL", unique = true, nullable = false)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_ORDEN_COMPRA", nullable = false)
	private OrdenCompra ordenCompra;

	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO_EVENTO", nullable = false, length = 80)
	private TipoEventoOrdenCompra tipoEvento;

	@Column(name = "TIPO_ENTIDAD", nullable = false, length = 80)
	private String tipoEntidad;

	@Column(name = "ID_ENTIDAD")
	private Long idEntidad;

	@Column(name = "MOTIVO", nullable = false, length = 1000)
	private String motivo;

	@Column(name = "DETALLE", length = 4000)
	private String detalle;

	@Column(name = "FECHA_EVENTO", nullable = false)
	private LocalDateTime fechaEvento;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public OrdenCompra getOrdenCompra() { return ordenCompra; }
	public void setOrdenCompra(OrdenCompra ordenCompra) { this.ordenCompra = ordenCompra; }
	public TipoEventoOrdenCompra getTipoEvento() { return tipoEvento; }
	public void setTipoEvento(TipoEventoOrdenCompra tipoEvento) { this.tipoEvento = tipoEvento; }
	public String getTipoEntidad() { return tipoEntidad; }
	public void setTipoEntidad(String tipoEntidad) { this.tipoEntidad = tipoEntidad; }
	public Long getIdEntidad() { return idEntidad; }
	public void setIdEntidad(Long idEntidad) { this.idEntidad = idEntidad; }
	public String getMotivo() { return motivo; }
	public void setMotivo(String motivo) { this.motivo = motivo; }
	public String getDetalle() { return detalle; }
	public void setDetalle(String detalle) { this.detalle = detalle; }
	public LocalDateTime getFechaEvento() { return fechaEvento; }
	public void setFechaEvento(LocalDateTime fechaEvento) { this.fechaEvento = fechaEvento; }
}
