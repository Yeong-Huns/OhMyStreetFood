package org.omsf.report.model;

import java.sql.Date;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Report {
	private Integer reportNo;
	private int storeNo;
	private String username;
	@NotNull(message="제목은 필수입니다.")
	private String title;
	@NotNull(message="내용은 필수입니다.")
	private String content;
	private Date createdAt;
}
