package org.omsf.store.dao;

import java.util.List;
import java.util.Optional;

import org.omsf.store.model.StoreVO;

public interface StoreRepository {
	     
	List<StoreVO> selectAllStore();
	List<StoreVO> getStoreByposition();
	
	Optional<StoreVO> getStoreByNo(int storeNo);
	void createStore();
//	void createStore(StoreVO store);
	void updateStore(StoreVO store);
	void deleteStore(int storeNo);
	
	void updateTotalReview(StoreVO store);
	void updateTotalRating(StoreVO store);
	void updateLikes(StoreVO store);
}
