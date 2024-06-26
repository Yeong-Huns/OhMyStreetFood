package org.omsf.main;

import java.util.List;
import java.util.Map;

import org.omsf.store.model.StorePagination;
import org.omsf.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MenuController {

	@Autowired
	private StoreService storeService;
	
    @GetMapping("/")
    public String showHome(Model model) {
//    	String position = "종로구";
//    	List<Store> stores = storeService.getStoreByposition(position);
//    	log.info("상점 목록 정보 : {}", stores.toString());
//    	model.addAttribute("stores", stores);
        return "index";
    }

    @GetMapping("/store")
    public String showStorePage() {
        return "store";
    }

    @GetMapping("/search")
    public String showSearchPage(Model model,
			@RequestParam(required = false) String orderType ) {
    	
    	//처음 목록이 나올 때 어떻게 보여줄지 정해야함
    	StorePagination pageRequest = StorePagination.builder()
				.orderType(orderType)
                .build();
    	List<Map<String,Object>> stores = storeService.getStoreList(pageRequest);
	    model.addAttribute("stores", stores);
	    //처음 20개 스크롤 + 10개씩
    	return "search";
    }
}
