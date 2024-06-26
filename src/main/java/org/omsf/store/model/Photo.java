package org.omsf.store.model;

import java.sql.Timestamp;

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
	private Timestamp createdAt;
	
	private int storeNo;
}
