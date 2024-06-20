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
public class Review {
	
	private int reviewNo;
	private int rating;
	private String content;
	private Date createdAt;
	private Date modifiedAt;
	
	private String username;
	private String storeNo;
}
