package org.omsf;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.omsf.store.dao.StoreRepository;
import org.omsf.store.model.Store;
import org.omsf.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@WebAppConfiguration
@SpringJUnitConfig(locations = {"file:**/*-context.xml"})
@Transactional
@Rollback
public class ApplicationTests {
	
	@Autowired
	StoreService storeService;
	@Autowired
	 StoreRepository storeRepository;

	
	@Test
	public void testInsert() {
		Store store = Store.builder()
				  	  .storeName("테스트 가게")
				  	  .latitude(33.450701)
				  	  .longitude(126.570667)
				  	  .address("우리집")
				  	  .introduce("우리 가게는 어제 막 열었습니다.")
				  	  .operatingDate("월, 화, 수, 목, 금, 토")
				  	  .operatingHours("10:00:00, 18:00:00")
				  	  .username("test7@naver.com")
					  .build();
		
		storeRepository.createStore(store);
		int result = storeRepository.getStoreNo();
		System.out.println(result);
	}
	
	@Test
	public void uploadTest() {
		Store store = Store.builder()
			  	  .storeName("테스트 가게")
			  	  .latitude(33.450701)
			  	  .longitude(126.570667)
			  	  .address("우리집")
			  	  .introduce("우리 가게는 어제 막 열었습니다.")
			  	  .operatingDate("월, 화, 수, 목, 금, 토")
			  	  .operatingHours("10:00:00, 18:00:00")
			  	  .username("test7@naver.com")
				  .build();
		
		byte[] content = "Hello World!".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", content);
		ArrayList<MultipartFile> files = new ArrayList<>();
	    files.add(file);
		
		storeRepository.createStore(store);
		int storeNo = storeRepository.getStoreNo();
		System.out.println("생성된 storeNo: " + storeNo);
		storeService.UploadImage(files, storeNo);
		
	}
}
