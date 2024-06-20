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
public class Photo {
	private int photoNo;
	private String contentType;
	private long fileSize;
	private String picture;
	private Date createdAt;
	
	private int storeNo;
}
