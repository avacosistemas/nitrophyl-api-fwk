
package ar.com.avaco.nitrophyl.epservice;

import java.util.List;

import ar.com.avaco.fwk.core.exception.BusinessException;
import ar.com.avaco.nitrophyl.dto.PiezaBaseDTO;
import ar.com.avaco.nitrophyl.dto.PiezaComboDTO;
import ar.com.avaco.nitrophyl.dto.PiezaCreacionDTO;
import ar.com.avaco.nitrophyl.dto.PiezaDTO;
import ar.com.avaco.nitrophyl.dto.PiezaEdicionDTO;
import ar.com.avaco.nitrophyl.dto.PiezaFilterDTO;
import ar.com.avaco.nitrophyl.dto.PiezaGrillaDTO;
import ar.com.avaco.nitrophyl.dto.PiezaPUTDTO;
import ar.com.avaco.fwk.core.component.dto.PageDTO;
import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;

public interface PiezaEPService extends CRUDEPService<Long, PiezaDTO> {

	PiezaCreacionDTO create(PiezaCreacionDTO dto);

	void marcarVigente(Long piezaId);

	void nuevaRevision(Long piezaId);

	PageDTO<PiezaGrillaDTO> listGrilla(PiezaFilterDTO filter);

	PiezaEdicionDTO getByIdEdicion(Long idPieza);

	void update(Long idPieza, PiezaPUTDTO piezaFormula);

	List<PiezaComboDTO> listCombo(String nombre, Long idCliente);

	void copiar(PiezaBaseDTO dto) throws BusinessException;


}
