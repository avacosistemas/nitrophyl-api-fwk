package ar.com.avaco.nitrophyl.epservice;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ar.com.avaco.fwk.core.component.epservice.CRUDAuditableEPBaseService;
import ar.com.avaco.nitrophyl.domain.entities.lote.EnsayoResultado;
import ar.com.avaco.nitrophyl.dto.EnsayoResultadoDTO;
import ar.com.avaco.nitrophyl.service.lote.EnsayoResultadoService;

@Service("ensayoResultadoEPService")
public class EnsayoResultadoEPServiceImpl
		extends CRUDAuditableEPBaseService<Long, EnsayoResultadoDTO, EnsayoResultado, EnsayoResultadoService>
		implements EnsayoResultadoEPService {

	public EnsayoResultadoEPServiceImpl() {
		super(EnsayoResultado.class, EnsayoResultadoDTO.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Resource(name = "ensayoResultadoService")
	protected void setService(EnsayoResultadoService service) {
		this.service = service;
	}

	@Override
	protected EnsayoResultadoDTO convertToDto(EnsayoResultado entity) {
		EnsayoResultadoDTO erdto = new EnsayoResultadoDTO();
		erdto.setId(entity.getId());
		erdto.setIdEnsayo(entity.getEnsayo().getId());
		erdto.setIdConfiguracionPruebaParametro(entity.getConfiguracionPruebaParametro().getId());
		erdto.setMaximo(entity.getConfiguracionPruebaParametro().getMaximo());
		erdto.setMinimo(entity.getConfiguracionPruebaParametro().getMinimo());
		erdto.setNombre(entity.getConfiguracionPruebaParametro().getMaquinaPrueba().getNombre());
		erdto.setNorma(entity.getConfiguracionPruebaParametro().getNorma());
		erdto.setRedondeo(entity.getRedondeo());
		erdto.setResultado(entity.getResultado());
		return erdto;
	}

	@Override
	public List<EnsayoResultadoDTO> listByEnsayo(Long idEnsayo) {
		List<EnsayoResultado> resultados = this.service.listByEnsayo(idEnsayo);
		List<EnsayoResultadoDTO> dtos = new ArrayList<EnsayoResultadoDTO>();
		resultados.forEach(x -> dtos.add(convertToDto(x)));
		return dtos;
	}

}
