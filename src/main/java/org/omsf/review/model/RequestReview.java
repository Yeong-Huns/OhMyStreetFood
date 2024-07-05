package org.omsf.review.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.omsf.review.annotation.Rating;

import lombok.Data;
/**
 * author         : leejongseop
 * ===========================================================
          AUTHOR             NOTE
 * -----------------------------------------------------------
       leejongseop       최초 생성
 */
@Data
public class RequestReview {

	@Rating(message = "1 ~ 5점 사이로만 입력해주세요.")
	private int rating;
	
	@NotBlank(message = "반드시 입력해주세요.")
	private String content;
	

	private String memberUsername;
	private int storeStoreNo;
}
