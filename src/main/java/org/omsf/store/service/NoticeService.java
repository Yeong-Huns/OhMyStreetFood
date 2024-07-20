package org.omsf.store.service;

import java.util.List;

import org.omsf.store.model.Notice;
import org.omsf.store.model.Pagenation;
import org.springframework.stereotype.Service;

@Service
public interface NoticeService {
	
	int createNotice(Notice notice);
	void sendToSubscribers(List<String> username, int noticeId);
	
	List<Notice> getNotices(String username, Pagenation page);
	void markNoticeAsRead(int noticeId);
	void markNoticeAsDeleted(int noticeId);
	
}
