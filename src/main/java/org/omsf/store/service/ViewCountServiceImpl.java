package org.omsf.store.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.omsf.store.model.Menu;
import org.omsf.store.model.Photo;
import org.omsf.store.model.Store;
import org.omsf.store.model.StoreInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

/**
 * packageName    : org.omsf.store.service
 * fileName       : ViewCountServiceImpl
 * author         : KIMCHANGHWAN
 * date           : 2024-07-05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-05      KIMCHANGHWAN       
 */


@Service
@RequiredArgsConstructor
public class ViewCountServiceImpl implements ViewCountService{
	
	private static final Logger log = LoggerFactory.getLogger(ViewCountService.class);
	private ObjectMapper objectMapper = new ObjectMapper();
    private final RedisTemplate<String, Object> redisTemplate;
	private final StoreService storeService;
    private final MenuService menuService;
	
    private static String currentTopStores;
    private static boolean isChanged = false;
	private static final double[] WEIGHT_VALUES = {1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4};
    private static final String DAILY_VIEW_COUNT_KEY = "store:daily:view:count:";
    private static final String POPULAR_STORES_KEY = "popular:stores";
    private static final String STORE_RANKINGS_KEY = "store:rankings";
    
    @Override
    public void incrementViewCount(Integer storeNo) {
    	//dailyviewCount + 날짜 + storeNo 저장
        String dailyKey = DAILY_VIEW_COUNT_KEY + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + ":" + storeNo;

        redisTemplate.opsForValue().increment(dailyKey);
        redisTemplate.expire(dailyKey, 7, TimeUnit.DAYS);
        
        redisTemplate.opsForZSet().incrementScore(STORE_RANKINGS_KEY, storeNo.toString(), WEIGHT_VALUES[0]);
    }

    @Scheduled(cron = "0 0 0 * * ?") 
    @Override
    public void calculateAllStoreScore() {
    	Set<String> keys = redisTemplate.keys(DAILY_VIEW_COUNT_KEY + "*");
    	 //모든가게 가중치 점수 갱신
    	for (String key : keys) {
    	    String[] parts = key.split(":");
    	    String storeNoSt = parts[parts.length - 1];
    	    double totalScore = 0;
        	
        	for (int i=0; i<7; i++) {
        		LocalDate date = LocalDate.now().minusDays(i);
        		//store:daily:view:count:2024-07-02:353
        		String DailyKey = DAILY_VIEW_COUNT_KEY + date.format(DateTimeFormatter.ISO_DATE) + ":" + storeNoSt;
        		Integer countSt = (Integer) redisTemplate.opsForValue().get(DailyKey);
        		System.out.println(DailyKey + "찾는중:" +countSt);
        		if (countSt != null) {
        			double count = Double.valueOf(countSt);
        			totalScore += count * WEIGHT_VALUES[i];
        		}
        	}
        	
        	redisTemplate.opsForZSet().add(STORE_RANKINGS_KEY, storeNoSt, totalScore);
        }
        
        //7일 지난 조회수 삭제
        String oldDailyKey = DAILY_VIEW_COUNT_KEY + LocalDate.now().minusDays(7).format(DateTimeFormatter.ISO_DATE) + ":*";
        Set<String> oldKeys = redisTemplate.keys(oldDailyKey);
        if (oldKeys != null && !oldKeys.isEmpty()) {
        	redisTemplate.delete(oldKeys);
        }
        
        updateTop10Stores();
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
	
    @Override
    @Transactional(readOnly = true)
    public void updateTop10Stores() {
    	Set<ZSetOperations.TypedTuple<Object>> topStores =
    	    redisTemplate.opsForZSet().reverseRangeWithScores(STORE_RANKINGS_KEY, 0, 9);
        log.info("상위 10개 점포 업데이트");
        if (topStores == null || topStores.isEmpty()) {
            return;
        }

        List<StoreInfo> top10Stores = new ArrayList<>();
        for (ZSetOperations.TypedTuple<Object> tuple : topStores) {
        	Object value = tuple.getValue();
            int storeNo = 0;
            if (value instanceof Integer) {
                storeNo = (Integer) value;
            } else if (value instanceof String) {
                storeNo = Integer.parseInt((String) value);
            } else {
            	log.warn("상위점포 없음", storeNo);
            	return;
            }
            
            
            Store store = storeService.getStoreByNo(storeNo);
            if (store != null) {
            	Photo storePhoto = Photo.builder().build();
            	if (store.getPicture() != null) {
            		storePhoto = storeService.getPhotoByPhotoNo(store.getPicture());
            	}
            	List<Menu> menus = menuService.getMenusByStoreNo(storeNo);
            	List<Photo> gallery = storeService.getStoreGallery(storeNo);
            	StoreInfo storeInfo = setStoreInfo(store, storePhoto, gallery, menus);
            	
            	top10Stores.add(storeInfo);
            }
            
            if (top10Stores.size() >= 10) {
                break;  
            }
        }

        try {
            String jsonTopStores = objectMapper.writeValueAsString(top10Stores);
            redisTemplate.opsForValue().set(POPULAR_STORES_KEY, jsonTopStores);
            
        } catch (JsonProcessingException e) {
            log.error("상위 10개 점포 업데이트 중 에러발생!");
            e.printStackTrace();
        }
    }
     
    @Override
    @Scheduled(fixedRate = 60000) 
    public void checkPopularChanged() {
        String newTopStores = (String) redisTemplate.opsForValue().get(POPULAR_STORES_KEY);
        if (newTopStores !=null && currentTopStores == null || !newTopStores.equals(currentTopStores) || isChanged) {
            updateTop10Stores();
            isChanged = false;
            currentTopStores = newTopStores;
        } else {
            log.info("변경된 순위 없음.");
        }
    }
    
    @Override
    @Transactional
    public void removeStoreRankings(int storeNo) {
        redisTemplate.opsForZSet().remove(STORE_RANKINGS_KEY, String.valueOf(storeNo));

        String jsonTopStores = (String) redisTemplate.opsForValue().get(POPULAR_STORES_KEY);
        if (jsonTopStores != null) {
            try {
                List<StoreInfo> topStores = objectMapper.readValue(jsonTopStores, new TypeReference<List<StoreInfo>>() {});
                //없는가게 삭제
                topStores.removeIf(storeInfo -> storeInfo.getStore().getStoreNo() == storeNo);
                String updatedJsonTopStores = objectMapper.writeValueAsString(topStores);
                redisTemplate.opsForValue().set(POPULAR_STORES_KEY, updatedJsonTopStores);
            } catch (JsonProcessingException e) {
                log.error("가게 삭제중 에러발생", e);
            }
        }
        
        //일일키 삭제
        String dailyKeyPattern = DAILY_VIEW_COUNT_KEY + "*:" + storeNo;
        Set<String> keys = redisTemplate.keys(dailyKeyPattern);
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }

        this.markAsChanged();
    }
    
    public List<StoreInfo> getTop10PopularStores() {
        String jsonTopStores = (String) redisTemplate.opsForValue().get(POPULAR_STORES_KEY);
        if (jsonTopStores == null) {
            return Collections.emptyList();
        }

        try {
            return objectMapper.readValue(jsonTopStores, new TypeReference<List<StoreInfo>>() {});
        } catch (JsonProcessingException e) {
            // 예외 처리
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    
    
    public StoreInfo setStoreInfo(Store store, Photo photo, List<Photo> gallery, List<Menu> menus) {
		StoreInfo storeInfo = new StoreInfo();
		
    	storeInfo.setStore(store);
    	storeInfo.setPhoto(photo);
    	storeInfo.setMenus(menus);
    	storeInfo.setGallery(gallery);
    	
    	return storeInfo;
    }

    @Override
    public StoreInfo getPopularStoreInfo(int storeNo) {
        String jsonTopStores = (String) redisTemplate.opsForValue().get(POPULAR_STORES_KEY);
        if (jsonTopStores == null) {
            return null;
        }

        try {
            List<StoreInfo> topStores = objectMapper.readValue(jsonTopStores, new TypeReference<List<StoreInfo>>() {});
            return topStores.stream()
                            .filter(store -> store.getStore().getStoreNo() == storeNo)
                            .findFirst()
                            .orElse(null);
        } catch (JsonProcessingException e) {
            log.error("인기 점포 정보를 얻는중 에러가 발생했습니다.", e);
            return null;
        }
    }
    

    public void markAsChanged() {
        isChanged = true;
    }
}
