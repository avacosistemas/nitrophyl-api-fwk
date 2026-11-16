/**
 * 
 */
package ar.com.avaco.nitrophyl.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import ar.com.avaco.fwk.core.component.dto.entity.DTOAuditableEntity;
import ar.com.avaco.nitrophyl.domain.entities.pieza.OrigenMovimientoStock;
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
public class PiezaMovimientoStockDTO extends DTOAuditableEntity<Long> {

	private Long id;

	private Long idPieza;

	private Integer cantidad;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate fecha;

	private String observacion;

	private OrigenMovimientoStock origen;

	@Builder.Default
	private List<Long> idLotesOrigen = new ArrayList<Long>();

}
