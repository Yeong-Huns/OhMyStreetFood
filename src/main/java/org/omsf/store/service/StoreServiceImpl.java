package org.omsf.store.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.omsf.store.dao.StoreRepository;
import org.omsf.store.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
	
	@Autowired
	private final StoreRepository storeRepository;
	
	@Override
	public List<Store> getStoreByposition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Store getStoreByNo(int storeNo) {
		Store store = storeRepository.getStoreByNo(storeNo).orElseThrow(() -> new NoSuchElementException("해당하는 상점을 찾을 수 없습니다"));
		return store;
	}

	@Override
	public void createStore(Store store) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateStore(Store store) {
		storeRepository.updateStore(store);
	}

	@Override
	public void deleteStore(int storeNo) {
		storeRepository.deleteStore(storeNo);
	}

	@Override
	public Store updateTotalReview(Store store) {
		int reviewCount = store.getTotalReview() + 1;
		store.setTotalReview(reviewCount++);
		return store;
	}

	@Override
	public Store updateTotalRating(Store store) {
		double totalRating = store.getTotalRating() + 1;
		// 업데이트 할 때 리뷰랑 평군점수 업데이트. 삭제할때는?
		return store;
	}

	@Override
	public Store updateLikes(Store store) {
		int reviewCount = store.getTotalReview() + 1;
		store.setTotalReview(reviewCount++);
		return store;
	}

	
	// jaeeun
	@Override
	public void addStore(Store store) {
		storeRepository.insertStore(store);
	}

}
