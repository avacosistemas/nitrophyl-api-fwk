package ar.com.avaco.nitrophyl.epservice;

import java.util.List;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.OrdenCompraDTO;
import ar.com.avaco.nitrophyl.dto.OrdenCompraHistorialDTO;

public interface OrdenCompraEPService extends CRUDEPService<Long, OrdenCompraDTO> {

	void cancelar(Long id, String motivo);

	void generarOrdenFabrica(Long idOC);

	List<OrdenCompraHistorialDTO> listarHistorial(Long idOC);

}
