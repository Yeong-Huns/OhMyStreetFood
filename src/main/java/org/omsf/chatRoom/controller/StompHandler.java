package org.omsf.chatRoom.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.omsf.chatRoom.model.MessageResponse;
import org.omsf.chatRoom.model.MessageVO;
import org.omsf.chatRoom.model.SubscribeRequest;
import org.omsf.chatRoom.service.ChatService;
import org.omsf.chatRoom.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.TimeZone;

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
    private final ObjectMapper objectMapper;
    // 1 대 1 채팅방 개설 요청
    @MessageMapping("/chat/subRequest")
    public void subscribeToChatRoom(SubscribeRequest request){
        chatService.subscribeToChatRoom(request);
    }

    // 메세지 전송 요청
    @MessageMapping("/chat/sendRequest")
    public void handleSendMessage(MessageVO request) throws JsonProcessingException {
        MessageResponse response = messageService.handleSendMessage(request);
        log.info("🤕🤕🤕🤕🤕"+String.valueOf(response.getMessageVO()));
        log.info("😋JVM 기본 시간대: " + TimeZone.getDefault().getID());
        messagingTemplate.convertAndSend("/queue/chat/" + response.getSubscription(), response.getMessageVO());
    }

    @Getter
    @Setter
    public static class SendRequest{
        private String content;
        private String senderId;
        private String chatRoomNo;
    }
}
