package org.omsf.review.controller;

import org.omsf.review.model.RequestReview;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/test")
public class ReviewController {

	@GetMapping("review")
	public String testReview(Model model) {
		RequestReview review = new RequestReview();
		review.setStoreStoreNo(2);
		
		model.addAttribute("requestReview", review);
		return "test/createReview";
	}
}
