package org.omsf.main;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.omsf.store.service.ViewCountService;
import org.omsf.store.service.ViewCountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ViewCountServiceAspect {
	
	@Autowired
	ViewCountService viewCountService;
	
	@After("execution(* org.omsf.store.service.LikeServiceImpl.*(..)) "
			+ "|| execution(* org.omsf.review.service.ReviewServiceImpl.createReview(..))"
			+ "|| execution(* org.omsf.review.service.ReviewServiceImpl.updateReview(..))"
			+ "|| execution(* org.omsf.review.service.ReviewServiceImpl.deleteReview(..))"
			+ "|| execution(* org.omsf.store.service.StoreServiceImpl.deleteStore(..))"
			)
    public void markAsChanged() {
        ViewCountServiceImpl.markAsChanged();
    }
	
	@Around("execution(* org.omsf.store.service.StoreServiceImpl.deleteStore(int)) && args(storeNo)")
    public void deleteStoreScore(ProceedingJoinPoint joinPoint, int storeNo) throws Throwable {
        joinPoint.proceed();
        viewCountService.removeStoreRankings(storeNo);
	 }  
}
