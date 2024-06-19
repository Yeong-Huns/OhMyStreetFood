package org.omsf.store.service;

import java.util.List;

import org.omsf.store.model.StoreVO;

public interface StoreService {
	
	List<StoreVO> getStoreByposition();
	
	StoreVO getStoreByNo(int storeNo);
	void createStore(StoreVO store);
	void updateStore(StoreVO store);
	void deleteStore(int storeNo);
	
	StoreVO updateTotalReview(StoreVO store);
	StoreVO updateTotalRating(StoreVO store);
	StoreVO updateLikes(StoreVO store);
	
}
