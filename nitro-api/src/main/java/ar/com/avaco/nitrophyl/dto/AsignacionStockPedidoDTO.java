package ar.com.avaco.nitrophyl.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class AsignacionStockPedidoDTO {
	private Long idOrdenCompraDetallePedido;
	private List<AsignacionStockOrdenFabricacionDTO> asignaciones = new ArrayList<AsignacionStockOrdenFabricacionDTO>();
}
