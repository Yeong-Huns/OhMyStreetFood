package org.omsf.store.dao;

import java.util.List;

import org.omsf.store.model.Review;

public interface ReviewRepository {
	
	List<Review> getReviewsByStoreNo(int storeNo);
	int getReviewCountByStoreNo(int storeNo);
	
	void createReview(Review review);
	void updateReview(Review review);
	void deleteReview(int reviewNo);
	
}
