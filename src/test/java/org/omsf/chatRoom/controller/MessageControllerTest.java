package org.omsf.chatRoom.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.omsf.chatRoom.model.MessageVO;
import org.omsf.error.Exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * packageName    : org.omsf.chatRoom.controller
 * fileName       : MessageControllerTest
 * author         : Yeong-Huns
 * date           : 2024-07-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-01        Yeong-Huns       최초 생성
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/spring/root-context.xml",
        "file:src/main/webapp/WEB-INF/spring/servlet-context.xml",
        "file:src/main/webapp/WEB-INF/spring/security-context.xml"
})
class MessageControllerTest {
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @DisplayName("구독주소로 모든 메세지 조회에 성공한다.")
    @Test
    void findAllMessageBySubscription() throws Exception {
        MvcResult result = mockMvc.perform(get("/chat/room")
                        .param("customer", "qwer@gmail.com")
                        .param("storeNo", "281")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        List<MessageVO> messages = objectMapper.readValue(content, new TypeReference<List<MessageVO>>() {});

        assertNotNull(messages);
        assertFalse(messages.isEmpty());
    }

    @DisplayName("메세지 기록이 없는 구독주소로 조회한다(ErrorCode.NOT_FOUND_MESSAGE)")
    @Test
    void findAllMessageBySubscriptionFail() throws Exception {
        MvcResult result = mockMvc.perform(get("/chat/room")
                        .param("customer", "qwer@gmail.com")
                        .param("storeNo", "8888")
                        .contentType("application/json"))
                .andExpect(status().isNotFound())
                .andReturn();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> responseMap = objectMapper.readValue(content, new TypeReference<Map<String, String>>() {});
        log.info("Response content: " + content);

        assertEquals(ErrorCode.NOT_FOUND_MESSAGE.getCode(), responseMap.get("code"));
        assertEquals(ErrorCode.NOT_FOUND_MESSAGE.getMessage(), responseMap.get("message"));
    }
}