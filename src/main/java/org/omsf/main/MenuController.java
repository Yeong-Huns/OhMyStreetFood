package org.omsf.main;

import org.omsf.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String showSearchPage() {
        return "search";
    }
}
