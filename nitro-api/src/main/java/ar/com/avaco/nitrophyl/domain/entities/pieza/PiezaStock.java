package ar.com.avaco.nitrophyl.domain.entities.pieza;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import ar.com.avaco.fwk.core.domain.AuditableEntity;
import ar.com.avaco.fwk.core.exception.ErrorValidationException;
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
@Table(name = "PIEZA_STOCK")
public class PiezaStock extends AuditableEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PIEZA_STOCK_SEQ")
	@GenericGenerator(name = "PIEZA_STOCK_SEQ", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@org.hibernate.annotations.Parameter(name = "sequence_name", value = "PIEZA_STOCK_SEQ"),
			@org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
			@org.hibernate.annotations.Parameter(name = "increment_size", value = "1") })
	@Column(name = "ID_PIEZA_STOCK", unique = true, nullable = false)
	private Long id;

	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PIEZA", nullable = false, unique = true)
	private Pieza pieza;

	@OneToMany(mappedBy = "piezaStock", fetch = FetchType.LAZY)
	@Builder.Default
	private Set<PiezaStockLote> detalles = new HashSet<PiezaStockLote>();

	@Column(name = "STOCK_FISICO", nullable = false)
	@Builder.Default
	private Integer stockFisico = 0;

	@Column(name = "STOCK_RESERVADO", nullable = false)
	@Builder.Default
	private Integer stockReservado = 0;

	public void incrementarStockFisico(Integer cantidad) {
		validarPositivo(cantidad);
		stockFisico = valor(stockFisico) + cantidad;
	}

	public void modificarStockReservado(Integer variacion) {
		if (variacion == null) throw new ErrorValidationException("La variación de la reserva es obligatoria", null);
		int reservadoNuevo = valor(stockReservado) + variacion;
		if (reservadoNuevo < 0 || reservadoNuevo > valor(stockFisico))
			throw new ErrorValidationException("El stock reservado debe estar entre cero y el stock físico", null);
		stockReservado = reservadoNuevo;
	}

	@Transient
	public Integer getStockDisponible() {
		return valor(stockFisico) - valor(stockReservado);
	}

	private void validarPositivo(Integer cantidad) {
		if (cantidad == null || cantidad <= 0)
			throw new ErrorValidationException("La cantidad debe ser mayor a cero", null);
	}

	private int valor(Integer valor) { return valor == null ? 0 : valor; }
}
