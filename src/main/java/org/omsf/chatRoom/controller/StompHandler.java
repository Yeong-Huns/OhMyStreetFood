package org.omsf.chatRoom.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.omsf.chatRoom.model.MessageVO;
import org.omsf.chatRoom.model.SubscribeRequest;
import org.omsf.chatRoom.service.ChatService;
import org.omsf.chatRoom.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : org.omsf.chatRoom.controller
 * fileName       : MessageController
 * author         : Yeong-Huns
 * date           : 2024-06-21
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-21        Yeong-Huns       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class StompHandler {
    private static final Logger log = LoggerFactory.getLogger(StompHandler.class);
    private final ChatService chatService;
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;
    // 1 대 1 채팅방 개설 요청
    @MessageMapping("/chat/subRequest")
    public void subscribeToChatRoom(SubscribeRequest request){
        chatService.subscribeToChatRoom(request);
    }

    // 메세지 전송 요청
    @MessageMapping("/chat/sendRequest")
    public void handleSendMessage(SendRequest request) {
        messageService.handleSendMessage(request);
        messagingTemplate.convertAndSend("/queue/chat/"+request.getChannel(), request.content);
    }

    @Getter
    @Setter
    public static class SendRequest{
        private String requestingUser;
        private String content;
        private String channel;
        private String customer;
        private long storeNo;
    }
}
