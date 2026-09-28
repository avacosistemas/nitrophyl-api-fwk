package ar.com.avaco.nitrophyl.filter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import ar.com.avaco.fwk.core.domain.filter.AbstractFilter;
import ar.com.avaco.fwk.core.domain.filter.FilterData;
import ar.com.avaco.fwk.core.domain.filter.FilterDataType;
import ar.com.avaco.nitrophyl.dto.MaquinaFilterDTO;

public class MaquinaFilter extends AbstractFilter {

	private String nombre;

	@Override
	public List<FilterData> getFilterDatas() {
		List<FilterData> list = new ArrayList<FilterData>();
		if (StringUtils.isNotBlank(nombre)) {
			list.add(new FilterData("nombre", nombre, FilterDataType.LIKE));
		}
		return list;
	}

	public MaquinaFilter() {
	}

	public MaquinaFilter(MaquinaFilterDTO ffdto) {
		super(ffdto.getPageSize(), ffdto.getPage(), ffdto.getAsc(), ffdto.getIdx());
		this.nombre = ffdto.getNombre();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
