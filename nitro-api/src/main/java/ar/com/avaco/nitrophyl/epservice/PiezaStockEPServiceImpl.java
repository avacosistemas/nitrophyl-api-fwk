package ar.com.avaco.nitrophyl.epservice;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStockLote;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStock;
import ar.com.avaco.nitrophyl.dto.PiezaStockDTO;
import ar.com.avaco.nitrophyl.dto.LoteStockDTO;
import ar.com.avaco.nitrophyl.dto.PiezaStockResumenDTO;
import ar.com.avaco.nitrophyl.service.pieza.PiezaStockService;
import ar.com.avaco.nitrophyl.service.pieza.PiezaService;
import org.springframework.beans.factory.annotation.Autowired;
import ar.com.avaco.fwk.core.component.epservice.CRUDAuditableEPBaseService;

//@Transactional
@Service("piezaStockEPService")
public class PiezaStockEPServiceImpl extends
		CRUDAuditableEPBaseService<Long, PiezaStockDTO, PiezaStockLote, PiezaStockService> implements PiezaStockEPService {

	@Autowired
	private PiezaService piezaService;

	public PiezaStockEPServiceImpl() {
		super(PiezaStockLote.class, PiezaStockDTO.class);
	}

	@Override
	protected PiezaStockDTO convertToDto(PiezaStockLote entity) {
		PiezaStockDTO dto = super.convertToDto(entity);
		dto.setStockDisponible(entity.getStockDisponible());
		dto.setFirmaLotes(entity.getFirmaLotes());
		dto.setLotes(new java.util.ArrayList<LoteStockDTO>());
		return dto;
	}

	@Override
	public PiezaStockResumenDTO obtenerResumen(Long idPieza) {
		var pieza = piezaService.get(idPieza);
		java.util.List<PiezaStockLote> grupos = service.listarPorPieza(idPieza);
		PiezaStockResumenDTO resumen = new PiezaStockResumenDTO();
		resumen.setId(idPieza);
		resumen.setIdPieza(idPieza);
		resumen.setCodigo(pieza.getCodigo());
		resumen.setDenominacion(pieza.getDenominacion());
		PiezaStock cabecera = service.obtenerCabecera(idPieza);
		resumen.setStockFisico(cabecera == null ? 0 : cabecera.getStockFisico());
		resumen.setStockReservado(cabecera == null ? 0 : cabecera.getStockReservado());
		resumen.setStockDisponible(resumen.getStockFisico() - resumen.getStockReservado());
		resumen.setGrupos(grupos.stream().map(g -> {
			PiezaStockDTO dto = new PiezaStockDTO();
			dto.setId(g.getId());
			dto.setIdPieza(idPieza);
			dto.setCodigo(pieza.getCodigo());
			dto.setDenominacion(pieza.getDenominacion());
			dto.setStockFisico(g.getStockFisico());
			dto.setStockReservado(g.getStockReservado());
			dto.setStockDisponible(g.getStockDisponible());
			dto.setFirmaLotes(g.getFirmaLotes());
			dto.setLotes(new java.util.ArrayList<LoteStockDTO>());
			return dto;
		}).collect(java.util.stream.Collectors.toList()));
		return resumen;
	}

	@Override
	public java.util.List<PiezaStockDTO> listarPorLote(String codigoLote) {
		return service.listarPorLote(codigoLote).stream().map(this::convertToDto).collect(java.util.stream.Collectors.toList());
	}

	@Override
	@Resource(name = "piezaStockService")
	protected void setService(PiezaStockService service) {
		this.service = service;
	}

}
