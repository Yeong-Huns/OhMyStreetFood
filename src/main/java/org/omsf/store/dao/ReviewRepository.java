package org.omsf.store.dao;

import java.util.List;

import org.omsf.store.model.ReviewVO;

public interface ReviewRepository {
	
	List<ReviewVO> getReviewsByStoreNo(int storeNo);
	int getReviewCountByStoreNo(int storeNo);
	
	void createReview(ReviewVO review);
	void updateReview(ReviewVO review);
	void deleteReview(int reviewNo);
	
}
