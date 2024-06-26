package org.omsf.store.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.omsf.review.model.RequestReview;
import org.omsf.store.model.Menu;
import org.omsf.store.model.Photo;
import org.omsf.store.model.Store;
import org.omsf.store.model.StorePagination;
import org.omsf.store.service.MenuService;
import org.omsf.store.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
//	private final ReviewService reviewService;
	private ObjectMapper objectMapper = new ObjectMapper();

	@GetMapping("/addbygeneral")
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
	
	@GetMapping("list/page")
	@ResponseBody()
	 public List<Map<String, Object>> storePageWithSorting(
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
		List<Map<String,Object>> stores = storeService.getStoreList(pageRequest);  
		return stores; 
    }
	
	@GetMapping("/list")
	public String showStorePage(Model model,
			@RequestParam(required = false) String orderType) {
		StorePagination pageRequest = StorePagination.builder()
				.orderType(orderType)
                .build();
		List<Map<String,Object>> stores = storeService.getStoreList(pageRequest);
	    model.addAttribute("stores", stores);
	    
	    //처음 20개 스크롤 + 10개씩
	    return "store";
	}
	
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
    public String searchPage() {
        return "search/searchTag";
    }
	
	@GetMapping("/search/list")
    public String searchPage(
        @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
        Model model) {
		
        List<Store> initialStores = storeService.searchByKeyword(keyword, 0, 5);
        model.addAttribute("keyword", keyword);
        model.addAttribute("stores", initialStores);
        return "search/searchList";
    }
	
	@GetMapping("/search/lists")
    public String searchStores(
        @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
        @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
        @RequestParam(value = "limit", required = false, defaultValue = "5") int limit,
        Model model) {
		
        List<Store> stores = storeService.searchByKeyword(keyword, offset, limit);
        model.addAttribute("stores", stores);
        
        return "search/searchItems";
    }
	
	@ResponseBody
	@GetMapping("api")
	public List<Store> getStoresByPosition(@RequestParam(value = "position", defaultValue = "서울 종로구") String position){
		log.info("api 요청 완료");
		return storeService.getStoresByPosition(position);
	}	
}
