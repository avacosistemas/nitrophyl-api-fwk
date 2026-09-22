package ar.com.avaco.nitrophyl.service.molde;

import java.util.List;

import ar.com.avaco.fwk.core.component.service.NJService;
import ar.com.avaco.fwk.core.exception.BusinessException;
import ar.com.avaco.fwk.core.exception.ErrorValidationException;
import ar.com.avaco.nitrophyl.domain.entities.molde.MoldePlano;

public interface MoldePlanoService extends NJService<Long, MoldePlano> {

	List<MoldePlano> listByMoldeId(Long idMolde);

	MoldePlano getUltimoRegistro(Long idMolde);

	MoldePlano addMoldePlano(MoldePlano moldePlano) throws ErrorValidationException, BusinessException;


}
