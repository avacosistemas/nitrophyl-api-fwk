package ar.com.avaco.nitrophyl.domain.entities.pieza;

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
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenFabricacion;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenFabricacionEntrega;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "PIEZA_RESERVA_STOCK")
public class PiezaStockReserva extends AuditableEntity<Long> {

	private static final long serialVersionUID = 5272753625649794342L;

	@Id
	@GeneratedValue(generator = "PIEZA_RESERVA_STOCK_SEQ")
	@GenericGenerator(name = "PIEZA_RESERVA_STOCK_SEQ", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@org.hibernate.annotations.Parameter(name = "sequence_name", value = "PIEZA_RESERVA_STOCK_SEQ"),
			@org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
			@org.hibernate.annotations.Parameter(name = "increment_size", value = "1") })
	@Column(name = "ID_PIEZA_RESERVA_STOCK", unique = true, nullable = false)
	private Long id;

	/** OF that owns the stock reservation. */
	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_ORDEN_FABRICACION", nullable = false)
	private OrdenFabricacion ordenFabricacion;

	@ManyToOne
	@JoinColumn(name = "ID_ORDEN_FAB_ENTREGA")
	private OrdenFabricacionEntrega entregaOrigen;

	@Column(name = "LOTES", length = 2000)
	private String lotes;

	@Column(name = "FECHA_RESERVA", nullable = false)
	private LocalDateTime fechaReserva;

	@Column(name = "CANTIDAD_RESERVADA", nullable = false)
	private Integer cantidadReservada;

	@Column(name = "ESTADO")
	@Enumerated(EnumType.STRING)
	private EstadoReserva estado;

	@Column(name = "FECHA_CAMBIO_ESTADO")
	private LocalDateTime fechaCambioEstado;

	public void setEstado(EstadoReserva estado) {
		if (this.estado == estado) return;
		this.estado = estado;
		this.fechaCambioEstado = estado == EstadoReserva.CANCELADA || estado == EstadoReserva.CONSUMIDA
				? LocalDateTime.now() : null;
	}
}
