package org.omsf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.omsf.store.dao.StoreRepository;
import org.omsf.store.model.Menu;
import org.omsf.store.model.Store;
import org.omsf.store.service.MenuService;
import org.omsf.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@WebAppConfiguration
@SpringJUnitConfig(locations = {"file:**/*-context.xml"})
@Transactional
@Rollback
public class MenuTests {
	
	@Autowired
	StoreService storeService;
	@Autowired
	StoreRepository storeRepository;
	@Autowired
	MenuService menuService;
	
	private int storeNo; 
	private int menuNo;

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
        
        Menu menu = Menu.builder()
        			.menuName("간장계란밥")
        			.price(10000)
        			.storeNo(storeNo)
        			.build();
        menuService.createMenu(menu);
        menuNo = menu.getMenuNo();
        System.out.println("생성된 menuNo: " + menuNo);
    }
    
	@Test
	public void createMenuTest() {
		
	}
	
	@Test
	public void getMenusTest() {
		Menu menu = Menu.builder()
    			.menuName("육회덮밥")
    			.price(10000)
    			.storeNo(storeNo)
    			.build();
		menuService.createMenu(menu);
		List<Menu> menus = menuService.getMenusByStoreNo(storeNo);
		assertEquals(2, menus.size());
		assertEquals("육회덮밥", menus.get(1).getMenuName());
	}
	
	@Test
	public void updateMenuTest() {
		Menu menu = menuService.getMenuByMenuNo(menuNo);
		menu.setMenuName("돈까스");
		menu.setPrice(8000);
		menuService.updateMenu(menu);
		Menu menu2 = menuService.getMenuByMenuNo(menuNo);
		assertEquals(menu.getMenuName(), menu2.getMenuName());
		assertEquals(menu.getPrice(), menu2.getPrice());
	}
		
	@Test
	public void deleteMenuTest() {
		menuService.deleteMenu(menuNo);
		assertThrows(NoSuchElementException.class, () -> {
			menuService.getMenuByMenuNo(menuNo);
	    });
	}
}
