//package org.omsf;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import java.util.ArrayList;
//import java.util.NoSuchElementException;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.omsf.store.dao.StoreRepository;
//import org.omsf.store.model.Store;
//import org.omsf.store.service.StoreService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//@WebAppConfiguration
//@SpringJUnitConfig(locations = {"file:**/*-context.xml"})
//@Transactional
//@Rollback
//public class StoreTests {
//	
//	@Autowired
//	StoreService storeService;
//	@Autowired
//	StoreRepository storeRepository;
//	
//	private int storeNo; 
//
//    @BeforeEach
//    public void setup() {
//        Store store = Store.builder()
//                .storeName("테스트 가게")
//                .latitude(33.450701)
//                .longitude(126.570667)
//                .address("우리집")
//                .introduce("우리 가게는 어제 막 열었습니다.")
//                .operatingDate("월, 화, 수, 목, 금, 토")
//                .operatingHours("10:00:00, 18:00:00")
//                .username("test7@naver.com")
//                .build();
//
//        storeService.createStore(store);
//        storeNo = store.getStoreNo(); 
//        System.out.println("생성된 storeNo: " + storeNo);
//    }
//	
//	@Test
//	public void testInsert() {
//		
//	}
//	
//	@Test
//	public void uploadTest() {
//		
//		byte[] content = "Hello World!".getBytes();
//        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", content);
//		ArrayList<MultipartFile> files = new ArrayList<>();
//	    files.add(file);
//		Store store = storeService.getStoreByNo(storeNo);
//		int photoNo = storeService.UploadImage(files, storeNo);
//		store.setPicture(photoNo);
//		storeService.updateStore(store);
//		Store dbStore = storeRepository.getStoreByNo(storeNo).get();
//		assertEquals(photoNo, dbStore.getPicture());
//	}
//	
//	@Test
//	public void updateTest() {
//		Store store = storeService.getStoreByNo(storeNo);
//		store.setStoreName("수정된 가게");
//		storeService.updateStore(store);
//		
//		Store store2 = storeService.getStoreByNo(storeNo);
//		assertEquals(store.getStoreName(), store2.getStoreName());
//	}
//	
//	@Test
//	public void deleteTest() {
//		storeService.deleteStore(storeNo);
//		//Exception class
//		assertThrows(NoSuchElementException.class, () -> {
//		        storeService.getStoreByNo(storeNo);
//		    });
//	}
//}
