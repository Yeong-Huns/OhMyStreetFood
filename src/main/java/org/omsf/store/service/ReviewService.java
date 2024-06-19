package org.omsf.store.service;

import java.util.List;

import org.omsf.store.model.ReviewVO;


public interface ReviewService {
	
	List<ReviewVO> getReviewsByStoreNo(int storeNo);
	int getReviewCountByStoreNo(int storeNo);
	
	void createReview(ReviewVO review);
	void updateReview(ReviewVO review);
	void deleteReview(int reviewNo);
}
