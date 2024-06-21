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
	private Integer totalReview;
	private Double totalRating;
	private Integer likes;
	private Date createdAt;
	private Date modifiedAt;
	private int picture;
	private String username;
}
