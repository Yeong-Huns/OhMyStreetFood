package org.omsf.store.dao;

import java.util.List;

import org.omsf.store.model.Notice;
import org.omsf.store.model.NoticeDto.NoticeDetailRequest;
import org.omsf.store.model.NoticeDto.NoticeDetailResponse;
import org.omsf.store.model.UserNoticeStatus;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository {
	
	int createNotice(Notice notice);
	int sendToSubscriber(UserNoticeStatus noticeStatus);
	
	List<NoticeDetailResponse> getNotices(NoticeDetailRequest noticeRequest);
	void markNoticeAsRead(int noticeNo);
	void markNoticeAsDeleted(int noticeNo);
}
