package ar.com.avaco.nitrophyl.service.lote;

import java.util.List;

import ar.com.avaco.fwk.core.component.service.NJService;
import ar.com.avaco.nitrophyl.domain.entities.molde.LoteGrafico;
import ar.com.avaco.nitrophyl.dto.ArchivoDTO;
import ar.com.avaco.nitrophyl.dto.LoteGraficoSinArchivoDTO;

public interface LoteGraficoService extends NJService<Long, LoteGrafico> {

	ArchivoDTO getGraficoByIdLote(Long idLote);

	List<Long> filterIdsConGrafico(List<Long> loteIds);

	List<LoteGraficoSinArchivoDTO> listGraficosByLote(Long idLote);

}
