package ar.com.avaco.nitrophyl.epservice;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ar.com.avaco.nitrophyl.domain.entities.pieza.insumo.MateriaPrima;
import ar.com.avaco.nitrophyl.domain.entities.pieza.insumo.MateriaPrimaStockHistorial;
import ar.com.avaco.nitrophyl.dto.MateriaPrimaStockHistorialDTO;
import ar.com.avaco.nitrophyl.service.pieza.MateriaPrimaStockHistorialService;
import ar.com.avaco.fwk.core.utils.DateUtils;
import ar.com.avaco.fwk.core.component.epservice.CRUDAuditableEPBaseService;

@Service("materiaPrimaStockHistorialEPService")
public class MateriaPrimaStockHistorialEPServiceImpl extends CRUDAuditableEPBaseService<Long, MateriaPrimaStockHistorialDTO, MateriaPrimaStockHistorial, MateriaPrimaStockHistorialService>
		implements MateriaPrimaStockHistorialEPService {

	public MateriaPrimaStockHistorialEPServiceImpl() {
		super(MateriaPrimaStockHistorial.class, MateriaPrimaStockHistorialDTO.class);
	}

	@Override
	protected MateriaPrimaStockHistorial convertToEntityForSave(MateriaPrimaStockHistorialDTO dto) {
		MateriaPrimaStockHistorial entity = super.convertToEntityForSave(dto);
		entity.setFecha(DateUtils.getFechaYHoraActual());
		entity.setMateriaPrima(MateriaPrima.ofId(dto.getIdMateriaPrima()));
		return entity;
	}
	
	@Override
	@Resource(name = "materiaPrimaStockHistorialService")
	protected void setService(MateriaPrimaStockHistorialService service) {
		this.service = service;
	}

}