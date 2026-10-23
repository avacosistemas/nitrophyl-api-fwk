package ar.com.avaco.nitrophyl.filter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import ar.com.avaco.fwk.core.domain.filter.AbstractFilter;
import ar.com.avaco.fwk.core.domain.filter.FilterData;
import ar.com.avaco.fwk.core.domain.filter.FilterDataType;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionEntregaFilterDTO;

public class OrdenFabricacionEntregaFilter extends AbstractFilter {

	private Long idCliente;

	private Long idPieza;

	private Long idOrdenFabricacion;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate fechaDesde;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate fechaHasta;

	public OrdenFabricacionEntregaFilter() {
	}

	public OrdenFabricacionEntregaFilter(OrdenFabricacionEntregaFilterDTO filter) {
		super(filter);
		this.idCliente = filter.getIdCliente();
		this.idPieza = filter.getIdPieza();
		this.fechaDesde = filter.getFechaDesde();
		this.fechaHasta = filter.getFechaHasta();
		this.idOrdenFabricacion = filter.getIdOrdenFabricacion();
	}

	@Override
	public List<FilterData> getFilterDatas() {
		List<FilterData> filterDatas = new ArrayList<FilterData>();

		if (idCliente != null) {
			String alias = "ordenFabricacion.ordenCompraDetallePedido.ordenCompraDetalle.ordenCompra.cliente.id";
			filterDatas.add(new FilterData(alias, idCliente, FilterDataType.EQUALS));
		}

		if (idPieza != null) {
			String alias = "ordenFabricacion.ordenCompraDetallePedido.ordenCompraDetalle.pieza.id";
			filterDatas.add(new FilterData(alias, idPieza, FilterDataType.EQUALS));
		}

		if (fechaDesde != null) {
			filterDatas.add(new FilterData("fecha", fechaDesde, FilterDataType.EQUALS_MORE_THAN));
		}

		if (fechaHasta != null) {
			filterDatas.add(new FilterData("fecha", fechaHasta, FilterDataType.EQUALS_LESS_THAN));
		}
		
		if (idOrdenFabricacion != null) {
			filterDatas.add(new FilterData("ordenFabricacion.id", idOrdenFabricacion, FilterDataType.EQUALS));
		}

		return filterDatas;

	}
	
	@Override
	public String getIdx() {
		String idx = super.getIdx(); 
		if (idx != null) {
			switch (idx) {
			case "cliente":
				idx = "ordenFabricacion.ordenCompraDetallePedido.ordenCompraDetalle.ordenCompra.cliente.nombre";
				break;
			case "numeroOT":
				idx = "ordenFabricacion.numero,ordenFabricacion.anio";
				break;
			case "pieza":
				idx = "ordenFabricacion.ordenCompraDetallePedido.ordenCompraDetalle.pieza.codigo";
				break;
			case "lotes":
				idx = null;
				break;
			case "operario":
				idx = "operario.nombre,operario.apellido";
				break;
			default:
				break;
			}
		}
		return idx;
	}

}
