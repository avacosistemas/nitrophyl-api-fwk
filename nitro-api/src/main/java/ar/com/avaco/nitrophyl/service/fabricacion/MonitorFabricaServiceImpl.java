package ar.com.avaco.nitrophyl.service.fabricacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.avaco.nitrophyl.dto.DetalleMaquinaOrdenTrabajoDTO;
import ar.com.avaco.nitrophyl.dto.ResumenMaquinaOrdenTrabajoDTO;
import ar.com.avaco.nitrophyl.repository.fabricacion.OrdenFabricacionRepository;

@Service("monitorFabricaService")
public class MonitorFabricaServiceImpl implements MonitorFabricaService {

	@Autowired
	private OrdenFabricacionRepository ordenFabricacionRepository;

	@Override
	public List<ResumenMaquinaOrdenTrabajoDTO> obtenerResumen() {
		return this.ordenFabricacionRepository.obtenerResumen();
	}
	
	@Override
	public List<DetalleMaquinaOrdenTrabajoDTO> obtenerOrdenesTrabajo(Long idSector, Long idMaquina) {
		return this.ordenFabricacionRepository.obtenerOrdenesTrabajo(idSector, idMaquina);
	}

	
	
}
