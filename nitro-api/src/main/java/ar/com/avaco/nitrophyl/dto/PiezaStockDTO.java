/**
 * 
 */
package ar.com.avaco.nitrophyl.dto;

import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import java.util.ArrayList;
import java.util.List;

import ar.com.avaco.fwk.core.component.dto.entity.DTOAuditableEntity;
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
public class PiezaStockDTO extends DTOAuditableEntity<Long> {

	private Long id;

	private String codigo;

	private String denominacion;

	private String tipo;

	private String material;

	private String formula;
	
	private Integer stockFisico;

	private Integer stockReservado;

	private Integer stockDisponible;

	private String firmaLotes;

	private Long idPieza;

	@Builder.Default
	private List<LoteStockDTO> lotes = new ArrayList<LoteStockDTO>();
	
	@Override
	public ProjectionList getProjections() {
		return Projections.projectionList().add(Projections.property("id"), "id")
				.add(Projections.property("piezaStock.pieza.id"), "idPieza")
				.add(Projections.property("piezaStock.pieza.denominacion"), "denominacion")
				.add(Projections.property("piezaStock.pieza.codigo"), "codigo")
				.add(Projections.property("piezaStock.pieza.tipo.nombre"), "tipo")
				.add(Projections.property("piezaStock.pieza.detalleFormula.formula.nombre"), "formula")
				.add(Projections.property("piezaStock.pieza.detalleFormula.formula.material.nombre"), "material")
				.add(Projections.property("stockFisico"), "stockFisico")
				.add(Projections.property("fechaActualizacion"), "fechaActualizacion")
				.add(Projections.property("stockReservado"), "stockReservado")
				.add(Projections.property("firmaLotes"), "firmaLotes");
	}
	
}
