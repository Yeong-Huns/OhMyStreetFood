package org.omsf.store.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

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

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
	
//	private final String uploadDir = "src/main/resources/static/uploads";
	private final String uploadDir = "upload";

	private final StoreService storeService;
	private final MenuService menuService;
//	private final ReviewService reviewService;

//	@GetMapping("/kakaomap")
//	public String testKakaoMap() {
//		return "store/kakaomap";
//	}
//	
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
//			store.setPicture(pictureNo);
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
	        @RequestParam(value = "picture", required = false) MultipartFile picture,
	        @RequestParam(value = "menuName", required = false) String[] menuNames,
	        @RequestParam(value = "menuPrice", required = false) long[] menuPrices,
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
	                .username("redjoun@gmail.com")
	                .build();

	        if (picture != null && !picture.isEmpty()) {
	            String fileName = UUID.randomUUID().toString() + "_" + picture.getOriginalFilename();
	            Path filePath = Paths.get(uploadDir, fileName);

	            File uploadDirFile = new File(uploadDir);
	            if (!uploadDirFile.exists()) {
	                uploadDirFile.mkdirs();
	            }

	            Files.copy(picture.getInputStream(), filePath);

	            String picturePath = filePath.toString();
	            store.setPicture(picturePath);
	        }

	        storeService.addStore(store);
	        int storeNo = store.getStoreNo();
	        
	        if (menuNames != null && menuPrices != null) {	        
	        	menuService.addMenu(menuNames, menuPrices, storeNo);
	        }
	        
	        redirectAttributes.addFlashAttribute("successMessage", "가게 등록이 완료되었습니다.");
	        return "index";
	    } catch (IOException e) {
	        redirectAttributes.addFlashAttribute("errorMessage", "가게 등록 중 오류가 발생했습니다.");
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
}
