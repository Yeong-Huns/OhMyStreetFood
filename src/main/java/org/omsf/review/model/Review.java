package org.omsf.review.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Review {

	private int reviewNo;
	private int rating;
	private String content;
	private Date createDat;
	private Date modifieDat;
	private String memberUserName;
	private int storeStoreNo;
}
