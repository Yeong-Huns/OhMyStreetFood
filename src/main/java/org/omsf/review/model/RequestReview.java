package org.omsf.review.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class RequestReview {

	@NotBlank
	@NotEmpty
	private int rating;
	
	@NotEmpty
	@NotBlank
	private String content;
	
	private String memberUsername;
	private int storeStoreNo;
}
