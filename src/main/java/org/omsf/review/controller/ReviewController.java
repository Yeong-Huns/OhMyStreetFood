package org.omsf.review.controller;

import javax.validation.Valid;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/review")
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
	
	@PostMapping("insert")
	public String testReviewInsert(@Valid @ModelAttribute("requestReview") RequestReview review,
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
	
	@GetMapping("list/{storeId}")
	public String testReviewList(@PathVariable("storeId") int storeId, Model model) {
//		model.addAttribute("list", reviewServ.getReviewListByStoreId(storeId));
		model.addAttribute("list", reviewServ.getReviewListOnStore(storeId));
		return "test/getReviewListTest";
	}
}
