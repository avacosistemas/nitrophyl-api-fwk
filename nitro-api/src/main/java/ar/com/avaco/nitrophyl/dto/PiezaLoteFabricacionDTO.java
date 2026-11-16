package ar.com.avaco.nitrophyl.dto;

import ar.com.avaco.fwk.core.component.dto.entity.DTOAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PiezaLoteFabricacionDTO extends DTOAuditableEntity<Long> {
	private Long id;
	private String codigo;
	private String pieza;
	private Long cantidadProducidaAsociada;
}
