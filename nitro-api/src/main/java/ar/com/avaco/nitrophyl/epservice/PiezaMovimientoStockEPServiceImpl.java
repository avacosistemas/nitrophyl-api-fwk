package ar.com.avaco.nitrophyl.epservice;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.com.avaco.nitrophyl.domain.entities.pieza.OrigenMovimientoStock;
import ar.com.avaco.nitrophyl.domain.entities.pieza.Pieza;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStockLoteMovimiento;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStockLote;
import ar.com.avaco.nitrophyl.domain.entities.lote.EstadoLote;
import ar.com.avaco.nitrophyl.domain.entities.lote.Lote;
import ar.com.avaco.nitrophyl.service.lote.LoteService;
import org.apache.commons.lang3.StringUtils;
import javax.transaction.Transactional;
import ar.com.avaco.nitrophyl.dto.PiezaMovimientoStockDTO;
import ar.com.avaco.nitrophyl.service.pieza.PiezaMovimientoStockService;
import ar.com.avaco.nitrophyl.service.pieza.PiezaStockService;
import ar.com.avaco.nitrophyl.service.pieza.PiezaService;
import ar.com.avaco.fwk.core.component.epservice.CRUDAuditableEPBaseService;
import ar.com.avaco.fwk.core.exception.ErrorValidationException;

//@Transactional
@Service("piezaMovimientoStockEPService")
public class PiezaMovimientoStockEPServiceImpl extends
		CRUDAuditableEPBaseService<Long, PiezaMovimientoStockDTO, PiezaStockLoteMovimiento, PiezaMovimientoStockService>
		implements PiezaMovimientoStockEPService {

	@Autowired
	private PiezaStockService piezaStockService;

	@Autowired
	private LoteService loteService;

	@Autowired
	private PiezaService piezaService;
	
	public PiezaMovimientoStockEPServiceImpl() {
		super(PiezaStockLoteMovimiento.class, PiezaMovimientoStockDTO.class);
	}

	@Override
	@Resource(name = "piezaMovimientoStockService")
	protected void setService(PiezaMovimientoStockService service) {
		this.service = service;
	}

	@Override
	public void registrarIngresoManual(Long idPieza, Integer cantidad, LocalDate fecha, String observacion) {
		PiezaMovimientoStockDTO dto = PiezaMovimientoStockDTO.builder().idPieza(idPieza).cantidad(cantidad)
				.fecha(fecha).observacion(observacion).origen(OrigenMovimientoStock.INGRESO_MANUAL).build();
		registrarIngresoManual(dto);
	}

	@Override
	@Transactional
	public void registrarIngresoManual(PiezaMovimientoStockDTO dto) {
		if (dto.getCantidad() == null || dto.getCantidad() <= 0 || dto.getIdPieza() == null
				|| dto.getFecha() == null || StringUtils.isBlank(dto.getObservacion()))
			throw new ErrorValidationException("Indique pieza, cantidad positiva, fecha y observación", null);
		OrigenMovimientoStock origen = dto.getOrigen() == OrigenMovimientoStock.DEVOLUCION_CLIENTE
				? OrigenMovimientoStock.DEVOLUCION_CLIENTE : OrigenMovimientoStock.INGRESO_MANUAL;
		Pieza pieza = piezaService.get(dto.getIdPieza());
		PiezaStockLoteMovimiento pms = PiezaStockLoteMovimiento.builder().fecha(dto.getFecha())
				.cantidad(dto.getCantidad()).observacion(dto.getObservacion().trim()).origen(origen).build();
		java.util.List<String> codigosLote = new java.util.ArrayList<String>();
		if (dto.getIdLotesOrigen() != null) {
			for (Long idLote : dto.getIdLotesOrigen()) {
				Lote lote = loteService.get(idLote);
				if ((lote.getEstado() != EstadoLote.APROBADO && lote.getEstado() != EstadoLote.APROBADO_OBSERVADO)
						|| !lote.getFormula().getId().equals(pieza.getDetalleFormula().getFormula().getId()))
					throw new ErrorValidationException("Todos los lotes deben estar aprobados y corresponder a la fórmula de la pieza", null);
				codigosLote.add(lote.getNroLote());
			}
		}
		String firmaLotes = codigosLote.stream().filter(StringUtils::isNotBlank).map(String::trim)
				.distinct().sorted().collect(java.util.stream.Collectors.joining(","));
		PiezaStockLote saldo = piezaStockService.ingresar(dto.getIdPieza(), firmaLotes, dto.getCantidad());
		pms.setPiezaStockLote(saldo);
		this.service.save(pms);
	}

}
