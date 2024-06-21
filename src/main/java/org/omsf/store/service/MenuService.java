package org.omsf.store.service;

import java.util.List;

import org.omsf.store.model.Menu;


public interface MenuService {
	
	List<Menu> getMenusByStoreNo(int storeNo);
	Menu getMenuByMenuNo(int menuNo);
	int createMenu(Menu menu);
	void updateMenu(Menu menu);
	void deleteMenu(int MenuNo);
	
	void updateMenus(int storeNo, List<Menu> menus);
}
