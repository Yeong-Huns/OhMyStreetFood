package org.omsf.store.service;

import java.util.ArrayList;
import java.util.List;

import org.omsf.store.model.Store;
import org.springframework.web.multipart.MultipartFile;

public interface StoreService {
	
	List<Store> getStoreByposition();
	
	Store getStoreByNo(int storeNo);
	int createStore(Store store);
	void updateStore(Store store);
	void deleteStore(int storeNo);
	
	Store updateTotalReview(Store store);
	Store updateTotalRating(Store store);
	Store updateLikes(Store store);

	// jaeeun
	void addStore(Store store);
	
	void UploadImage(ArrayList<MultipartFile> files, int storeNo);
}
