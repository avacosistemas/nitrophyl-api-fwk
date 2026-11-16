package ar.com.avaco.nitrophyl.epservice;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.OrdenCompraDetallePedidoDTO;
import ar.com.avaco.nitrophyl.dto.OperacionOrdenCompraDetallePedidoDTO;

public interface OrdenCompraDetallePedidoEPService extends CRUDEPService<Long, OrdenCompraDetallePedidoDTO> {
	void modificarCantidad(Long idPedido, OperacionOrdenCompraDetallePedidoDTO operacion);
	void cancelar(Long idPedido, String motivo);

}
