package ar.com.avaco.nitrophyl.repository.reporte;

import ar.com.avaco.fwk.core.component.repository.NJRepository;
import ar.com.avaco.nitrophyl.domain.entities.reporte.ReporteLoteConfiguracionCliente;

public interface ReporteLoteConfiguracionClienteRepository
		extends NJRepository<Long, ReporteLoteConfiguracionCliente>, ReporteLoteConfiguracionClienteRepositoryCustom {

	boolean existsByFormulaId(Long idFormula);

}
