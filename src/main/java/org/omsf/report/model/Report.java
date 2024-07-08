package org.omsf.report.model;

import java.sql.Date;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* @packageName    : org.omsf.report.model
* @fileName       : Report.java
* @author         : leeyunbin
* @date           : 2024.06.18
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.06.18        leeyunbin       최초 생성
*/

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Report { // yunbin
	private Integer reportNo;
	private int storeNo;
	private String username;
	@NotEmpty(message="제목은 필수입니다.")
	private String title;
	@NotEmpty(message="내용은 필수입니다.")
	private String content;
	private Date createdAt;
}
