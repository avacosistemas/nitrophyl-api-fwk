package ar.com.avaco.nitrophyl.filter;

import ar.com.avaco.fwk.core.component.dto.filter.SortPageDTO;
import ar.com.avaco.fwk.core.domain.filter.AbstractFilter;

public class SimplePager extends AbstractFilter {

	public SimplePager() {
	}

	public SimplePager(SortPageDTO sp) {
		super(sp.getPageSize(), sp.getPage(), sp.getAsc(), sp.getIdx());
	}

}
