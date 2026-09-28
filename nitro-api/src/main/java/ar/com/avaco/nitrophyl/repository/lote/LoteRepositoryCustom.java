package ar.com.avaco.nitrophyl.repository.lote;

import java.util.List;

import ar.com.avaco.nitrophyl.dto.RegistroEnsayoLotePorMaquinaDTO;
import ar.com.avaco.nitrophyl.dto.ReporteEnsayoLotePorMaquinaFilterDTO;

public interface LoteRepositoryCustom {

	List<RegistroEnsayoLotePorMaquinaDTO> getEnsayosLotePorMaquina(ReporteEnsayoLotePorMaquinaFilterDTO filtro);

}
