package ar.com.avaco.nitrophyl.dto;

import ar.com.avaco.fwk.core.component.dto.filter.SortPageDTO;
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
public class PiezaStockFilterDTO extends SortPageDTO {

	private Long idFormula;
	
	private String codigo;
	
	private String denominacion;
	
}
