package ar.com.avaco.nitrophyl.ws.service.filter;

import ar.com.avaco.fwk.core.component.dto.SortPageDTO;
import ar.com.avaco.fwk.core.domain.filter.AbstractFilter;

public class SimplePager extends AbstractFilter {

	public SimplePager() {
	}

	public SimplePager(SortPageDTO sp) {
		super(sp.getPageSize(), sp.getPage(), sp.getAsc(), sp.getIdx());
	}

}
