package ar.com.avaco.nitrophyl.epservice;

import ar.com.avaco.fwk.core.exception.BusinessException;
import ar.com.avaco.nitrophyl.dto.FormulaDTO;
import ar.com.avaco.nitrophyl.dto.RevisionParametrosDTO;
import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;

public interface FormulaEPService extends CRUDEPService<Long, FormulaDTO> {

	FormulaDTO clone(FormulaDTO dto) throws BusinessException;

	RevisionParametrosDTO marcarRevision(Long idFormula);

}
