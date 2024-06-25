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
	private Timestamp createdAt;
	private Timestamp modifiedAt;
	
	private String username;
    private Integer picture;
    
    public Store() {
    	
    }
    
    public Store(Integer storeNo, String storeName, Double latitude, Double longitude, String address, String introduce,
    		String operatingDate, String operatingHours, Integer totalReview, Double totalRating, Integer likes,
    		Timestamp createdAt, Timestamp modifiedAt, String username, Integer picture) {
    	this.storeNo = storeNo;
    	this.storeName = storeName;
    	this.latitude = latitude;
    	this.longitude = longitude;
    	this.address = address;
    	this.introduce = introduce;
    	this.operatingDate = operatingDate;
    	this.operatingHours = operatingHours;
    	this.totalReview = totalReview;
    	this.totalRating = totalRating;
    	this.likes = likes;
    	this.createdAt = createdAt;
    	this.modifiedAt = modifiedAt;
    	this.username = username;
    	this.picture = picture;
    }
}
