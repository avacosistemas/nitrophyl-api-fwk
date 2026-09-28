package ar.com.avaco.nitrophyl.epservice;

import java.util.List;

import ar.com.avaco.fwk.core.component.epservice.CRUDEPService;
import ar.com.avaco.fwk.core.exception.BusinessException;
import ar.com.avaco.fwk.core.exception.ErrorValidationException;
import ar.com.avaco.nitrophyl.dto.ClienteDTO;
import ar.com.avaco.nitrophyl.dto.ContactoDTO;

public interface ClienteEPService extends CRUDEPService<Long, ClienteDTO> {

	ClienteDTO getCliente(Long idCliente);
	
	List<ContactoDTO> getContactosByCliente(Long idCliente);

	ContactoDTO getContacto(Long idContactoCliente);
	
	ClienteDTO addCliente(ClienteDTO clienteDTO) throws ErrorValidationException, BusinessException;
	
	ClienteDTO updateCliente(ClienteDTO clienteDTO) throws ErrorValidationException, BusinessException;

	ContactoDTO addContactoCliente(Long idCliente, ContactoDTO contactoDTO) throws ErrorValidationException, BusinessException;

	ContactoDTO updateContactoCliente(ContactoDTO contactoDTO) throws ErrorValidationException, BusinessException;

	String getCorreoInformes(Long idCliente);

	void deleteContacto(Long idContactoCliente);

}
