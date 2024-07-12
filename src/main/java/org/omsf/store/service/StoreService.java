package org.omsf.store.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.omsf.store.model.Photo;
import org.omsf.store.model.Store;
import org.omsf.store.model.StorePagination;
import org.springframework.web.multipart.MultipartFile;

/**
 * packageName    : org.omsf.store.service
 * fileName       : StoreService
 * author         : KIMCHANGHWAN
 * date           : 2024-07-05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-05      KIMCHANGHWAN       
 */

public interface StoreService {
	
//	List<Store> getStoreByposition(String position);
	List<Map<String, Object>> getStoreList(StorePagination page);
	
	int createStore(Store store);
	void updateStore(Store store);
	void updatePicture(Store store);
	void deleteStore(int storeNo);
	void updatePhotoOrder(List<Integer> photoOrder, int storeNo, String username);
	
	int UploadImage(ArrayList<MultipartFile> files, int storeNo, String username) throws IOException;
	void deleteImage(int PhotoNo);
  
	Photo getPhotoByPhotoNo(Integer photoNo);
	List<Photo> getStorePhotos(int storeNo);
	List<Photo> getStoreGallery(int storeNo);
	List<Photo> getUpdateStoreGallery(int storeNo, String username);
  
	// jaeeun
	List<Store> getPopularStores();
	Store getStoreByNo(int storeNo);
	List<Store> showStoreList(String keyword, String orderType, Double latitude, Double longitude, int offset, int limit);
	
	// yunbin
	void deleteStoreByUsername(String username);
	List<Store> getStoresByUsername(String name);

	// leejongseop
	List<Map<String, Object>> getStoresByPosition(String position);



}
