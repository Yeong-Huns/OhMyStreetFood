package org.omsf.review.controller;

import java.util.List;

import javax.validation.Valid;

import org.omsf.review.model.RequestReview;
import org.omsf.review.model.Review;
import org.omsf.review.service.ReviewService;
import org.omsf.store.model.Store;
import org.omsf.store.service.StoreService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/review")
public class ReviewController {

	private final ReviewService reviewServ;
	private final StoreService storeService;
	
	@GetMapping("review")
	public String testReview(Model model) {
		RequestReview review = new RequestReview();
		review.setStoreStoreNo(20);
		review.setMemberUsername("test10@naver.com");
		model.addAttribute("requestReview", review);
		return "test/createReview";
	}
	
	// 리뷰 등록
	@PostMapping("insert")
	public String reviewInsert(@Valid @ModelAttribute("requestReview") RequestReview review,
										Errors errors,
										Model model,
										RedirectAttributes redirectAttributes) {
		log.info("RequestReview content : {}", review.toString());
		
		if(errors.hasErrors()) {
			redirectAttributes.addFlashAttribute("modalOn", true);
			redirectAttributes.addFlashAttribute("requestReview", review);
			redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
			log.info("error 배열 : {}", errors.getAllErrors().toString());
			return String.format("redirect:/store/%d", review.getStoreStoreNo());
		}
		reviewServ.createReview(review);
		return String.format("redirect:/store/%d", review.getStoreStoreNo());
	}
	
	// 리뷰 목록
	@GetMapping("list/{storeId}")
	public String reviewList(@PathVariable("storeId") int storeId, Model model) {
		model.addAttribute("reviews", reviewServ.getJSONReviewListByStoreId(storeId, 1));
		model.addAttribute("store", storeService.getStoreByNo(storeId));
		return "review/reviewList";
	}
	
	// 무한 스크롤 응답
	@GetMapping("api/{storeId}")
	@ResponseBody
	public List<Review> getReviewList(@PathVariable("storeId") int storeId, 
										@RequestParam(value = "page", defaultValue = "2") int page, Model model) {
		List<Review> reviews = reviewServ.getJSONReviewListByStoreId(storeId, page);
		return reviews;
	}
	
	
	// 리뷰 상세 페이지
	@GetMapping("{reviewNo}")
	public String getReviewDetail(@PathVariable("reviewNo") int reviewNo,
									Model model) {
		model.addAttribute("review", reviewServ.getReviewByReviewNo(reviewNo));
		return "review/reviewDetail";
	}
	
	
}
