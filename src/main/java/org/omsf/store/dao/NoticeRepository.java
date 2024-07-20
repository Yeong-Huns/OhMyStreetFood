package org.omsf.store.dao;

import java.util.List;

import org.omsf.store.model.Notice;
import org.omsf.store.model.Pagenation;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository {
	
	int createNotice(Notice notice);
	void sendToSubscriber(String username, int noticeId);
	
	List<Notice> getNotices(String username, Pagenation page);
	void markNoticeAsRead(int noticeId);
	void markNoticeAsDeleted(int noticeId);
}
