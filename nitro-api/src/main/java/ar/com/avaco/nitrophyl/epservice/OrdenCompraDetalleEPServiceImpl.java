package ar.com.avaco.nitrophyl.epservice;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ar.com.avaco.nitrophyl.domain.entities.fabricacion.OrdenCompraDetalle;
import ar.com.avaco.nitrophyl.dto.OrdenCompraDetalleDTO;
import ar.com.avaco.nitrophyl.service.fabricacion.OrdenCompraDetalleService;
import ar.com.avaco.nitrophyl.service.fabricacion.OrdenFabricacionService;
import ar.com.avaco.fwk.core.exception.BusinessException;
import ar.com.avaco.fwk.core.exception.ErrorValidationException;
import ar.com.avaco.fwk.core.component.epservice.CRUDAuditableEPBaseService;

@Service("ordenCompraDetalleEPService")
public class OrdenCompraDetalleEPServiceImpl extends CRUDAuditableEPBaseService<Long, OrdenCompraDetalleDTO, OrdenCompraDetalle, OrdenCompraDetalleService>
		implements OrdenCompraDetalleEPService {

	@Resource(name = "ordenFabricacionEPService")
	private OrdenFabricacionEPService ordenFabricacionEPService;

	@Resource(name = "ordenFabricacionService")
	private OrdenFabricacionService ordenFabricacionService;

	public OrdenCompraDetalleEPServiceImpl() {
		super(OrdenCompraDetalle.class, OrdenCompraDetalleDTO.class);
	}

	@Override
	@Resource(name = "ordenCompraDetalleService")
	protected void setService(OrdenCompraDetalleService service) {
		this.service = service;
	}

	@Override
	public void cancelarPieza(Long idDetalle, String motivo) {
		ordenFabricacionEPService.cancelarPieza(idDetalle, motivo);
	}

	@Override
	public OrdenCompraDetalleDTO update(OrdenCompraDetalleDTO dto) throws BusinessException {
		OrdenCompraDetalle detalle = this.service.get(dto.getId());
		if (ordenFabricacionService.existenParaOrdenCompra(detalle.getOrdenCompra().getId()))
			throw new ErrorValidationException("No se puede editar el detalle de una OC con OF generadas; use las acciones específicas", null);
		return super.update(dto);
	}

	@Override
	public void remove(Long idDetalle) {
		OrdenCompraDetalle detalle = this.service.get(idDetalle);
		if (ordenFabricacionService.existenParaOrdenCompra(detalle.getOrdenCompra().getId()))
			throw new ErrorValidationException("Con OF generadas, cancele la pieza mediante la acción específica", null);
		super.remove(idDetalle);
	}

}
