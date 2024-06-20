package org.omsf.review.controller;

import org.omsf.review.model.RequestReview;
import org.omsf.review.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		review.setStoreStoreNo(20);
		review.setMemberUsername("test10@naver.com");
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
}
