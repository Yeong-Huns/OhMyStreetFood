package org.omsf.store.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.UUID;

import org.omsf.store.model.Store;
import org.omsf.store.service.StoreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
//	private final ReviewService reviewService;
//	private final MenuService menuService;

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
    public String addStore(
            @RequestParam("storeName") String storeName,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam("address") String address,
            @RequestParam("introduce") String introduce,
            @RequestParam("days") String[] selectedDays,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime,
            @RequestParam(value = "picture", required = false) MultipartFile picture,
            RedirectAttributes redirectAttributes
    ) {
        try {
            String operatingDate = String.join(",", selectedDays);
            String operatingHours = startTime + " - " + endTime;

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

            // Handle picture upload if provided
            if (picture != null && !picture.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + picture.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);

                // 디렉토리가 존재하지 않으면 생성
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }

                // 파일을 지정된 경로로 복사
                Files.copy(picture.getInputStream(), filePath);

                String picturePath = filePath.toString();
                store.setPicturePath(picturePath);
            }

            // storeService를 사용하여 데이터베이스에 가게 정보 저장
            storeService.addStore(store);

            redirectAttributes.addFlashAttribute("successMessage", "가게 등록이 완료되었습니다.");
            return "index";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "가게 등록 중 오류가 발생했습니다.");
            return "index";
        }
    }
}
