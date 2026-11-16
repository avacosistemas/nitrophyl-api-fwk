package ar.com.avaco.nitrophyl.epservice;

import java.util.List;
import java.util.Map;

import ar.com.avaco.fwk.core.component.dto.PageDTO;
import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.ListadoOrdenFabricacionDTO;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionAsignacionDTO;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionDTO;
import ar.com.avaco.nitrophyl.dto.OrdenFabricacionFilterDTO;
import ar.com.avaco.nitrophyl.dto.OrdenTrabajoResponseDTO;
import ar.com.avaco.nitrophyl.dto.OrdenTrabajoResumenDTO;
import ar.com.avaco.nitrophyl.dto.AsignacionStockOrdenFabricacionDTO;
import ar.com.avaco.nitrophyl.dto.PiezaStockLoteDisponibleDTO;
import ar.com.avaco.nitrophyl.dto.PiezaLoteFabricacionDTO;

public interface OrdenFabricacionEPService extends CRUDEPService<Long, OrdenFabricacionDTO> {

	PageDTO<ListadoOrdenFabricacionDTO> listFilterCount(OrdenFabricacionFilterDTO ordenFabricacionFilterDTO);

	void create(Long idOrdenCompra);

	void asignar(Long idOrdenFabricacion, OrdenFabricacionAsignacionDTO asignacion);

	void asignarStock(Long idOrdenFabricacion, AsignacionStockOrdenFabricacionDTO asignacion);

	List<PiezaStockLoteDisponibleDTO> listarStockDisponible(Long idPieza);

	List<PiezaLoteFabricacionDTO> listarPiezasProducidasAsociadasAlLote(Long idLote);

	OrdenTrabajoResponseDTO generarOrdenTrabajo(Long idOrdenFabricacion);

	Map<String, List<OrdenTrabajoResumenDTO>> generarResumen(List<Long> ids);

	void reordenar(Long idOrdenFabricacion, Integer nuevaPosicion);

	void facturar(Long idOrdenFabricacion);

	void cancelarPedido(Long idOrdenCompraDetallePedido, String motivo);

	void cancelarPieza(Long idOrdenCompraDetalle, String motivo);

	void cancelarOrdenCompra(Long idOrdenCompra, String motivo);

	void modificarCantidadPedido(Long idOrdenCompraDetallePedido, Integer cantidadNueva, String motivo);


}
