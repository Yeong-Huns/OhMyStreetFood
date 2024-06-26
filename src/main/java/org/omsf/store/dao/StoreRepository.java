package org.omsf.store.dao;

import java.util.List;
import java.util.Optional;

import org.omsf.store.model.Photo;
import org.omsf.store.model.Store;
import org.omsf.store.model.StorePagination;

public interface StoreRepository {
	     
	List<Store> selectAllStore();
	//List<Store> 
	//가게 정렬해서 가져오기 최신순, 인기순, 리뷰순, 거리순
	List<Store> getStoreList(StorePagination page);
	List<Store> getStoreByposition(double latitude, double longitude);
	void createStore(Store store);
	void updateStore(Store store);
	void deleteStore(int storeNo);
	
	void updateTotalReviewAndRating(Store store);
	void updateLikes(Store store);
	void createPhoto(Photo photo);
	Photo getPhotoByPhotoNo(int photoNo);
	List<Photo> getStorePhotos(Store store);
	void deletePhoto(int photoNo);
	
	// jaeeun
	List<Store> getAllStores();
	Optional<Store> getStoreByNo(int storeNo);
	
	// yunbin
	String getStoreNameByStoreNo(int storeNo);
	
	// leejongseop
	List<Store> getStoresByPosition(String position);
}
