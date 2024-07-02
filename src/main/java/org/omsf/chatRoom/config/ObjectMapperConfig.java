package org.omsf.chatRoom.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * packageName    : org.omsf.chatRoom.config
 * fileName       : ObjeectMapperConfig
 * author         : Yeong-Huns
 * date           : 2024-07-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-01        Yeong-Huns       최초 생성
 */
@Configuration
public class ObjectMapperConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
        mapper.registerModule(module);
        return mapper;
    }
}
