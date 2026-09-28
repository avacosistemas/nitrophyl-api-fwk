package ar.com.avaco.nitrophyl.service.fabricacion;

import java.util.List;

import ar.com.avaco.nitrophyl.dto.DetalleMaquinaOrdenTrabajoDTO;
import ar.com.avaco.nitrophyl.dto.ResumenMaquinaOrdenTrabajoDTO;

public interface MonitorFabricaService {

	List<ResumenMaquinaOrdenTrabajoDTO> obtenerResumen();

	List<DetalleMaquinaOrdenTrabajoDTO> obtenerOrdenesTrabajo(Long idSector, Long idMaquina);

}