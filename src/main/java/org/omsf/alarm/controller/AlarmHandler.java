package org.omsf.alarm.controller;

import lombok.RequiredArgsConstructor;
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
@Controller
@RequiredArgsConstructor
public class AlarmHandler {
    private final RestTemplate restTemplate;
    private final ChatService chatService;

    public void sendRequestAlarm(String username, long storeNo){
        handleSubChannelRequest(username, storeNo);
        long chatRoomNo = chatService.getChatRoomNoBySubscription(username, storeNo);
        handleOrderRequest(username, chatRoomNo);
    }


    private void handleSubChannelRequest(String username, long storeNo){
        restTemplate.postForObject("http://localhost:8080/rest/chat/subRequest",
                SubscribeRequest.builder()
                        .customerId(username)
                        .storeNo(storeNo)
                        .build(),
                Void.class);
    }

    private void handleOrderRequest(String username, long chatRoomNo){
        restTemplate.postForObject("http://localhost:8080/rest/chat/sendRequest",
                MessageVO.builder()
                        .senderId(username)
                        .chatRoomNo(chatRoomNo)
                        .content("[주문 요청] 가게 사장님께 주문 요청을 전달했습니다.")
                        .build(),
                Void.class);
    }
}
