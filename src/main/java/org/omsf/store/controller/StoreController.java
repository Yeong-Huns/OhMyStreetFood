package org.omsf.store.controller;

import org.omsf.store.model.Store;
import org.omsf.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
	
	@Autowired
	private final StoreService storeService;
//	private final ReviewService reviewService;
//	private final MenuService menuService;
	
	@GetMapping("/kakaomap")
	public String testKakaoMap() {
		return "store/kakaomap";
	}
	
	// jaeeun
	@GetMapping("/addbyowner")
	public String showAddStoreOwnerPage() {
		return "store/addStoreOwner";
	}
	
	@PostMapping("/addbyowner")
    public String addStoreOwner(Store store,
    							@RequestParam("days") String[] selectedDays,
                                @RequestParam("startTime") String startTime,
                                @RequestParam("endTime") String endTime) {

        String operatingDate = String.join(",", selectedDays);
        String operatingHours = startTime + " - " + endTime;

        store.setOperatingDate(operatingDate);
        store.setOperatingHours(operatingHours);

        storeService.addStore(store);

        return "index";
    }
}
