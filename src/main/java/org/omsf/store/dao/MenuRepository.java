package org.omsf.store.dao;

import java.util.List;
import java.util.Optional;

import org.omsf.store.model.Menu;

/**
 * packageName    : org.omsf.store.dao
 * fileName       : MenuRepository
 * author         : KIMCHANGHWAN
 * date           : 2024-07-05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-05      KIMCHANGHWAN       
 */

public interface MenuRepository {
	
	List<Menu> getMenusByStoreNo(int storeNo);
	void createMenu(Menu menu);
	void updateMenu(Menu menu);
	void deleteMenu(int MenuNo);
	void deleteMenusByStoreNo(int StoreNo);
	// jaeeun
	void insertMenu(Menu menu);
	Optional<Menu> getMenuByMenuNo(int menuNo);	
}
