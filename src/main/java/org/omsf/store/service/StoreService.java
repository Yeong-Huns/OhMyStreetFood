package org.omsf.store.service;

import java.util.List;

import org.omsf.store.model.Store;

public interface StoreService {
	
	List<Store> getStoreByposition();
	
	Store getStoreByNo(int storeNo);
	void createStore(Store store);
	void updateStore(Store store);
	void deleteStore(int storeNo);
	
	Store updateTotalReview(Store store);
	Store updateTotalRating(Store store);
	Store updateLikes(Store store);
	
}
