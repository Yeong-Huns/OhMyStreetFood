package org.omsf.store.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notice {
	private Integer noticeNo;
	private String title;
	private String content;
	private Timestamp createdAt;
	
	private Integer storeNo;
}
