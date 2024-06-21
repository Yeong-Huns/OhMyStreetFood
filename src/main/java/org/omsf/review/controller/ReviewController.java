package org.omsf.review.controller;

import org.omsf.review.model.RequestReview;
import org.omsf.review.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/test")
public class ReviewController {

	private final ReviewService reviewServ;
	
	@GetMapping("review")
	public String testReview(Model model) {
		RequestReview review = new RequestReview();
		review.setStoreStoreNo(54);
		review.setMemberUsername("owner1@naver.com");
		model.addAttribute("requestReview", review);
		return "test/createReview";
	}
	
	@PostMapping("review")
	public String testReviewInsert(@ModelAttribute("RequestReview") RequestReview review,
										Errors errors,
										Model model) {
		log.info("RequestReview content : {}", review.toString());
		
		if(errors.hasErrors()) {
			return "test/createReview";
		}
		reviewServ.createReview(review);
		return "redirect:/test/review";
	}
	
	// 가게별 리뷰
	@GetMapping("list/{storeId}")
	public String testReviewList(@PathVariable("storeId") int storeId, Model model) {
//		model.addAttribute("list", reviewServ.getReviewListByStoreId(storeId));
		model.addAttribute("list", reviewServ.getReviewListOnStore(storeId));
		return "test/getReviewListTest";
	}
	
	// username별 리뷰
	@GetMapping("list")
	public String testReviewListByUsername(Model model) {
		String username = "owner1@naver.com";
		model.addAttribute("list", reviewServ.getReviewListByUsername(username));
		return "test/getReviewListTest";
	}
	
	// 리뷰 상세화면
	@GetMapping("review/{reviewNo}")
	public String testReviewDetail(@PathVariable("reviewNo") int reviewNo, Model model) {
		model.addAttribute("review", reviewServ.getReviewByReviewNo(reviewNo));
		return "test/reviewDetail";
	}
	
	// 리뷰 수정화면
	@GetMapping("review/{reviewNo}/update")
	public String testReviewUpdate(@PathVariable("reviewNo") int reviewNo, Model model) {
		model.addAttribute("requestReview", reviewServ.getReviewByReviewNo(reviewNo));
		return "test/createReview";
	}
	
	@PostMapping("review/{reviewNo}/update")
	public String testReviewUpdate(@ModelAttribute("RequestReview") RequestReview review,
									@PathVariable("reviewNo") int reviewNo, 
									Errors errors,
									Model model) {
		log.info("RequestReview content : {}", review.toString());
		
		if(errors.hasErrors()) {
			return "test/createReview";
		}
		reviewServ.updateReview(reviewNo, review);
		return String.format("redirect:/test/review/%d", reviewNo);
	}
	
	// 리뷰 삭제
	@GetMapping("review/{reviewNo}/delete")
	public String testReviewDelete(@PathVariable("reviewNo") int reviewNo, Model model) {
		reviewServ.deleteReview(reviewNo);
		return "redirect:/test/review";
	}
	
}
