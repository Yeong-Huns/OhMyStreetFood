package org.omsf.chatRoom.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
 * fileName       : ChatRoomControllerTest
 * author         : Yeong-Huns
 * date           : 2024-06-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-30        Yeong-Huns       최초 생성
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/spring/root-context.xml",
        "file:src/main/webapp/WEB-INF/spring/servlet-context.xml",
        "file:src/main/webapp/WEB-INF/spring/security-context.xml"
})
class ChatRoomControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @DisplayName("일반 회원 주소 조회에 성공한다.")
    @Test
    void getAddressGeneral() throws Exception {
        MvcResult result = mockMvc.perform(get("/chat/getAddress")
                        .param("username", "qwer@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("qwer@gmail.com"));
    }

    @DisplayName("등록된 가게가 없는 사장 조회에 실패(ErrorCode.NOT_FOUND_STORE)")
    @Test
    void _getAddressOwner() throws Exception {
        MvcResult result = mockMvc.perform(get("/chat/getAddress")
                        .param("username", "Owner900@naver.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> responseMap = objectMapper.readValue(content, new TypeReference<Map<String, String>>() {});
        log.info("Response content: " + content);

        assertEquals(ErrorCode.NOT_FOUND_STORE.getCode(), responseMap.get("code"));
        assertEquals(ErrorCode.NOT_FOUND_STORE.getMessage(), responseMap.get("message"));
    }


    @DisplayName("존재하지 않는 회원 조회(ErrorCode.NOT_FOUND_USER")
    @Test
    void getAddressNotFound() throws Exception {
        MvcResult result = mockMvc.perform(get("/chat/getAddress")
                        .param("username", "notfound@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> responseMap = objectMapper.readValue(content, new TypeReference<Map<String, String>>() {});
        log.info("Response content: " + content);

        assertEquals(ErrorCode.NOT_FOUND_USER.getCode(), responseMap.get("code"));
        assertEquals(ErrorCode.NOT_FOUND_USER.getMessage(), responseMap.get("message"));
    }

    @DisplayName("사장 주소 조회에 성공한다.")
    @Test
    void getAddressOwner() throws Exception {
        MvcResult result = mockMvc.perform(get("/chat/getAddress")
                        .param("username", "redjoun@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        List<String> addresses = new ObjectMapper().readValue(content, new TypeReference<List<String>>() {});
        assertFalse(addresses.isEmpty(), "빈 리스트입니다.");
    }

    @DisplayName("일반 회원 구독 리스트 조회에 성공한다.")
    @Test
    void getUserSubscriptions() throws Exception {
        MvcResult result = mockMvc.perform(get("/chat/subscriptions")
                        .param("address", "qwer@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("qwer@gmail.com281"));
        assertTrue(content.contains("qwer@gmail.com156"));
    }
}
