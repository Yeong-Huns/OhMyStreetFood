package org.omsf.store.service;

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
	public List<Menu> getMenusByStoreNo(int storeNo) {
		
		return menuRepository.getMenusByStoreNo(storeNo);
	}
	
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
	public void updateMenus(List<Menu> menus) {
		// 원래 메뉴 순서들을 받아서
		// 클라이언트에서 받은 메뉴의 순서들로 업데이트
		// 각각 하나씩 update하는 방식구현
	}
}
