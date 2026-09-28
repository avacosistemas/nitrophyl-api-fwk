package ar.com.avaco.nitrophyl.service.molde;

import java.util.List;

import ar.com.avaco.fwk.core.component.service.NJService;
import ar.com.avaco.nitrophyl.domain.entities.molde.Molde;
import ar.com.avaco.nitrophyl.dto.MoldeFilterDTO;
import ar.com.avaco.nitrophyl.dto.MoldeListadoDTO;

public interface MoldeService extends NJService<Long, Molde> {

	List<MoldeListadoDTO> list(MoldeFilterDTO filter);

	void incrementarBocas(Long idMolde);

	void disminuirBocas(Long idMolde);

	Integer getCantidadBocas(Long idMolde);

	void actualizarFaltantes(Long idMolde);

}
