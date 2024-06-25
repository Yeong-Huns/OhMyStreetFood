package org.omsf.store.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.omsf.review.model.RequestReview;
import org.omsf.store.model.Menu;
import org.omsf.store.model.Store;
import org.omsf.store.model.StorePagination;
import org.omsf.store.service.MenuService;
import org.omsf.store.service.StoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
	
//	private final String uploadDir = "src/main/resources/static/uploads";
	private final String uploadDir = "upload";

	private final StoreService storeService;
	private final MenuService menuService;
//	private final ReviewService reviewService;

//	@PostMapping("/create")
//	@Transactional
//	public String createStore(HttpServletRequest request, Store store, ArrayList<MultipartFile> files) {
//		//storevo db에 저장
//		// 등록할때 사진이 있다면 storeno를 받고  대표사진을 저장
//		// (메인페이지 선택)로 이동
//		int storeNo = storeService.createStore(store);
//		
//		if (files != null) {
//			int pictureNo = storeService.UploadImage(files, storeNo);
//			
//			storeService.updateStore(store);
//		}
//		
//		return "index";
//	}
//
//
//    @GetMapping("/{storeNo}")
//    public String storeDetail(@PathVariable("storeNo") int storeNo, Model model) {
//        Store store = storeService.getStoreByNo(storeNo);
////       ReviewService.getReviewListBystoreNo(storeNo); 최신5개만 + 페이징
//        List<Menu> menus = menuService.getMenusByStoreNo(storeNo);
//        
//        model.addAttribute("store", store);
//        model.addAttribute("menus", menus);
//        return "storeDetail";
//    }
    
	// jaeeun
	@GetMapping("/addbygeneral")
	public String showAddStoreGeneralPage() {
	    return "store/addStoreGeneral";
	}

	@PostMapping("/addbygeneral")
	public String showAddStoreGeneralPage(
	        @RequestParam("storeName") String storeName,
	        @RequestParam("latitude") Double latitude,
	        @RequestParam("longitude") Double longitude,
	        @RequestParam("address") String address,
	        @RequestParam(value = "introduce", required = false) String introduce,
	        @RequestParam(value = "days", required = false) String[] selectedDays,
	        @RequestParam(value = "startTime", required = false) String startTime,
	        @RequestParam(value = "endTime", required = false) String endTime,
	        @RequestParam(value = "picture", required = false) ArrayList<MultipartFile> picture,
	        @RequestParam(value = "menus", required = false) ArrayList<Menu> menus,
	        Principal principal,
	        RedirectAttributes redirectAttributes
	) {
	    try {
	        String operatingDate = (selectedDays != null) ? String.join(",", selectedDays) : null;
	        String operatingHours = (startTime != null && endTime != null) ? startTime + " - " + endTime : null;
	   
	        
	        Store store = Store.builder()
	                .storeName(storeName)
	                .latitude(latitude)
	                .longitude(longitude)
	                .address(address)
	                .introduce(introduce)
	                .operatingDate(operatingDate)
	                .operatingHours(operatingHours)
	                .totalReview(0)
	                .totalRating(0.0)
	                .likes(0)
	                .createdAt(new Timestamp(System.currentTimeMillis()))
	                .modifiedAt(new Timestamp(System.currentTimeMillis()))
	                .username("User123@naver.com")
	                .build();

	        storeService.createStore(store);
	        int storeNo = store.getStoreNo();
	        if (picture != null && !picture.isEmpty()) {
	        	int photoNo = storeService.UploadImage(picture, storeNo);
	        	store.setPicture(photoNo);
	        	storeService.updateStore(store);
	        }

	        if (menus.size() != 0) {	
	        	for (Menu menu : menus) {	        		
	        		menuService.createMenu(menu);
	        	}
	        }
	        
	        return "index";
	    } catch (IOException e) {
	        return "index";
	    }
	}
	
	@GetMapping("list/page")
	@ResponseBody()
	 public List<Store> storePageWithSorting(
			 	@RequestParam(required = false, defaultValue = "likes") String order,
			 	@RequestParam(defaultValue = "1" ) int page,
			 	@RequestParam(required = false) String keyword,
	            @RequestParam(required = false, defaultValue = "DESC") String sort) {
        
		StorePagination pageRequest = StorePagination.builder()
                                    .currPageNo(page) 
                                    .orderType(order)
                                    .searchType("storeName")
                                    .keyword(keyword)
                                    .sortOrder(sort)
                                    .build();

        return storeService.getStoreList(pageRequest); 
    }
	
	@GetMapping("/list")
	public String showStorePage(Model model,
			@RequestParam(required = false) String orderType) {
		StorePagination pageRequest = StorePagination.builder()
				.orderType(orderType)
                .build();
	    List<Store> stores = storeService.getStoreList(pageRequest);
	    model.addAttribute("stores", stores);
	    
	    //처음 20개 스크롤 + 10개씩
	    return "store";
	}
	
	@GetMapping("/{storeNo}")
	public String showStoreDetailPage(Principal principal, @PathVariable Integer storeNo, Model model) {
		Store store = storeService.getStoreByNo(storeNo);
		List<Menu> menu = menuService.getMenusByStoreNo(storeNo);
		
		model.addAttribute("store", store);
		model.addAttribute("menus", menu);
		
		// leejongseop - 리뷰 작성 폼 바인딩
		if (!model.containsAttribute("requestReview")) {
			RequestReview review = new RequestReview();
			review.setStoreStoreNo(storeNo);
			review.setMemberUsername(principal == null ? "" : principal.getName());
            model.addAttribute("requestReview", review);
        }
		
	    return "store/showStore";
	}
	
	@ResponseBody
	@GetMapping("api")
	public List<Store> getStoresByPosition(@RequestParam(value = "position", defaultValue = "서울 종로구") String position){
		log.info("api 요청 완료");
		return storeService.getStoresByPosition(position);
	}
	
}
