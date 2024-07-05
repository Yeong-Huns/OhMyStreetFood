package org.omsf.review.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.omsf.review.annotation.Rating;
/**
 * author         : leejongseop
 * description    :
 * ===========================================================
 *    AUTHOR            NOTE
 * -----------------------------------------------------------
 *  leejongseop       최초 생성
 */
public class RatingValidator implements ConstraintValidator<Rating, Integer>{

	@Override
	public void initialize(Rating constraintAnnotation) {
		
	}
	
	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		// 1점부터 5점까지만 허용
		if(value.intValue() >= 1 && value.intValue() <= 5) {
			return true;
		}
		return false;
	}

}
