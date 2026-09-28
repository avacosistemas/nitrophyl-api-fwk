
package ar.com.avaco.nitrophyl.epservice;

import java.util.List;

import ar.com.avaco.fwk.core.exception.BusinessException;
import ar.com.avaco.fwk.core.exception.ErrorValidationException;
import ar.com.avaco.nitrophyl.dto.MoldeClienteDTO;
import ar.com.avaco.nitrophyl.dto.MoldeDTO;
import ar.com.avaco.nitrophyl.dto.MoldeDimensionListadoDTO;
import ar.com.avaco.nitrophyl.dto.MoldeFilterDTO;
import ar.com.avaco.nitrophyl.dto.MoldeFotoDTO;
import ar.com.avaco.nitrophyl.dto.MoldeFotoListadoDTO;
import ar.com.avaco.nitrophyl.dto.MoldeListadoDTO;
import ar.com.avaco.nitrophyl.dto.MoldeObservacionDTO;
import ar.com.avaco.nitrophyl.dto.MoldePlanoDTO;
import ar.com.avaco.nitrophyl.dto.MoldePlanoListadoDTO;
import ar.com.avaco.nitrophyl.dto.MoldeRegistroDTO;
import ar.com.avaco.fwk.core.component.dto.PageDTO;
import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;

public interface MoldeEPService extends CRUDEPService<Long, MoldeDTO> {

	List<MoldeDimensionListadoDTO> getMoldesDimension(Long idMolde);

	List<MoldeDimensionListadoDTO> updateMoldeDimensiones(Long idMolde,
			List<MoldeDimensionListadoDTO> moldeDimensionListadoDTOs);

	List<MoldeRegistroDTO> getMoldesRegistro(Long idMolde);

	MoldeRegistroDTO saveMoldeRegistro(MoldeRegistroDTO moldeRegistroDTO);

	List<MoldePlanoListadoDTO> getMoldesPlano(Long idMolde);

	MoldePlanoDTO addMoldePlano(MoldePlanoDTO moldePlanoDTO) throws ErrorValidationException, BusinessException;

	List<MoldeFotoListadoDTO> getMoldesFoto(Long idMolde);

	MoldeFotoDTO addMoldeFoto(MoldeFotoDTO moldeFotoDTO) throws ErrorValidationException, BusinessException;

	MoldePlanoDTO downloadMoldePlano(Long idMoldePlano);

	MoldeFotoDTO downloadMoldeFoto(Long idMoldeFoto);

	List<MoldeClienteDTO> getMoldeClientes(Long idMolde);

	List<MoldeClienteDTO> updateMoldeClientes(Long idMolde, List<MoldeClienteDTO> moldeClientesListadoDTOs);

	List<MoldeObservacionDTO> getMoldeObservaciones(Long idMolde);

	MoldeObservacionDTO addMoldeObservacion(MoldeObservacionDTO dto);

	PageDTO<MoldeListadoDTO> list(MoldeFilterDTO filter);

}
