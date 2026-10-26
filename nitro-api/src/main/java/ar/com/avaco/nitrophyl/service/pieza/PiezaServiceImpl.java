package ar.com.avaco.nitrophyl.service.pieza;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import ar.com.avaco.fwk.core.component.service.NJBaseService;
import ar.com.avaco.nitrophyl.domain.entities.pieza.Pieza;
import ar.com.avaco.nitrophyl.domain.entities.pieza.Proceso;
import ar.com.avaco.nitrophyl.domain.entities.pieza.Terminacion;
import ar.com.avaco.nitrophyl.domain.entities.pieza.moldeo.Vulcanizacion;
import ar.com.avaco.nitrophyl.dto.PiezaComboDTO;
import ar.com.avaco.nitrophyl.dto.PiezaFilterDTO;
import ar.com.avaco.nitrophyl.dto.PiezaGrillaDTO;
import ar.com.avaco.nitrophyl.repository.pieza.PiezaRepository;

//@Transactional
@Service("piezaService")
public class PiezaServiceImpl extends NJBaseService<Long, Pieza, PiezaRepository> implements PiezaService {

	@Resource(name = "piezaRepository")
	void setClienteRepository(PiezaRepository piezaRepository) {
		this.repository = piezaRepository;
	}

	@Override
	public Pieza save(Pieza entity) {
		Pieza save = super.save(entity);
		actualizarFaltantes(save);
		return save;
	}

	@Override
	public Pieza update(Pieza entity) {
		Pieza update = super.update(entity);
		actualizarFaltantes(entity);
		return update;
	}
	
	@Override
	public void actualizarFaltantes(Pieza pieza) {
		StringBuilder sb = new StringBuilder();

		Proceso proceso = pieza.getProceso();
		if (proceso.getPrensas() == null || proceso.getPrensas().isEmpty()) {
			sb.append("Falta cargar una prensa,");
		}

		Vulcanizacion vulcanizacion = proceso.getVulcanizacion();
		if (vulcanizacion == null || vulcanizacion.getTemperaturaMax() == null
				|| vulcanizacion.getTemperaturaMin() == null || vulcanizacion.getTiempo() == null) {
			sb.append("Falta cargar la vulcanización,");
		}

		if (proceso.getBombeos() == null || proceso.getBombeos().isEmpty()) {
			sb.append("Falta cargar al menos un bombeo,");
		}

		if (StringUtils.isBlank(proceso.getDesmoldante())) {
			sb.append("Falta cargar el desmoldante,");
		}

		if (StringUtils.isBlank(proceso.getPostCura())) {
			sb.append("Falta cargar la postcura,");
		}

		if (proceso.getEsquema() == null || proceso.getEsquema().isEmpty()) {
			sb.append("Falta cargar al menos un esquema,");
		}

		if (pieza.getDimensiones() == null || pieza.getDimensiones().isEmpty()) {
			sb.append("Faltan cargar las dimensiones,");
		}

		Terminacion terminacion = proceso.getTerminacion();
		if (terminacion == null || StringUtils.isBlank(terminacion.getEmbalaje())
				|| StringUtils.isBlank(terminacion.getIdentificacion())
				|| StringUtils.isBlank(terminacion.getRefilado()) || terminacion.getImagenTerminada() == null) {
			sb.append("Falta completar la terminacion,");
		}
		
		Integer cantinsumos = pieza.getCantidadInsumos();
		if (pieza.getRequiereInsumos() != null && pieza.getRequiereInsumos().booleanValue() && (cantinsumos == null || cantinsumos == 0 || (!cantinsumos.equals(pieza.getInsumos().size())))) {
			sb.append("Faltan cargar insumos,");
		}

		if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ',') {
		    sb.setLength(sb.length() - 1);
		}
		
		this.repository.actualizarFaltantes(pieza.getId(), sb.toString());
	}

	@Override
	public List<PiezaGrillaDTO> listGrilla(PiezaFilterDTO pfdto) {
		return this.repository.listGrilla(pfdto);
	}

	@Override
	public Pieza getVigenteByCodigoInternoFormula(String codigoInterno, Long idFormula) {
		return this.repository.findByCodigoAndVigenteAndDetalleFormulaFormulaId(codigoInterno, true, idFormula);
	}

	@Override
	public boolean existsByFormula(Long idFormula) {
		return this.repository.existsByDetalleFormulaFormulaId(idFormula);
	}

	@Override
	public List<PiezaComboDTO> listCombo(String nombre, Long idCliente, boolean soloDelCliente) {
		List<PiezaComboDTO> piezas = this.repository.listPiezasCombo(nombre, idCliente, soloDelCliente);
		Map<List<String>, PiezaComboDTO> piezasUnicas = new LinkedHashMap<>();
		for (PiezaComboDTO pieza : piezas) {
			List<String> clave = Arrays.asList(
					StringUtils.trimToEmpty(pieza.getCodigo()),
					StringUtils.trimToEmpty(pieza.getDenominacion()).toLowerCase(Locale.ROOT),
					StringUtils.trimToEmpty(pieza.getCodigoCliente()));
			piezasUnicas.putIfAbsent(clave, pieza);
		}
		return new ArrayList<>(piezasUnicas.values());
	}

	@Override
	public boolean existsByCodigoAndDetalleFormulaFormulaId(String codigo, Long idMaterial) {
		return this.repository.existsByCodigoAndDetalleFormulaFormulaId(codigo, idMaterial) ;
	}

}
