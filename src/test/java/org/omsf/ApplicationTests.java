package org.omsf;

import org.junit.jupiter.api.Test;
import org.omsf.store.dao.StoreRepository;
import org.omsf.store.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;


@SpringJUnitConfig(locations = {"file:**/*-context.xml"})
@Transactional
@Rollback
public class ApplicationTests {
	
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
	}
}
