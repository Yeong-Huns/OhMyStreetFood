package org.omsf.store.service;

import java.sql.Timestamp;
import java.util.List;

import org.omsf.store.dao.MenuRepository;
import org.omsf.store.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	MenuRepository menuRepository;

	@Override
	public Menu getMenuByMenuNo(int menuNo) {
		Menu menu = menuRepository.getMenuByMenuNo(menuNo).get();
		return menu;
	}
	
	@Override
	public int createMenu(Menu menu) {
		menuRepository.createMenu(menu);
		return menu.getMenuNo();
	}

	@Override
	public void updateMenu(Menu menu) {
		menuRepository.updateMenu(menu);
	}

	@Override
	public void deleteMenu(int MenuNo) {
		menuRepository.deleteMenu(MenuNo);
	}

	@Override
	public void updateMenus(int storeNo, List<Menu> menues) {
		
		List<Menu> dbMenus = menuRepository.getMenusByStoreNo(storeNo);
		if (dbMenus.size() != menues.size()) {
			throw new IllegalArgumentException("메뉴의 수가 일치하지 않습니다.");
		}
		
		for (int i=0; i<menues.size(); i++) {
			Menu dbMenu = dbMenus.get(i);
			Menu menu = menues.get(i);
			dbMenu.setMenuName(menu.getMenuName());
			dbMenu.setPrice(menu.getPrice());
			menuRepository.updateMenu(dbMenu);
		}
	}

	// jaeeun
	@Override
	public void addMenu(String[] menuNames, long[] menuPrices, int storeNo) {
		// Menu 데이터 삽입
        for (int i = 0; i < menuNames.length; i++) {
            Menu menu = Menu.builder()
                    .storeNo(storeNo)
                    .menuName(menuNames[i])
                    .price(menuPrices[i])
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .modifiedAt(new Timestamp(System.currentTimeMillis()))
                    .build();

            menuRepository.insertMenu(menu);
	    }
	}
	
	@Override
	public List<Menu> getMenusByStoreNo(int storeNo) {
		return menuRepository.getMenusByStoreNo(storeNo);
	}
}
