package org.omsf.review.service;

import java.util.List;

import org.omsf.review.dao.ReviewRepository;
import org.omsf.review.model.RequestReview;
import org.omsf.review.model.Review;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	
	@Override
	public void createReview(RequestReview review) {
		reviewRepository.createReview(review);
	}

	@Override
	public List<Review> getReviewListByStoreId(int storeNo) {
		return reviewRepository.getReviewListByStoreId(storeNo);
	}

	@Override
	public List<Review> getReviewListByUsername(String userName) {
		return reviewRepository.getReviewListByUsername(userName);
	}

	@Override
	public Review getReviewByReviewNo(int reviewNo) {
		return reviewRepository.getReviewByReviewNo(reviewNo);
	}

	@Override
	public void updateReview(int reviewNo, RequestReview review) {
		reviewRepository.updateReview(reviewNo, review);
	}

	@Override
	public void deleteReview(int reviewNo) {
		reviewRepository.deleteReview(reviewNo);
	}

}
