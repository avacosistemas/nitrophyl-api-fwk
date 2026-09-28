package ar.com.avaco.nitrophyl.epservice;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenCompraDetallePedido;
import ar.com.avaco.nitrophyl.dto.OrdenCompraDetallePedidoDTO;
import ar.com.avaco.nitrophyl.service.fabricacion.OrdenCompraDetallePedidoService;
import ar.com.avaco.fwk.core.component.epservice.CRUDAuditableEPBaseService;

@Service("ordenCompraDetallePedidoEPService")
public class OrdenCompraDetallePedidoEPServiceImpl extends CRUDAuditableEPBaseService<Long, OrdenCompraDetallePedidoDTO, OrdenCompraDetallePedido, OrdenCompraDetallePedidoService>
		implements OrdenCompraDetallePedidoEPService {

	public OrdenCompraDetallePedidoEPServiceImpl() {
		super(OrdenCompraDetallePedido.class, OrdenCompraDetallePedidoDTO.class);
	}

	@Override
	@Resource(name = "ordenCompraDetallePedidoService")
	protected void setService(OrdenCompraDetallePedidoService service) {
		this.service = service;
	}

}