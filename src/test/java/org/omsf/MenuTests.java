package org.omsf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.omsf.error.Exception.CustomBaseException;
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
		for (int i=1; i<=5; i++) {
			Menu menu = Menu.builder()
			.menuName("테스트 메뉴" + i)
			.price(i * 1000)
			.storeNo(storeNo)
			.build();
			menuService.createMenu(menu);
		}
		List<Menu> menus = new ArrayList<Menu>();
		for (int i=6; i>=1; i--) {
			Menu menu = Menu.builder()
			.menuName("테스트 메뉴" + i)
			.price(i * 1000)
			.storeNo(storeNo)
			.build();
			menus.add(menu);
		}
		menuService.updateMenus(storeNo, menus);
		
		List<Menu> dbMenus = menuService.getMenusByStoreNo(storeNo);
		for (int i=0; i<dbMenus.size(); i++) {
			Menu dbMenu = dbMenus.get(i);
			Menu Menu = menus.get(i);
			System.out.println(dbMenu);
			assertEquals(dbMenu.getMenuName(), Menu.getMenuName());
		}
		
	}
		
	@Test
	public void deleteMenuTest() {
		menuService.deleteMenu(menuNo);
		assertThrows(CustomBaseException.class, () -> {
			menuService.getMenuByMenuNo(menuNo);
	    });
	}
}
