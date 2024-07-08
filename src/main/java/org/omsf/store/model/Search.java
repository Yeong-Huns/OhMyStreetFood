package org.omsf.store.model;

import java.sql.Timestamp;

import javax.validation.constraints.Pattern;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* @packageName    : org.omsf.store.model
* @fileName       : Search.java
* @author         : iamjaeeuncho
* @date           : 2024.06.20
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.06.20        iamjaeeuncho       최초 생성
*/

@Getter
@Setter
@ToString
@Builder
public class Search {
	private String userIp;
	@Pattern(regexp = "^[^ㄱㄴㄷㄻㅄㅇㅈㅊㅋㅌㅍㅎ]*$")
	private String keyword;
	private Timestamp createdAt;
}
