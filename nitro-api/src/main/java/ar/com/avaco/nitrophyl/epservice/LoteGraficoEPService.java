package ar.com.avaco.nitrophyl.epservice;

import java.util.List;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.LoteGraficoDTO;
import ar.com.avaco.nitrophyl.dto.LoteGraficoSinArchivoDTO;

public interface LoteGraficoEPService extends CRUDEPService<Long, LoteGraficoDTO> {

	List<LoteGraficoSinArchivoDTO> listByIdLote(Long idLote);

}
