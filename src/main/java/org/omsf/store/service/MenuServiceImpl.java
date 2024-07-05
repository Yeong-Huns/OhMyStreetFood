package org.omsf.store.service;

import java.sql.Timestamp;
import java.util.List;

import org.omsf.error.Exception.CustomBaseException;
import org.omsf.error.Exception.ErrorCode;
import org.omsf.store.dao.MenuRepository;
import org.omsf.store.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * packageName    : org.omsf.store.service
 * fileName       : MenuServiceImpl
 * author         : KIMCHANGHWAN
 * date           : 2024-07-05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-05      KIMCHANGHWAN       
 */

@Service
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	MenuRepository menuRepository;

	@Override
	public Menu getMenuByMenuNo(int menuNo) {
		Menu menu = menuRepository.getMenuByMenuNo(menuNo).orElseThrow(() -> new CustomBaseException(ErrorCode.RESOURCE_NOT_FOUND));
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
		menuRepository.deleteMenusByStoreNo(storeNo);
		
		for (Menu menu : menues) {
			menu.setStoreNo(storeNo);
			menuRepository.createMenu(menu);
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
		List<Menu> menus = menuRepository.getMenusByStoreNo(storeNo);
		return menus;
	}
}
