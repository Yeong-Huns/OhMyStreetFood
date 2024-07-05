package org.omsf.review.model;

import java.sql.Date;

import lombok.Data;
/**
 * author         : leejongseop
 * description    :
 * ===========================================================
 *    AUTHOR            NOTE
 * -----------------------------------------------------------
 *  leejongseop       최초 생성
 */
@Data
public class Review {

	private int reviewNo;
	private int rating;
	private String content;
	private Date createdAt;
	private Date modifiedAt;
	private String memberUsername;
	private int storeStoreNo;
}
