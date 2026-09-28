package ar.com.avaco.nitrophyl.epservice;

import java.util.List;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.nitrophyl.dto.ConfiguracionPruebaDTO;

public interface ConfiguracionPruebaEPService extends CRUDEPService<Long, ConfiguracionPruebaDTO> {

	List<ConfiguracionPruebaDTO> list(Long idFormula);

	List<ConfiguracionPruebaDTO> listVigentesByLote(Long idLote);

	void setarVigente(Long idConfiguracionPrueba);

}
