package org.omsf.store.dao;

import java.util.List;
import java.util.Optional;

import org.omsf.store.model.Photo;
import org.omsf.store.model.Store;

public interface StoreRepository {
	     
	List<Store> selectAllStore();

	
	// List<Store> getStoreByposition(@Param("position") String position);
	void createStore(Store store);
	void updateStore(Store store);
	void deleteStore(int storeNo);
	
	void updateTotalReviewAndRating(Store store);
	void updateLikes(Store store);
	void createPhoto(Photo photo);
	void deletePhoto(int PhotoNo);
	
	// jaeeun
	void insertStore(Store store);
	List<Store> getAllStores();
	Optional<Store> getStoreByNo(int storeNo);
	
	// yunbin
	String getStoreNameByStoreNo(int storeNo);
}
