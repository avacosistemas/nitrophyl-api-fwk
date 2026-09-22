package ar.com.avaco.nitrophyl.ws.service;

import java.util.List;

import ar.com.avaco.nitrophyl.ws.dto.LoteGraficoDTO;
import ar.com.avaco.nitrophyl.ws.dto.LoteGraficoSinArchivoDTO;
import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;

public interface LoteGraficoEPService extends CRUDEPService<Long, LoteGraficoDTO> {

	List<LoteGraficoSinArchivoDTO> listByIdLote(Long idLote);

}
