package ar.com.avaco.nitrophyl.ws.dto;

import ar.com.avaco.fwk.core.component.dto.SortPageDTO;

public class ConfiguracionPruebaFilterDTO extends SortPageDTO {

	private String idFormula;

	public String getIdFormula() {
		return idFormula;
	}

	public void setIdFormula(String idFormula) {
		this.idFormula = idFormula;
	}

}
