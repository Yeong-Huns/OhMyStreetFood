package org.omsf.store.model;

import java.sql.Timestamp;

import javax.validation.constraints.Pattern;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
