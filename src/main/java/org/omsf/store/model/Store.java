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
public class Store {
	private Integer storeNo;
	private String storeName;
	private Double latitude;
	private Double longitude;
	private String address;
	private String introduce;
	private String operatingDate;
	private String operatingHours;

	private int totalReview;
	private double totalRating;
	private int likes;
	private Timestamp createdAt;
	private Timestamp modifiedAt;
	
	private String username;
	private Integer picture;


}
