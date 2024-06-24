package org.omsf.store.service;

import java.util.ArrayList;
import java.util.List;

import org.omsf.store.model.Store;
import org.springframework.web.multipart.MultipartFile;

public interface StoreService {

//	List<Store> getStoreByposition(String position);
	
	int createStore(Store store);
	void updateStore(Store store);
	void deleteStore(int storeNo);
	
	Store updateTotalReview(Store store);
	Store updateTotalRating(Store store);
	Store updateLikes(Store store);
	
	int UploadImage(ArrayList<MultipartFile> files, int storeNo);
	
	// jaeeun
	int addStore(Store store);
	List<Store> getAllStores();
	Store getStoreByNo(int storeNo);
	
	// yunbin
	String getStoreNameByStoreNo(int storeNo);
}
