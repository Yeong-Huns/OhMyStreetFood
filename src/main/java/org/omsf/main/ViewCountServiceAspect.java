package org.omsf.main;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.omsf.store.service.ViewCountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import groovy.util.logging.Slf4j;

/**
 * packageName    : org.omsf.main
 * fileName       : ViewCountServiceAspect
 * author         : KIMCHANGHWAN
 * date           : 2024-07-05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-05      KIMCHANGHWAN       
 */

@Aspect
@Component
@Slf4j
public class ViewCountServiceAspect {
	
	@Autowired
	ViewCountService viewCountService;
	
	private static final Logger log = LoggerFactory.getLogger(ViewCountService.class);
	
	@After("execution(* org.omsf.store.service.LikeServiceImpl.*(..)) "
			+ "|| execution(* org.omsf.review.service.ReviewServiceImpl.createReview(..))"
			+ "|| execution(* org.omsf.review.service.ReviewServiceImpl.updateReview(..))"
			+ "|| execution(* org.omsf.review.service.ReviewServiceImpl.deleteReview(..))"
			+ "|| execution(* org.omsf.store.service.StoreServiceImpl.deleteStore(..))"
			)
    public void markAsChanged() {
        viewCountService.markAsChanged();
        log.debug("가게변경감지. 1분뒤 캐시에 재저장합니다.");
    }
	
	@Around("execution(* org.omsf.store.service.StoreServiceImpl.deleteStore(int)) && args(storeNo)")
    public void deleteStoreScore(ProceedingJoinPoint joinPoint, int storeNo) throws Throwable {
        joinPoint.proceed();
        viewCountService.removeStoreRankings(storeNo);
        log.debug("가게 삭제감지. 점포 순위를 재설정합니다.");
	 }  
}
