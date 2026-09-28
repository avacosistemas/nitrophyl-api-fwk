package ar.com.avaco.nitrophyl.filter;

import java.util.ArrayList;
import java.util.List;

import ar.com.avaco.fwk.core.domain.filter.AbstractFilter;
import ar.com.avaco.fwk.core.domain.filter.FilterData;
import ar.com.avaco.nitrophyl.dto.MateriaPrimaFilterDTO;

public class MateriaPrimaFilter extends AbstractFilter {

	public MateriaPrimaFilter() {
	}

	public MateriaPrimaFilter(MateriaPrimaFilterDTO filter) {
		super(filter.getPageSize(), filter.getPage(), filter.getAsc(), filter.getIdx());
	}

	@Override
	public List<FilterData> getFilterDatas() {
		List<FilterData> list = new ArrayList<FilterData>();
		return list;
	}

}
