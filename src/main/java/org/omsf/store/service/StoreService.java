package org.omsf.store.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.omsf.store.model.Store;
import org.omsf.store.model.StorePagination;
import org.springframework.web.multipart.MultipartFile;

public interface StoreService {

//	List<Store> getStoreByposition(String position);
	List<Store> getStoreList(StorePagination page);
	

	int createStore(Store store);
	void updateStore(Store store);
	void deleteStore(int storeNo);
	
	Store updateTotalReview(Store store);
	Store updateTotalRating(Store store);
	Store updateLikes(Store store);
	
	int UploadImage(ArrayList<MultipartFile> files, int storeNo) throws IOException;
	
	// jaeeun
	List<Store> getAllStores();
	Store getStoreByNo(int storeNo);
	
	// yunbin
	String getStoreNameByStoreNo(int storeNo);
}
