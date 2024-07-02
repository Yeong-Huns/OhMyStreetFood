package org.omsf.store.service;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.omsf.store.model.StoreInfo;

public interface ViewCountService {
	 public void incrementViewCount(Integer storeNo);
	 public void decreaseViewCount(Integer storeNo, int count);
	 public void decreaseOldViewCounts();
	 Cookie addViewCount(HttpServletRequest request, int storeNo);
	 public void updateTop10Stores();
	 public void removeStoreRankings(int storeNo);
	 
	 Cookie findCookie(Cookie[] cookies, String name);
	 List<StoreInfo> getTop10PopularStores();
	 StoreInfo getPopularStoreInfo(int StoreNo);
}
