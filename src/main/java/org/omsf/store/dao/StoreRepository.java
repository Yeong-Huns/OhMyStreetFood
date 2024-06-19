package org.omsf.store.dao;

import java.util.List;
import java.util.Optional;

import org.omsf.store.model.Store;

public interface StoreRepository {
	     
	List<Store> selectAllStore();
	List<Store> getStoreByposition();
	
	Optional<Store> getStoreByNo(int storeNo);
	void createStore();
//	void createStore(Store store);
	void updateStore(Store store);
	void deleteStore(int storeNo);
	
	void updateTotalReview(Store store);
	void updateTotalRating(Store store);
	void updateLikes(Store store);
}
