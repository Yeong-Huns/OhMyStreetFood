package org.omsf.chatRoom.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : org.omsf.chatRoom.config
 * fileName       : TestJacksonConfig
 * author         : Yeong-Huns
 * date           : 2024-07-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-01        Yeong-Huns       최초 생성
 */
@Configuration
public class TestJacksonConfig {

    @Bean
    public ObjectMapper testObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}