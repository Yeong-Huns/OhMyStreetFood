package org.omsf.review.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.omsf.member.dao.GeneralMemberRepository;
import org.omsf.member.model.GeneralMember;
import org.omsf.review.dao.ReviewRepository;
import org.omsf.review.model.RequestReview;
import org.omsf.review.model.Review;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("reviewServ")
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewDao;
	private final GeneralMemberRepository generalMemberRepository;
	
	@Override
	public void createReview(RequestReview review) {
		reviewDao.createReview(review);
	}

	@Override
	public List<Review> getReviewListByStoreId(int storeNo) {
		return reviewDao.getReviewListByStoreId(storeNo);
	}

	@Override
	public List<Review> getReviewListByUsername(String username) {
		return reviewDao.getReviewListByUsername(username);
	}

	@Override
	public Review getReviewByReviewNo(int reviewNo) {
		return reviewDao.getReviewByReviewNo(reviewNo);
	}

	@Override
	public void updateReview(int reviewNo, RequestReview review) {
		reviewDao.updateReview(reviewNo, review);
	}

	@Override
	public void deleteReview(int reviewNo) {
		reviewDao.deleteReview(reviewNo);
	}

	@Override
	public Map<Review, String> getReviewListOnStore(int storeNo) {
		List<Review> reviews = reviewDao.getReviewListOnStore(storeNo);
		Map<Review, String> reviewsWithNickName = new LinkedHashMap<>();
		for(Review review:reviews) {
			
			Optional<GeneralMember> _generalMember = generalMemberRepository.findByUsername(review.getMemberUsername());
			if(_generalMember.isPresent()) {
				GeneralMember generalMember = _generalMember.get();
				reviewsWithNickName.put(review, generalMember.getNickName());
			} else {
				reviewsWithNickName.put(review, null);
			}	
		}
		return reviewsWithNickName;
	}

	@Override
	public List<Map<String, Object>> getJSONReviewListByStoreId(int storeNo, int page) {
		return reviewDao.getJSONReviewListByStoreId(storeNo, page);
	}

	// yunbin
	@Override
	public List<Review> getReviewsByUsername(String username) {
		return reviewDao.getReviewsByUsername(username);
	}

	@Override
	public int isWriter(int storeNo, String memberUsername) {
		return reviewDao.isWriter(storeNo, memberUsername);
	}

}
