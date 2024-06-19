package org.omsf.store.model;

import java.sql.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class MenuVO {
	private int menuNo;
	private String menuName;
	private long price;
	private Date createdAt;
	private Date modifiedAt;
	
	private StoreVO store;
}
