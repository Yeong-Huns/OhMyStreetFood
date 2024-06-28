package org.omsf.store.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.omsf.store.model.Photo;
import org.omsf.store.model.Store;
import org.omsf.store.model.StorePagination;
import org.springframework.web.multipart.MultipartFile;

public interface StoreService {

//	List<Store> getStoreByposition(String position);
	List<Map<String, Object>> getStoreList(StorePagination page);
	

	int createStore(Store store);
	void updateStore(Store store);
	void updatePicture(Store store);
	void deleteStore(int storeNo);
	void updatePhotoOrder(List<Integer> photoOrder, int storeNo, String username);
	
	Store updateTotalReview(Store store);
	Store updateTotalRating(Store store);
	Store updateLikes(Store store);
	
	int UploadImage(ArrayList<MultipartFile> files, int storeNo, String username) throws IOException;
	void deleteImage(int PhotoNo);
	
	// jaeeun
	List<Store> getAllStores();
	Store getStoreByNo(int storeNo);
	List<Store> searchByKeyword(String keyword, int offset, int limit);
	
	// yunbin
	String getStoreNameByStoreNo(int storeNo);

	// leejongseop
	List<Store> getStoresByPosition(String position);
	
	Photo getPhotoByPhotoNo(Integer photoNo);
	List<Photo> getStorePhotos(int storeNo);
	List<Photo> getStoreGallery(int storeNo);
	List<Photo> getUpdateStoreGallery(int storeNo, String username);
}
