package org.omsf.main;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.omsf.store.service.ViewCountServiceImpl;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ViewCountServiceAspect {
	
	
	@After("execution(* org.omsf.store.service.LikeServiceImpl.*(..)) || execution(* org.omsf.store.service.ReviewServiceImpl.*(..))")
    public void markAsChanged() {
        ViewCountServiceImpl.markAsChanged();
    }
}
