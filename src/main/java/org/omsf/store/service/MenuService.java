package org.omsf.store.service;

import java.util.List;

import org.omsf.store.model.MenuVO;


public interface MenuService {
	
	List<MenuVO> getMenusByStoreNo(int storeNo);
	void createMenu(MenuVO menu);
	void updateMenu(MenuVO menu);
	void deleteMenu(int MenuNo);   
}
