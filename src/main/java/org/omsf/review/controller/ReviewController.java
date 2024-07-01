package org.omsf.review.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.omsf.error.Exception.CustomBaseException;
import org.omsf.error.Exception.ErrorCode;
import org.omsf.review.model.RequestReview;
import org.omsf.review.model.Review;
import org.omsf.review.service.ReviewService;
import org.omsf.review.validator.UserValidator;
import org.omsf.store.service.StoreService;
import org.springframework.security.access.prepost.PreAuthorize;
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

	private final UserValidator userValidator;
	
	// 리뷰 등록
	@PostMapping("insert")
	public String reviewInsert(Principal principal, @Valid @ModelAttribute("requestReview") RequestReview review,
										Errors errors,
										Model model,
										RedirectAttributes redirectAttributes) {
		log.info("RequestReview content : {}", review.toString());
		userValidator.validate(principal, errors);
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
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("{reviewNo}")
	public String getReviewDetail(@PathVariable("reviewNo") int reviewNo,@RequestParam(value="requestPage", required = false) String requestPage,
									Model model) {
		Review review = reviewServ.getReviewByReviewNo(reviewNo);
		model.addAttribute("review", review);
		model.addAttribute("reviewNo", reviewNo);
		model.addAttribute("memberUsername", review.getMemberUsername());
		
		if(requestPage != null && !requestPage.isEmpty())
			model.addAttribute("requestPage", requestPage);
		return "review/reviewDetail";
	}


	// 리뷰 커맨드패턴 처리
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("command")
	public String reviewCommand(Principal principal, @ModelAttribute("requestReview") RequestReview review,
			@RequestParam("command") String command, @RequestParam("reviewNo") int reviewNo
			, @RequestParam(value="requestPage", required = false) String requestPage,
			Errors errors, RedirectAttributes redirectAttributes) {
		log.info("요청 커맨드 : {}", command);
		
		userValidator.validate(review, principal, errors);
		if (errors.hasErrors()) {
			redirectAttributes.addFlashAttribute("requestReview", review);
			redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
			log.info("error 배열 : {}", errors.getAllErrors().toString());
			return String.format("redirect:/review/%d", reviewNo);
		}
		
		if (command.equals("update")) {
			// update 요청
			reviewServ.updateReview(reviewNo, review);
			return String.format("redirect:/review/%d", reviewNo);
		} else {
			// delete 요청
			if (!command.equals("delete")) {
				// delete요청이 아닐 때
				throw new CustomBaseException(ErrorCode.NOT_ALLOWED_REQUEST);
			}
			reviewServ.deleteReview(reviewNo);
			if(requestPage != null && !requestPage.isEmpty())
				return "redirect:/mypage";
			else
				return String.format("redirect:/review/list/%d", review.getStoreStoreNo());
		}
	}

}
