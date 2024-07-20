package org.omsf.store.model;


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
public class UserNoticeStatus {
	
	private Integer noticeStatusNo;
	private boolean isRead;
	private boolean isDeleted;
	
	private Integer noticeNo;
	private String username;
}