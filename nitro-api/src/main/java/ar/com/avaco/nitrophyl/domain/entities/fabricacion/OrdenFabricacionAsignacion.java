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
import org.hibernate.annotations.Parameter;

import ar.com.avaco.fwk.core.domain.AuditableEntity;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStockReserva;

/**
 * Records how much of an OF is currently covered by production or inventory.
 * The source delivery remains a production event; releasing an assignment
 * changes its active quantity without rewriting that event.
 */
@Entity
@Table(name = "ORDEN_FABRICACION_ASIGNACION")
public class OrdenFabricacionAsignacion extends AuditableEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ORDEN_FAB_ASIG_SEQ")
	@GenericGenerator(name = "ORDEN_FAB_ASIG_SEQ", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "ORDEN_FAB_ASIG_SEQ"),
			@Parameter(name = "initial_value", value = "1"),
			@Parameter(name = "increment_size", value = "1") })
	@Column(name = "ID_ORDEN_FAB_ASIGNACION", unique = true, nullable = false)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_ORDEN_FABRICACION", nullable = false)
	private OrdenFabricacion ordenFabricacion;

	@Enumerated(EnumType.STRING)
	@Column(name = "ORIGEN", nullable = false, length = 20)
	private OrigenAsignacionOrdenFabricacion origen;

	/** Set only for assignments whose source is a production event. */
	@ManyToOne
	@JoinColumn(name = "ID_ORDEN_FAB_ENTREGA")
	private OrdenFabricacionEntrega entregaOrigen;

	/** Set only for assignments whose source is reserved inventory. */
	@ManyToOne
	@JoinColumn(name = "ID_PIEZA_RESERVA_STOCK")
	private PiezaStockReserva reservaStockOrigen;

	@Column(name = "CANTIDAD_ASIGNADA", nullable = false)
	private Integer cantidadAsignada;

	/** Cumulative quantity released back to stock; active coverage is assigned minus released. */
	@Column(name = "CANTIDAD_LIBERADA", nullable = false)
	private Integer cantidadLiberada = 0;

	@Column(name = "FECHA_ASIGNACION", nullable = false)
	private LocalDateTime fechaAsignacion;

	@Column(name = "OBSERVACIONES", length = 2000)
	private String observaciones;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public OrdenFabricacion getOrdenFabricacion() { return ordenFabricacion; }
	public void setOrdenFabricacion(OrdenFabricacion ordenFabricacion) { this.ordenFabricacion = ordenFabricacion; }
	public OrigenAsignacionOrdenFabricacion getOrigen() { return origen; }
	public void setOrigen(OrigenAsignacionOrdenFabricacion origen) { this.origen = origen; }
	public OrdenFabricacionEntrega getEntregaOrigen() { return entregaOrigen; }
	public void setEntregaOrigen(OrdenFabricacionEntrega entregaOrigen) { this.entregaOrigen = entregaOrigen; }
	public PiezaStockReserva getReservaStockOrigen() { return reservaStockOrigen; }
	public void setReservaStockOrigen(PiezaStockReserva reservaStockOrigen) { this.reservaStockOrigen = reservaStockOrigen; }
	public Integer getCantidadAsignada() { return cantidadAsignada; }
	public void setCantidadAsignada(Integer cantidadAsignada) { this.cantidadAsignada = cantidadAsignada; }
	public Integer getCantidadLiberada() { return cantidadLiberada; }
	public void setCantidadLiberada(Integer cantidadLiberada) { this.cantidadLiberada = cantidadLiberada; }
	public LocalDateTime getFechaAsignacion() { return fechaAsignacion; }
	public void setFechaAsignacion(LocalDateTime fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }
	public String getObservaciones() { return observaciones; }
	public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

	public Integer getCantidadActiva() {
		return Math.max(0, (cantidadAsignada == null ? 0 : cantidadAsignada)
				- (cantidadLiberada == null ? 0 : cantidadLiberada));
	}
}
