package ar.com.avaco.nitrophyl.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class PiezaStockLoteDisponibleDTO {
	private Long idPiezaStockLote;
	private Integer cantidadDisponible;
	private String firmaLotes;
	private List<LoteStockDTO> lotesOrigen = new ArrayList<LoteStockDTO>();
}
