package org.omsf.store.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.omsf.member.model.Member;
import org.omsf.member.service.MemberService;
import org.omsf.review.model.RequestReview;
import org.omsf.store.model.Menu;
import org.omsf.store.model.Photo;
import org.omsf.store.model.Store;
import org.omsf.store.model.StorePagination;
import org.omsf.store.service.MenuService;
import org.omsf.store.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
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
	private final MemberService<Member> memberService;
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
		String username = principal.getName();
		Store store = objectMapper.readValue(storeJson, Store.class);
		List<Menu> menus = objectMapper.readValue(menusJson, new TypeReference<List<Menu>>() {});
		store.setUsername(username);
	
		int storeNo = storeService.createStore(store);
        if (picture != null && !picture.isEmpty()) {
        	int photoNo = storeService.UploadImage(picture, storeNo, username);
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
		storePhoto = storeService.getPhotoByPhotoNo(store.getPicture());
		List<Photo> gallery = storeService.getStoreGallery(storeNo);
		
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
    public String showStoreEditForm(@PathVariable("storeNo") int storeNo, Model model,
    		Principal principal) {
    	String username = principal.getName();
    	Store store = storeService.getStoreByNo(storeNo);
    	
    	if (store.getUsername() != null) {	
    		Member member = (Member) memberService.findByUsername(store.getUsername()).get();
    		if (member.getMemberType().equals("owner")  && (store.getUsername() != username)) {
    			return "redirect:/error";
    		}
    	}
    	
    	List<Menu> menus = menuService.getMenusByStoreNo(storeNo);
    	List<Photo> gallery = storeService.getUpdateStoreGallery(storeNo, username);
    	Photo storePhoto = storeService.getPhotoByPhotoNo(store.getPicture());
    	
    	//상점정보
        model.addAttribute("store", store);
        model.addAttribute("menus", menus);
        model.addAttribute("gallery", gallery);
        model.addAttribute("storePhoto", storePhoto);
        return "store/updateStore"; 
    }
	
    @ResponseBody
    @PostMapping("/{storeNo}")
    @Transactional
	public String updateStore(
			@PathVariable("storeNo") int storeNo,
			@RequestParam(value= "store") String storeJson,
			@RequestParam(value = "photo", required = false) String photosJson,
			@RequestParam(value = "menus", required = false) String menusJson,
	        Principal principal, Model model) throws  JsonProcessingException {
    	
    	String username = principal.getName();
    	Store currentStore = storeService.getStoreByNo(storeNo);
    	Store store = objectMapper.readValue(storeJson, Store.class);
    	List<Menu> menus = objectMapper.readValue(menusJson, new TypeReference<List<Menu>>() {});
    	List<Integer> photoOrder = objectMapper.readValue(photosJson, new TypeReference<List<Integer>>() {});

    	//가게 업데이트
    	store.setStoreNo(storeNo);
    	store.setUsername(username);
    	store.setPicture(currentStore.getPicture());
    	storeService.updateStore(store);
		//메뉴 업데이트
    	menuService.updateMenus(storeNo, menus);
		//사진 업데이트
		storeService.updatePhotoOrder(photoOrder, storeNo, username);
		
	    return "store/{storeNo}";
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
	
	
	@ResponseBody
    @DeleteMapping("/{storeNo}/{photoNo}")
    public ResponseEntity<?> deleteStoreGallery(@PathVariable int storeNo,
    		@PathVariable int photoNo,
    		Principal principal) {
        String username = principal.getName();
        Photo photo = storeService.getPhotoByPhotoNo(photoNo);
        Store store = storeService.getStoreByNo(storeNo);
        String memberType = null;
        if (store.getUsername() != null) {        	
        	memberType = memberService.findByUsername(store.getUsername()).get().getMemberType();
        }
        
        //사장
        if (store.getUsername() == username && memberType == "owner") {
        	storeService.deleteImage(photoNo);
        	return ResponseEntity.status(HttpStatus.ACCEPTED).body("사진을 삭제하였습니다.");
        	   
        }
        //일반인
        if (photo == null || !photo.getUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
        }
        storeService.deleteImage(photoNo);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("사진을 삭제하였습니다.");
    }
	
	
	
	
	
	@PutMapping("/{storeNo}/{photoNo}")
	@ResponseBody
	@Transactional
    public ResponseEntity<?> updateStorePhoto(@PathVariable int storeNo,
    		@PathVariable int photoNo, @RequestParam(value= "photo", required = true) ArrayList<MultipartFile> photo,
    		Principal principal) throws IOException {
        String username = principal.getName();
        Photo storePhoto = storeService.getPhotoByPhotoNo(photoNo);
        Store store = storeService.getStoreByNo(storeNo);
        String memberType = null;
        
        if (store.getUsername() != null) {        	
        	memberType = memberService.findByUsername(store.getUsername()).get().getMemberType();
        }
        
        //사장
        if (store.getUsername() == username && "owner".equals(memberType)) {
        	storeService.deleteImage(photoNo);
        	int newPhotoNo = storeService.UploadImage(photo, storeNo, username);
        	store.setPicture(newPhotoNo);
        	storeService.updatePicture(store);
        	return ResponseEntity.status(HttpStatus.ACCEPTED).body(newPhotoNo);
        	   
        }
        //일반인
        if (storePhoto == null || (storePhoto.getUsername() != null &&!username.equals(storePhoto.getUsername()))  ) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
        }
        
        storeService.deleteImage(photoNo);
        int newPhotoNo = storeService.UploadImage(photo, storeNo, username);
    	store.setPicture(newPhotoNo);
    	storeService.updatePicture(store);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(newPhotoNo);
    }
	
 @PostMapping("/{storeNo}/upload-photo")
    public ResponseEntity<Photo> uploadStorePicture(@PathVariable int storeNo, @RequestParam("photo") ArrayList<MultipartFile> photos,
    		Principal principal) throws IOException {
        	
	 		String username = principal.getName();
            int newPhotoNo = storeService.UploadImage(photos, storeNo, username);
            Photo photo = storeService.getPhotoByPhotoNo(newPhotoNo);           
            return ResponseEntity.ok(photo);
    }
}
