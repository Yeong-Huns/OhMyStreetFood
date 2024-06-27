package org.omsf.store.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.omsf.review.model.RequestReview;
import org.omsf.review.model.Review;
import org.omsf.review.service.ReviewService;
import org.omsf.store.model.Like;
import org.omsf.store.model.Menu;
import org.omsf.store.model.Photo;
import org.omsf.store.model.Store;
import org.omsf.store.service.LikeService;
import org.omsf.store.service.MenuService;
import org.omsf.store.service.SearchService;
import org.omsf.store.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
	
	private final StoreService storeService;
	private final MenuService menuService;
	private final ReviewService reviewService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	private final LikeService likeService;
	private final SearchService searchService;

	@GetMapping("/createstore")
	public String showAddStoreGeneralPage() {
	    return "store/addStore";
	}

	@PostMapping("/createstore")
	@ResponseBody
	public ResponseEntity<String> createStore(
			@RequestParam(value= "store") String storeJson,
			@RequestParam(value = "photo", required = false) ArrayList<MultipartFile> picture,
			@RequestParam(value = "menus", required = false) String menusJson,
	        Principal principal
	) throws IOException {
		
		Store store = objectMapper.readValue(storeJson, Store.class);
		List<Menu> menus = objectMapper.readValue(menusJson, new TypeReference<List<Menu>>() {});
		store.setUsername(principal.getName());
	
		int storeNo = storeService.createStore(store);
        if (picture != null && !picture.isEmpty()) {
        	int photoNo = storeService.UploadImage(picture, storeNo);
        	store.setPicture(photoNo);
        	storeService.updateStore(store);
        }
        
        menus.stream().peek(menu -> menu.setStoreNo(storeNo))
        .forEach(menuService::createMenu);
        
        return ResponseEntity.ok("");
	}
	
//	@GetMapping("list/page")
//	@ResponseBody()
//	 public List<Map<String, Object>> storePageWithSorting(
//			 	@RequestParam(required = false, defaultValue = "likes") String order,
//			 	@RequestParam(defaultValue = "1" ) int page,
//			 	@RequestParam(required = false) String keyword,
//	            @RequestParam(required = false, defaultValue = "DESC") String sort) {
//        
//		StorePagination pageRequest = StorePagination.builder()
//                                    .currPageNo(page) 
//                                    .orderType(order)
//                                    .searchType("storeName")
//                                    .keyword(keyword)
//                                    .sortOrder(sort)
//                                    .build();
//		List<Map<String,Object>> stores = storeService.getStoreList(pageRequest);  
//		return stores; 
//    }
//	
//	@GetMapping("/list")
//	public String showStorePage(Model model,
//			@RequestParam(required = false) String orderType) {
//		StorePagination pageRequest = StorePagination.builder()
//				.orderType(orderType)
//                .build();
//		List<Map<String,Object>> stores = storeService.getStoreList(pageRequest);
//	    model.addAttribute("stores", stores);
//	    
//	    //처음 20개 스크롤 + 10개씩
//	    return "store";
//	}
	
	@GetMapping("/{storeNo}")
	public String showStoreDetailPage(Principal principal, @PathVariable Integer storeNo, Model model) {
		Store store = storeService.getStoreByNo(storeNo);
		List<Menu> menu = menuService.getMenusByStoreNo(storeNo);
		Photo storePhoto = null;
		if (store.getPicture() != null) {
			storePhoto = storeService.getPhotoByPhotoNo(store.getPicture());
		}
		List<Photo> gallery = storeService.getStorePhotos(storeNo);

		model.addAttribute("store", store);
		model.addAttribute("menus", menu);
		model.addAttribute("storePhoto", storePhoto);
		model.addAttribute("gallery", gallery);
		
		// leejongseop - 리뷰 작성 폼 바인딩
		if (!model.containsAttribute("requestReview")) {
			RequestReview review = new RequestReview();
			review.setStoreStoreNo(storeNo);
			review.setMemberUsername(principal == null ? "" : principal.getName());
            model.addAttribute("requestReview", review);
        }
		
		List<Review> review = reviewService.getReviewListOnStore(storeNo);
		
		model.addAttribute("store", store);
		model.addAttribute("menus", menu);
		model.addAttribute("reviews", review);
		
		return "store/showStore";
	}

    @GetMapping("/{storeNo}/update")
    public String showStoreEditForm(@PathVariable("storeNo") int storeId, Model model,
    		Principal principal) {
    	String username = principal.getName();
        Store store = storeService.getStoreByNo(storeId);
        model.addAttribute("store", store);
        return "store-edit-form"; 
    }

    @GetMapping("/search")
    public String searchPage(Model model) {
        List<Map<String, Object>> search = searchService.getAllKeywords();
        
        model.addAttribute("searchs", search);
        return "search/searchTag";
    }

	@GetMapping("/list")
	public String searchPage(
	    @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
	    @RequestParam(value = "orderType", required = false, defaultValue = "storeNo") String orderType,
//	    @RequestParam(value = "position") String position,
	    HttpServletRequest request,
	    Model model) {
		
	    List<Store> initialStores = storeService.searchByKeyword(keyword, orderType, 0, 5);
	    
	    List<Photo> pictures = new ArrayList<>();
	    for (Store store : initialStores) {
	    	if (store.getPicture() != null) {
	    		Photo photo = storeService.getPhotoByPhotoNo(store.getPicture());	    		
	    		pictures.add(photo);
	    	}
	    }
	    
//	    System.out.println("test" + storeService.getStoresByPosition(position));

	    // ip 주소 설정
	    String userIp = "";
	    
        if (request != null) {
        	userIp = request.getHeader("X-FORWARDED-FOR");
            if (userIp == null || "".equals(userIp)) {
            	userIp = request.getRemoteAddr();
            }
        }
        
    	// 검증 로직 추가
        boolean isValidKeyword = keyword.matches("^[^ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎㄳㅄ]*$");
    	
        if (isValidKeyword && keyword != null && !keyword.isEmpty()) {            
            searchService.insertKeyword(userIp, keyword);
        }
	    
        model.addAttribute("stores", initialStores);
        model.addAttribute("pictures", pictures);
        model.addAttribute("keyword", keyword);
        model.addAttribute("orderType", orderType);
	    return "search/searchList";
	}

	@GetMapping("/lists")
	public String searchStores(
	    @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
	    @RequestParam(value = "orderType", required = false, defaultValue = "storeNo") String orderType,
	    @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
	    @RequestParam(value = "limit", required = false, defaultValue = "5") int limit,
	    Model model) {
	    
	    List<Store> stores = storeService.searchByKeyword(keyword, orderType, offset, limit);
	    model.addAttribute("stores", stores);
	    
	    return "search/searchItems";
	}
	
	@ResponseBody
	@GetMapping("api")

	public List<Store> getStoresByPosition(@RequestParam(value = "position", defaultValue = "서울 종로구") String position,
									@RequestParam("latitude") String latitude,
									@RequestParam("longitude") String longitude){
		log.info("위도 : {}, 경도 : {}", latitude, longitude);
		log.info("api 요청 완료");
		log.info("position : {}" , position);
		return storeService.getStoresByPosition(position);
	}
	
	// leejongseop - like 기능
	@PreAuthorize("hasRole('ROLE_USER')")
	@ResponseBody
	@PostMapping("like/insert")
	public String insertLike(Principal principal, @RequestBody Like like, Errors errors) {
		log.info("like insert api 요청 완료");
		log.info("like 정보 : {}", like.toString());
		like.setMemberUsername(principal.getName());
		likeService.insertLike(like);
		return "찜 목록에 등록";
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@ResponseBody
	@DeleteMapping("like/delete")
	public String deleteLike(Principal principal, @RequestBody Like like, Errors errors) {
		log.info("like delete api 요청 완료");
		log.info("like 정보 : {}", like.toString());
		like.setMemberUsername(principal.getName());
		likeService.deleteLike(like);
		return "찜 목록에 제외";
	}
	
	// LIKE돼 있는 지 확인 하는 메소드
	@PreAuthorize("hasRole('ROLE_USER')")
	@ResponseBody
	@GetMapping("like/check")
	public ResponseEntity<Integer> isLike(Principal principal, Like like, Errors errors) {
		log.info("like check api 요청 완료");
		log.info("like 정보 : {}", like.toString());
		like.setMemberUsername(principal.getName());
		int count = likeService.isLike(like);
		log.info("count 개수 : {}", count);
		return new ResponseEntity<>(count, HttpStatus.OK);
	}
}

