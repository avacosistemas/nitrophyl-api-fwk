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
public class CotizacionFilterDTO extends SortPageDTO {

	private Long idCliente;

	private Long idPieza;

	private Boolean soloVigentes;

	private String codigo;
	
}
