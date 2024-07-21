package org.omsf.store.service;

import java.util.List;

import org.omsf.store.model.NoticeDto;
import org.omsf.store.model.NoticeDto.NoticeDetailRequest;
import org.omsf.store.model.NoticeDto.NoticeDetailResponse;

public interface NoticeService {
	
	NoticeDto.Response createNotice(NoticeDto.Create noticeDTO);
	void sendToSubscribers(List<String> username, int noticeNo);
	
	List<NoticeDetailResponse> getNotices(NoticeDetailRequest noticeRequest);
	void markNoticeAsRead(int noticeNo);
	void markNoticeAsDeleted(int noticeNo);
	
}
