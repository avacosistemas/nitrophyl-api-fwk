package ar.com.avaco.nitrophyl.epservice;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.avaco.nitrophyl.domain.entities.lote.Lote;
import ar.com.avaco.nitrophyl.domain.entities.maquina.Maquina;
import ar.com.avaco.nitrophyl.domain.entities.molde.LoteGrafico;
import ar.com.avaco.nitrophyl.dto.LoteGraficoDTO;
import ar.com.avaco.nitrophyl.dto.LoteGraficoSinArchivoDTO;
import ar.com.avaco.nitrophyl.service.lote.LoteGraficoService;
import ar.com.avaco.nitrophyl.service.lote.LoteService;
import ar.com.avaco.nitrophyl.service.maquina.MaquinaService;
import ar.com.avaco.fwk.core.utils.DateUtils;
import ar.com.avaco.fwk.core.component.epservice.CRUDEPBaseService;

@Service("loteGraficoEPService")
public class LoteGraficoEPServiceImpl extends CRUDEPBaseService<Long, LoteGraficoDTO, LoteGrafico, LoteGraficoService>
		implements LoteGraficoEPService {

	public LoteGraficoEPServiceImpl() {
		super(LoteGrafico.class, LoteGraficoDTO.class);
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private MaquinaService maquinaService;

	@Autowired
	private LoteService loteService;
	
	@Override
	protected LoteGrafico convertToEntity(LoteGraficoDTO dto) {
		LoteGrafico lg = new LoteGrafico();

		Lote lote = loteService.get(dto.getIdLote());
		lg.setLote(lote);
		Maquina maquina = maquinaService.get(dto.getIdMaquina());
		lg.setMaquina(maquina);
		lg.setArchivo(dto.getArchivo());
		lg.setFecha(DateUtils.getFechaYHoraActual());
		lg.setIdLote(dto.getIdLote());
		return lg;
	}

	@Override
	protected LoteGraficoDTO convertToDto(LoteGrafico entity) {
		LoteGraficoDTO lgdto = new LoteGraficoDTO();
		lgdto.setArchivo(entity.getArchivo());
		lgdto.setId(entity.getId());
		lgdto.setIdLote(entity.getIdLote());
		lgdto.setIdMaquina(entity.getMaquina().getId());
		lgdto.setMaquina(entity.getMaquina().getNombre());
		lgdto.setLote(entity.getLote().getNroLote());
		return lgdto;
	}

	@Override
	@Resource(name = "loteGraficoService")
	protected void setService(LoteGraficoService service) {
		this.service = service;
	}

	@Override
	public List<LoteGraficoSinArchivoDTO> listByIdLote(Long idLote) {
		return this.service.listGraficosByLote(idLote);
	}

}
