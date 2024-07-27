package org.omsf.main;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omsf.store.model.Photo;
import org.omsf.store.model.Store;
import org.omsf.store.service.NoticeService;
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
	@Autowired
	private NoticeService noticeService;
	
    @GetMapping("/")
    public String showHome(Model model) {
//    	String position = "종로구";
//    	List<Store> stores = storeService.getStoreByposition(position);
//    	log.info("상점 목록 정보 : {}", stores.toString());
//    	model.addAttribute("stores", stores);
    	
    	List<Store> stores = storeService.getPopularStores();
        
    	List<Photo> pictures = new ArrayList<>();
	    for (Store store : stores) {
	    	if (store.getPicture() != null) {
	    		Photo photo = storeService.getPhotoByPhotoNo(store.getPicture());
	    		pictures.add(photo);
	    	} else {
	    		pictures.add(null);
	    	}
	    }
	    Map<String, Object> dataMap = new HashMap<>();
	    dataMap.put("stores", stores);
	    dataMap.put("pictures", pictures);
	    model.addAllAttributes(dataMap);
    	return "index";
    }
    
    @GetMapping("/alarm")
    public String showChart(Model model, Principal principal) {
    	String username = principal.getName();
    	model.addAttribute("notices", noticeService.findNoticesByUsername(username));
    	
    	return "alarm";
    }
}
