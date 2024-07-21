package org.omsf.store.service;

import java.util.List;

import org.omsf.store.model.NoticeDto;
import org.omsf.store.model.NoticeDto.NoticeDetailResponse;
import org.omsf.store.model.Pagenation;

public interface NoticeService {
	
	NoticeDto.Response createNotice(NoticeDto.Create noticeDTO);
	void sendToSubscribers(List<String> username, int noticeNo);
	
	List<NoticeDetailResponse> findNoticesByUsername(String username, int pageNumber, int pageSize);
	void markNoticeAsRead(int noticeNo);
	void markNoticeAsDeleted(int noticeNo);
	
}
