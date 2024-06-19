package org.omsf.store.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.omsf.store.dao.StoreRepository;
import org.omsf.store.model.StoreVO;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
	
	private final StoreRepository storeRepository;
	
	@Override
	public List<StoreVO> getStoreByposition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoreVO getStoreByNo(int storeNo) {
		StoreVO store = storeRepository.getStoreByNo(storeNo).orElseThrow(() -> new NoSuchElementException("해당하는 상점을 찾을 수 없습니다"));
		return store;
	}

	@Override
	public void createStore(StoreVO store) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateStore(StoreVO store) {
		storeRepository.updateStore(store);
	}

	@Override
	public void deleteStore(int storeNo) {
		storeRepository.deleteStore(storeNo);
	}

	@Override
	public StoreVO updateTotalReview(StoreVO store) {
		int reviewCount = store.getTotalReview() + 1;
		store.setTotalReview(reviewCount++);
		return store;
	}

	@Override
	public StoreVO updateTotalRating(StoreVO store) {
		double totalRating = store.getTotalRating() + 1;
		// 업데이트 할 때 리뷰랑 평군점수 업데이트. 삭제할때는?
		return store;
	}

	@Override
	public StoreVO updateLikes(StoreVO store) {
		int reviewCount = store.getTotalReview() + 1;
		store.setTotalReview(reviewCount++);
		return store;
	}

}
