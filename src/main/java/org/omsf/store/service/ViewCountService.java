package org.omsf.store.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.omsf.store.model.Photo;
import org.omsf.store.model.Store;

public interface ViewCountService {
	 public void incrementViewCount(Integer storeNo);
	 public void decreaseViewCount(Integer storeNo, int count);
	 public void decreaseOldViewCounts();
	 Cookie addViewCount(HttpServletRequest request, int storeNo);
	 Cookie findCookie(Cookie[] cookies, String name);
	 public void saveStoreAndPhoto(Store store, Photo photo);
}
