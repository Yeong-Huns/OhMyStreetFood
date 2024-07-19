package org.omsf.alarm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omsf.chatRoom.model.MessageVO;
import org.omsf.chatRoom.model.SubscribeRequest;
import org.omsf.chatRoom.service.ChatService;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

/**
 * packageName    : org.omsf.alarm.controller
 * fileName       : AlarmHandler
 * author         : Yeong-Huns
 * date           : 2024-07-15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-15        Yeong-Huns       최초 생성
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class AlarmHandler {
    private final RestTemplate restTemplate;
    private final ChatService chatService;

    public void sendRequestAlarm(String username, long storeNo, String httpMapping){
        log.info("username: {}", username);
        log.info("storeNo: {}", storeNo);
        handleSubChannelRequest(username, storeNo);
        long chatRoomNo = chatService.getChatRoomNoBySubscription(username, storeNo);
        handleOrderRequest(username, chatRoomNo, httpMapping);
    }


    private void handleSubChannelRequest(String username, long storeNo){
        restTemplate.postForObject("http://localhost:8080/rest/chat/subRequest",
                SubscribeRequest.builder()
                        .customerId(username)
                        .storeNo(storeNo)
                        .build(),
                Void.class);
    }

    private void handleOrderRequest(String username, long chatRoomNo, String httpMapping){
        String content = "<strong>[주문 요청]</strong> 새로운 주문이 있어요. <br> <a href=\"" + httpMapping + "\">내역 보기</a>";
        restTemplate.postForObject("http://localhost:8080/rest/chat/sendRequest",
                MessageVO.builder()
                        .senderId(username)
                        .chatRoomNo(chatRoomNo)
                        .content(content)
                        .build(),
                Void.class);
    }
}
