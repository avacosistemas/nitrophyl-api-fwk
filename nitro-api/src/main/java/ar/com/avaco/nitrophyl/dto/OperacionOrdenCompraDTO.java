package ar.com.avaco.nitrophyl.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class OperacionOrdenCompraDTO {
	private List<AsignacionStockPedidoDTO> asignacionesStock = new ArrayList<AsignacionStockPedidoDTO>();
}
