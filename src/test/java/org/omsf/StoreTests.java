package org.omsf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.omsf.error.Exception.CustomBaseException;
import org.omsf.store.dao.StoreRepository;
import org.omsf.store.model.Store;
import org.omsf.store.model.StorePagination;
import org.omsf.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

/**
 * packageName    : org.omsf
 * fileName       : StoreTests
 * author         : KIMCHANGHWAN
 * date           : 2024-07-05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-05      KIMCHANGHWAN       
 */

@WebAppConfiguration
@SpringJUnitConfig(locations = {"file:**/*-context.xml"})
@Transactional
@Rollback
public class StoreTests {
	
	@Autowired
	StoreService storeService;
	@Autowired
	StoreRepository storeRepository;
	@Autowired
    private WebApplicationContext wac;
	private MockMvc mockMvc;
	private int storeNo; 
	
	@BeforeEach
    public void mock() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
        							.apply(springSecurity())
        								.build();
    }
	
    @BeforeEach
    public void setup() {
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

        storeService.createStore(store);
        storeNo = store.getStoreNo(); 
        System.out.println("생성된 storeNo: " + storeNo);
    }
	
	@Test
	public void testInsert() {
		
	}
	
	@Test
	public void uploadTest() throws IOException {
		
		byte[] content = "Hello World!".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", content);
		ArrayList<MultipartFile> files = new ArrayList<>();
	    files.add(file);
		Store store = storeService.getStoreByNo(storeNo);
		int photoNo = storeService.UploadImage(files, storeNo, "admin@ohmystreetfood.com");
		store.setPicture(photoNo);
		storeService.updateStore(store);
		Store dbStore = storeRepository.getStoreByNo(storeNo).get();
		assertEquals(photoNo, dbStore.getPicture());
	}
	
	@Test
	public void updateTest() {
		Store store = storeService.getStoreByNo(storeNo);
		store.setStoreName("수정된 가게");
		storeService.updateStore(store);
		
		Store store2 = storeService.getStoreByNo(storeNo);
		assertEquals(store.getStoreName(), store2.getStoreName());
	}
	
	@Test
	public void deleteTest() {
		storeService.deleteStore(storeNo);
		//Exception class
		assertThrows(CustomBaseException.class, () -> {
		        storeService.getStoreByNo(storeNo);
		    });
	}
	
	@Test
	public void pagingTest() {
		StorePagination page = StorePagination.builder()
							   .orderType("likes")
							   .searchType("storeName")
							   .keyword("조재")
							   .build();
		
		List<Map<String, Object>> storeList = storeService.getStoreList(page);
		for (Map<String, Object> store : storeList) {
			System.out.println(store);
		}
	}
	
	@Test
	public void storeListPageTest() throws Exception {
		 MvcResult result = mockMvc.perform(get("/store/" + storeNo))
				 .andExpect(status().isOk())
				 .andReturn();
		 String content = result.getResponse().getContentAsString();
		 
	}
}