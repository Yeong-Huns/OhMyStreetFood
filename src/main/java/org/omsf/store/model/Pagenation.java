package org.omsf.store.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Pagenation {
	private int pageNumber;
	private int pageSize;
	
	private int offset;
}
