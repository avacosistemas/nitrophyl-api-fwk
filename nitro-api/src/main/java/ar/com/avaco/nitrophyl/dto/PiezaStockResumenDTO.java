package ar.com.avaco.nitrophyl.dto;

import java.util.ArrayList;
import java.util.List;

import ar.com.avaco.fwk.core.component.dto.entity.DTOAuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PiezaStockResumenDTO extends DTOAuditableEntity<Long> {
	private Long id;
	private Long idPieza;
	private String codigo;
	private String denominacion;
	private Integer stockFisico;
	private Integer stockReservado;
	private Integer stockDisponible;
	private List<PiezaStockDTO> grupos = new ArrayList<PiezaStockDTO>();
}
