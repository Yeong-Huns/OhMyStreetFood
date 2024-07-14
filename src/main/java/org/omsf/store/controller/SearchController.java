package org.omsf.store.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.omsf.store.model.Photo;
import org.omsf.store.model.Store;
import org.omsf.store.service.SearchService;
import org.omsf.store.service.StoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
* @packageName    : org.omsf.store.controller
* @fileName       : SearchController.java
* @author         : iamjaeeuncho
* @date           : 2024.07.12
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.07.12        iamjaeeuncho       최초 생성
*/

@Slf4j
@Controller
@RequiredArgsConstructor
public class SearchController {
	
	private final StoreService storeService;
	private final SearchService searchService;
	
    // jaeeun - 검색
    @GetMapping("/search")
    public String searchPage(Model model) {
        List<Map<String, Object>> search = searchService.getAllKeywords();
        
        model.addAttribute("searchs", search);
        return "search/searchTag";
    }
    
    // jaeeun - 리스트
	@GetMapping("/list")
	public String searchPage(
	    @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
	    @RequestParam(value = "orderType", required = false, defaultValue = "storeNo") String orderType,
	    @RequestParam(value = "latitude", required = false, defaultValue = "") Double latitude,
        @RequestParam(value = "longitude", required = false, defaultValue = "") Double longitude,
	    HttpServletRequest request,
	    Model model) {
        
        System.out.println("Received latitude: " + latitude + ", longitude: " + longitude);
        
	    List<Store> initialStores = storeService.showStoreList(keyword, orderType, latitude, longitude, 0, 5);
	    
	    List<Photo> pictures = new ArrayList<>();
	    for (Store store : initialStores) {
	    	if (store.getPicture() != null) {
	    		Photo photo = storeService.getPhotoByPhotoNo(store.getPicture());
	    		photo.setStoreNo(store.getStoreNo());
	    		pictures.add(photo);
	    	}
	    }
	    
        String userIp = "";
        if (request != null) {
            userIp = request.getHeader("X-FORWARDED-FOR");
            if (userIp == null || userIp.isEmpty()) {
                userIp = request.getRemoteAddr();
            }
        }

        boolean isValidKeyword = keyword.matches("^[^ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎㄳㅄ]*$");
        if (isValidKeyword && !keyword.isEmpty()) {
            searchService.insertKeyword(userIp, keyword);
        }
        
        model.addAttribute("stores", initialStores);
        model.addAttribute("pictures", pictures);
        
        System.out.println(pictures);
        
        model.addAttribute("keyword", keyword);
        model.addAttribute("orderType", orderType);
        
        if (keyword == null || keyword.isEmpty() || keyword == "") {
            return "store";
        } else {
            return "search/searchList"; 
        }
    }
	
	// jaeeun - 페이징
	@GetMapping("/lists")
	public String searchStores(
	    @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
	    @RequestParam(value = "orderType", required = false, defaultValue = "storeNo") String orderType,
	    @RequestParam(value = "latitude", required = false, defaultValue = "") Double latitude,
        @RequestParam(value = "longitude", required = false, defaultValue = "") Double longitude,
	    @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
	    @RequestParam(value = "limit", required = false, defaultValue = "5") int limit,
	    Model model) {

		List<Store> stores = storeService.showStoreList(keyword, orderType, latitude, longitude, offset, limit);

	    List<Photo> pictures = new ArrayList<>();
	    for (Store store : stores) {
	    	if (store.getPicture() != null) {
	    		Photo photo = storeService.getPhotoByPhotoNo(store.getPicture());
	    		photo.setStoreNo(store.getStoreNo());
	    		pictures.add(photo);
	    	}
	    }

	    model.addAttribute("stores", stores);
	    model.addAttribute("pictures", pictures);

	    return "search/searchItems";
	}
}

