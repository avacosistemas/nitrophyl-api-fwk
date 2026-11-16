package ar.com.avaco.nitrophyl.dto;

import lombok.Data;

@Data
public class AsignacionStockOrdenFabricacionDTO {
	private Long idPiezaStockLote;
	private Integer cantidad;
	private String observaciones;
}
