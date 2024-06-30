package org.omsf.review.validator;

import java.security.Principal;

import javax.validation.ValidationException;

import org.omsf.review.model.RequestReview;
import org.omsf.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

	@Autowired
	ReviewService reviewServ;

	@Override
	public boolean supports(Class<?> clazz) {
		 return RequestReview.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RequestReview review = (RequestReview) target;
	}

	public void validate(RequestReview review, Principal principal, Errors errors) {
		// Principal 정보를 사용한 검증 로직
		if (principal == null) {
			errors.rejectValue("memberUsername", "invalid.user", "로그인 후 이용가능합니다.");
		} else if (reviewServ.isWriter(review.getStoreStoreNo(), principal.getName()) < 1) {
			errors.rejectValue("memberUsername", "invalid.user", "유효하지 않은 접근입니다.");
		}
	}
	
	public void validate(Principal principal, Errors errors) {
		if(principal == null) {
			errors.rejectValue("memberUsername", "invalid.user", "로그인 후 이용가능합니다.");
		}
	}
	
}
