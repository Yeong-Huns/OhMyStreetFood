package org.omsf.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
	
//	private final StoreService storeService;
//	private final ReviewService reviewService;
//	private final MenuService menuService;
	
	@GetMapping("/kakaomap")
	public String testKakaoMap() {
		return "store/kakaomap";
	}
	
}
