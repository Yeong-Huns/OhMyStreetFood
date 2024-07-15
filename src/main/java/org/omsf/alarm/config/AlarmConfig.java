package org.omsf.alarm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * packageName    : org.omsf.store.config
 * fileName       : Appconfig
 * author         : Yeong-Huns
 * date           : 2024-07-15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-15        Yeong-Huns       최초 생성
 */
@Configuration
public class AlarmConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}