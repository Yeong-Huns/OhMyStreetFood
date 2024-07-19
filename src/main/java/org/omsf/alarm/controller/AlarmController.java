package org.omsf.alarm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omsf.chatRoom.model.MessageResponse;
import org.omsf.chatRoom.model.MessageVO;
import org.omsf.chatRoom.model.SubscribeRequest;
import org.omsf.chatRoom.service.ChatService;
import org.omsf.chatRoom.service.MessageService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : org.omsf.alarm.controller
 * fileName       : RestStompHandler
 * author         : Yeong-Huns
 * date           : 2024-07-15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-15        Yeong-Huns       최초 생성
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class AlarmController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;
    private final MessageService messageService;

    // 주문 알람용
    @PostMapping("/rest/chat/subRequest")
    public void handleChatSubscription(@RequestBody SubscribeRequest request) {
        log.info("handleChatSubscription : {}", request.getCustomerId());
        log.info("handleChatSubscription : {}", request.getStoreNo());
        chatService.subscribeToChatRoom(request);
    }

    // 주문 알람용 메세지 전송 요청
    @PostMapping("/rest/chat/sendRequest")
    public void restSendMessage(@RequestBody MessageVO request) throws JsonProcessingException {
        MessageResponse response = messageService.handleSendMessage(request);
        messagingTemplate.convertAndSend("/queue/chat/" + response.getSubscription(), response.getMessageVO());
    }



}
