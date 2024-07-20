package org.omsf.store.service;

import java.util.List;

import org.omsf.store.model.Notice;
import org.omsf.store.model.Pagenation;

public class NoticeServiceImpl implements NoticeService {

	@Override
	public int createNotice(Notice notice) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void sendToSubscribers(List<String> username, int noticeId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Notice> getNotices(String username, Pagenation page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void markNoticeAsRead(int noticeId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void markNoticeAsDeleted(int noticeId) {
		// TODO Auto-generated method stub

	}

}
