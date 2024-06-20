package org.omsf.review.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.omsf.review.model.RequestReview;
import org.omsf.review.model.Review;
import org.springframework.stereotype.Repository;

@Repository("reviewDao")
public interface ReviewRepository {

	// 리뷰 등록
	void createReview(RequestReview review);
	
	// 가게별 리뷰 목록 가져오기
	List<Review> getReviewListByStoreId(int storeNo);
	
	// 마이페이지 리뷰 목록 가져오기
	List<Review> getReviewListByUsername(String userName);
	
	// 리뷰 상세글 가져오기
	Review getReviewByReviewNo(int reviewNo);
	
	// 리뷰 수정
	void updateReview(@Param("reviewNo") int reviewNo, @Param("review") RequestReview review);
	
	// 리뷰 삭제
	void deleteReview(int reviewNo);
	
	
}
