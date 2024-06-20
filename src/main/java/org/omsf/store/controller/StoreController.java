package org.omsf.store.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.omsf.store.model.Store;
import org.omsf.store.service.StoreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
	
	private final StoreService storeService;
//	private final ReviewService reviewService;
//	private final MenuService menuService;
	
	@GetMapping("/kakaomap")
	public String testKakaoMap() {
		return "store/kakaomap";
	}
	
	@PostMapping("/store")
	public String createStore(HttpServletRequest request, Store store, ArrayList<MultipartFile> files) {
		//storevo db에 저장
		// 등록할때 사진이 있다면 storeno를 받고  대표사진을 저장
		// (메인페이지)로 이동
		
		int storeNo = storeService.createStore(store);
		if (files != null) {
			 // 방금 만들어진 storeNo를 가져오는 로직
			storeService.UploadImage(files, storeNo);
		}
		
		return "index";
	}
	
}
