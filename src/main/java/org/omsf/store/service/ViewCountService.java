package org.omsf.store.service;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.omsf.store.model.StoreInfo;

/**
 * packageName    : org.omsf.store.service
 * fileName       : ViewCountService
 * author         : KIMCHANGHWAN
 * date           : 2024-07-05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-05      KIMCHANGHWAN       
 */

public interface ViewCountService {
	 public void incrementViewCount(Integer storeNo);
	 public void calculateAllStoreScore();
	 Cookie addViewCount(HttpServletRequest request, int storeNo);
	 public void updateTop10Stores();
	 public void removeStoreRankings(int storeNo);
	 
	 void checkPopularChanged();
	 Cookie findCookie(Cookie[] cookies, String name);
	 List<StoreInfo> getTop10PopularStores();
	 StoreInfo getPopularStoreInfo(int StoreNo);
	 void markAsChanged();
}
