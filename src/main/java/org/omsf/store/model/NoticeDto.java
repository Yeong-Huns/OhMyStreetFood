package org.omsf.store.model;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class NoticeDto {
	
	@Builder
	@Getter
	public static class Create {
		
		@NotBlank(message = "제목은 필수입니다.")
		private String title;
		@NotBlank(message = "내용은 필수입니다.")
		private String content;
		@NotBlank()
		private Integer storeNo;
		
		public Notice toEntity() {
			Notice notice = Notice.builder()
					.title(title)
					.content(content)
					.storeNo(storeNo)
					.createdAt(new Timestamp(System.currentTimeMillis()))
					.build();
			return notice;
		}
	}
	
	@Getter
	@Builder
	public static class Response {
		private String title;
		private String content;
		private Timestamp createdAt;
		
		public static Response of(Notice notice) {
			Response response = Response.builder()
					.title(notice.getTitle())
					.content(notice.getContent())
					.createdAt(notice.getCreatedAt())
					.build();
			
			return response;
		}
	}
	
	@Getter
	@Builder
	public static class NoticeDetailResponse {
	    private int noticeNo;
	    private String title;
	    private String content;
	    private boolean isRead;
	    private boolean isDeleted;
	    private String memberUsername;
	    private Timestamp createdAt;
	    private String storePicture;
	}
	
	@Getter
	@Builder
	public static class NoticeDetailRequest {
	    private String username;
	    private Pagenation page;
	}
}
