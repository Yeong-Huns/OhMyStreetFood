package org.omsf.store.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.omsf.store.model.Photo;
import org.omsf.store.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ViewCountServiceImpl implements ViewCountService{

	private ObjectMapper objectMapper = new ObjectMapper();
    private final RedisTemplate<String, String> redisTemplate;
	private final StoreService storeService;
    
    private static final String DAILY_VIEW_COUNT_KEY = "store:daily:view:count:";
    private static final String POPULAR_STORES_KEY = "popular:stores";
    
    @Override
    public void incrementViewCount(Integer storeNo) {
        String dailyKey = DAILY_VIEW_COUNT_KEY + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + ":" + storeNo;


        // 일일 조회수 증가 및 7일 후 만료 설정
        redisTemplate.opsForValue().increment(dailyKey);
        redisTemplate.expire(dailyKey, 7, TimeUnit.DAYS);

        // Sorted Set에 저장 (전체 조회수 기준)
        redisTemplate.opsForZSet().incrementScore("store:rankings", storeNo.toString(), 1);
    }
    @Override
    public void decreaseViewCount(Integer storeNo, int count) {
        redisTemplate.opsForZSet().incrementScore("store:rankings", storeNo.toString(), -count);
    }

    // 7일 전 조회수 감소 처리 (스케줄러에서 호출)
    @Override
    public void decreaseOldViewCounts() {
        String oldDailyKey = DAILY_VIEW_COUNT_KEY + LocalDate.now().minusDays(7).format(DateTimeFormatter.ISO_DATE) + ":*";
        Set<String> keys = redisTemplate.keys(oldDailyKey);
        for (String key : keys) {
            String[] parts = key.split(":");
            Integer storeNo = Integer.parseInt(parts[parts.length - 1]);
            String countStr = redisTemplate.opsForValue().get(key);
            if (countStr != null) {
                int count = Integer.parseInt(countStr);
                decreaseViewCount(storeNo, count);
            }
            redisTemplate.delete(key);
        }
    }
    
    @Transactional
    @Override
    public Cookie addViewCount(HttpServletRequest request, int storeNo) {

        Cookie[] cookies = request.getCookies();
        Cookie oldCookie = this.findCookie(cookies, "View_Count");

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + storeNo + "]")) {
                oldCookie.setValue(oldCookie.getValue() + "[" + storeNo + "]");
                this.incrementViewCount(storeNo);
            }
            oldCookie.setPath("/");
            return oldCookie;
        } else {
            Cookie newCookie = new Cookie("View_Count", "[" + storeNo + "]");
            newCookie.setPath("/");
            this.incrementViewCount(storeNo);
            return newCookie;
        }
    }
    
    @Override
    public Cookie findCookie(Cookie[] cookies, String name) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }
    
    private void cacheTop10Stores() throws JsonProcessingException {
    	Set<String> top10StoreNos = redisTemplate.opsForZSet().reverseRange("store:rankings", 0, 9);
        if (top10StoreNos != null) {
            List<Store> top10Stores = new ArrayList<>();
            for (String storeNo : top10StoreNos) {
                Store store = storeService.getStoreByNo(Integer.parseInt(storeNo));
                top10Stores.add(store);
            }
            // Redis에 상위 10개 가게 정보 캐싱
            redisTemplate.opsForValue().set(POPULAR_STORES_KEY, objectMapper.writeValueAsString(top10Stores));
        }
    }
    
	@Override
	public void saveStoreAndPhoto(Store store, Photo photo) {
//		 String storeKey = STORE_KEY_PREFIX + store.getStoreNo();
//	        redisTemplate.opsForHash().put(storeKey, "storeNo", store.getStoreNo().toString());
//	        redisTemplate.opsForHash().put(storeKey, "name", store.getName());
//	        redisTemplate.opsForHash().put(storeKey, "description", store.getDescription());
//
//	        if (photo != null) {
//	            String photoKey = PHOTO_KEY_PREFIX + photo.getPhotoNo();
//	            redisTemplate.opsForHash().put(photoKey, "photoNo", photo.getPhotoNo().toString());
//	            redisTemplate.opsForHash().put(photoKey, "url", photo.getUrl());
//	        }
	}
}
