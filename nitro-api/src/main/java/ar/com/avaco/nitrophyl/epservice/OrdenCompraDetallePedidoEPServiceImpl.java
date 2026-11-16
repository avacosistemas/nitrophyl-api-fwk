package ar.com.avaco.nitrophyl.epservice;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenCompraDetallePedido;
import ar.com.avaco.nitrophyl.dto.OrdenCompraDetallePedidoDTO;
import ar.com.avaco.nitrophyl.dto.OperacionOrdenCompraDetallePedidoDTO;
import ar.com.avaco.nitrophyl.service.fabricacion.OrdenCompraDetallePedidoService;
import ar.com.avaco.nitrophyl.service.fabricacion.OrdenFabricacionService;
import ar.com.avaco.fwk.core.exception.BusinessException;
import ar.com.avaco.fwk.core.exception.ErrorValidationException;
import ar.com.avaco.fwk.core.component.epservice.CRUDAuditableEPBaseService;

@Service("ordenCompraDetallePedidoEPService")
public class OrdenCompraDetallePedidoEPServiceImpl extends CRUDAuditableEPBaseService<Long, OrdenCompraDetallePedidoDTO, OrdenCompraDetallePedido, OrdenCompraDetallePedidoService>
		implements OrdenCompraDetallePedidoEPService {

	@Resource(name = "ordenFabricacionEPService")
	private OrdenFabricacionEPService ordenFabricacionEPService;

	@Resource(name = "ordenFabricacionService")
	private OrdenFabricacionService ordenFabricacionService;

	public OrdenCompraDetallePedidoEPServiceImpl() {
		super(OrdenCompraDetallePedido.class, OrdenCompraDetallePedidoDTO.class);
	}

	@Override
	@Resource(name = "ordenCompraDetallePedidoService")
	protected void setService(OrdenCompraDetallePedidoService service) {
		this.service = service;
	}

	@Override
	public void modificarCantidad(Long idPedido, OperacionOrdenCompraDetallePedidoDTO operacion) {
		if (operacion == null)
			throw new ar.com.avaco.fwk.core.exception.ErrorValidationException("Debe indicar la nueva cantidad y el motivo", null);
		ordenFabricacionEPService.modificarCantidadPedido(idPedido, operacion.getCantidad(), operacion.getMotivo());
	}

	@Override
	public void cancelar(Long idPedido, String motivo) {
		ordenFabricacionEPService.cancelarPedido(idPedido, motivo);
	}

	@Override
	public OrdenCompraDetallePedidoDTO update(OrdenCompraDetallePedidoDTO dto) throws BusinessException {
		OrdenCompraDetallePedido pedido = this.service.get(dto.getId());
		Long idOrdenCompra = pedido.getOrdenCompraDetalle().getOrdenCompra().getId();
		if (ordenFabricacionService.existenParaOrdenCompra(idOrdenCompra))
			throw new ErrorValidationException("Con OF generadas, use la acción específica de cambio de cantidad o cancelación", null);
		if (dto.getCantidad() == null || dto.getCantidad() < 0)
			throw new ErrorValidationException("La cantidad solicitada no puede ser negativa", null);
		return super.update(dto);
	}

	@Override
	public void remove(Long idPedido) {
		OrdenCompraDetallePedido pedido = this.service.get(idPedido);
		Long idOrdenCompra = pedido.getOrdenCompraDetalle().getOrdenCompra().getId();
		if (ordenFabricacionService.existenParaOrdenCompra(idOrdenCompra))
			throw new ErrorValidationException("Con OF generadas, cancele el pedido mediante la acción específica", null);
		super.remove(idPedido);
	}

}
