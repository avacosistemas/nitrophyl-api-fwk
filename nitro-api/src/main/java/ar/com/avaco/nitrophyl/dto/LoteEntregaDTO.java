package ar.com.avaco.nitrophyl.dto;

import ar.com.avaco.fwk.core.component.dto.entity.DTOAuditableEntity;

public class LoteEntregaDTO extends DTOAuditableEntity<Long> {

	private Long id;

	private String nroLote;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNroLote() {
		return nroLote;
	}

	public void setNroLote(String nroLote) {
		this.nroLote = nroLote;
	}

}
