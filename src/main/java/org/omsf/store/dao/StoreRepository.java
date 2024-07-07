package org.omsf.store.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.omsf.store.model.Photo;
import org.omsf.store.model.Store;
import org.omsf.store.model.StorePagination;

/**
 * packageName    : org.omsf.store.dao
 * fileName       : StoreRepository
 * author         : KIMCHANGHWAN
 * date           : 2024-07-05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-05      KIMCHANGHWAN       
 */

public interface StoreRepository {
	     
	List<Store> selectAllStore();
	//List<Store> 
	//가게 정렬해서 가져오기 최신순, 인기순, 리뷰순, 거리순
	List<Store> getStoreList(StorePagination page);
	List<Store> getStoreByposition(double latitude, double longitude);
	void createStore(Store store);
	void updateStore(Store store);
	void updatePicture(Store store);
	void deleteStore(int storeNo);
	void updateTotalReviewAndRating(Store store);
	void updateLikes(Store store);
	void createPhoto(Photo photo);
	Optional<Photo> getPhotoByPhotoNo(int photoNo);
	List<Photo> getStorePhotos(int storeNo);
	List<Photo> getStoreGallery(Store store);
	void updatePhoto(Photo photo);
	void deletePhoto(int photoNo);
	
	// jaeeun
	List<Store> getPopularStores();
	Optional<Store> getStoreByNo(int storeNo);
	List<Store> showStoreList(Map<String, Object> params);

	// yunbin
	//String getStoreNameByStoreNo(int storeNo);
	void deleteStoreByUsername(String username);
	List<Store> getStoresByUsername(String username);
	
	// leejongseop
	List<Map<String, Object>> getStoresByPosition(String position);
}
