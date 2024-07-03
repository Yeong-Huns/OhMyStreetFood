package org.omsf.review.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.omsf.review.model.RequestReview;
import org.omsf.review.model.Review;

public interface ReviewService {

	// 리뷰 등록
	void createReview(RequestReview review);
	
	// 가게별 리뷰 목록 가져오기
	List<Review> getReviewListByStoreId(int storeNo);
	
	// 마이페이지 리뷰 목록 가져오기
	List<Review> getReviewListByUsername(String userName);
	
	// 리뷰 상세글 가져오기
	Review getReviewByReviewNo(int reviewNo);
	
	// 리뷰 수정
	void updateReview(int reviewNo, RequestReview review);
	
	// 리뷰 삭제
	void deleteReview(int reviewNo);
	
	// 가게별 최근 리뷰 5개 가져오기
	Map<Review, String> getReviewListOnStore(int storeNo);
	
	// 가게별 리뷰 목록 가져오기 - 무한 스크롤
	List<Review> getJSONReviewListByStoreId(@Param("storeNo") int storeNo, @Param("page") int page);

	// yunbin
	List<Review> getReviewsByUsername(String username);
	
	// 가게번호와 유저명(principal)에 근거하여 진짜 그 리뷰 작성자인지 확인
	int isWriter(@Param("storeNo") int storeNo, @Param("memberUsername") String memberUsername);
	
}
