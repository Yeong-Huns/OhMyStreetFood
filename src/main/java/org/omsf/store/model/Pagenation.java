package org.omsf.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Pagenation {
	private int pageNumber;
	private int pageSize;
	
	private int offset;
	
}
