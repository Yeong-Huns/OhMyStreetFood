package org.omsf.store.model;

import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

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
	private MultipartFile picture;
    private String picturePath;
}
