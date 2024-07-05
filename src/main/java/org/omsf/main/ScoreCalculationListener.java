package org.omsf.main;

import java.time.LocalDateTime;

import org.omsf.store.service.ViewCountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * packageName    : org.omsf.main
 * fileName       : ScoreCalculationListener
 * author         : KIMCHANGHWAN
 * date           : 2024-07-05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-05      KIMCHANGHWAN       
 */

@Component
@RequiredArgsConstructor
public class ScoreCalculationListener implements ApplicationListener<ContextRefreshedEvent> {
	
	private static final Logger log = LoggerFactory.getLogger(ScoreCalculationListener.class);
	private final ViewCountService viewCountService;
	private final RedisTemplate<String, Object> redisTemplate;
	
	private static final String LAST_CALCULATION_KEY = "last_score_calculation_time";
	
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		String lastCalculation = (String) redisTemplate.opsForValue().get(LAST_CALCULATION_KEY);
		if (lastCalculation == null || isCalculationNeeded(lastCalculation)) {
			viewCountService.calculateAllStoreScore();
			redisTemplate.opsForValue().set(LAST_CALCULATION_KEY, LocalDateTime.now().toString());
			log.info("인기 점포 가중치 계산");
		}
	}
	
	
	private boolean isCalculationNeeded(String lastCalculation) {
	    LocalDateTime lastCalc = LocalDateTime.parse(lastCalculation);
	    return LocalDateTime.now().minusDays(1).isAfter(lastCalc);
	}
}
