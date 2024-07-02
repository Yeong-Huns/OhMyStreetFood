package org.omsf.chatRoom.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : org.omsf.chatRoom.controller
 * fileName       : SubscriptionController
 * author         : Yeong-Huns
 * date           : 2024-06-29
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-29        Yeong-Huns       최초 생성
 */
@RequiredArgsConstructor
@RestController
public class SubscriptionController {
    private final WebSocketEventListener webSocketEventListener;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/subscribe")
    public void subscribeToNewChannel(SubscriptionRequest request) {
        if (webSocketEventListener.isActive(request.getTarget())) {
            messagingTemplate.convertAndSend("/topic/chat/" + request.getTarget(), request.getChannel());
            messagingTemplate.convertAndSend("/topic/chat/" + request.getRequestingUser(), request.getChannel());
        }
    }

    @MessageMapping("/chat/subscribeWithOut")
    public void subscribeToNewChannelWithOutLoginCheck(SubscriptionRequest request) {
        messagingTemplate.convertAndSend("/topic/chat/" + request.getTarget(), request.getChannel());
        messagingTemplate.convertAndSend("/topic/chat/" + request.getRequestingUser(), request.getChannel());
    }


    @GetMapping("/chat/isActive")
    public boolean isActive(@RequestParam String target) {
        return webSocketEventListener.isActive(target);
    }

    @Setter
    @Getter
    public static class SubscriptionRequest {
        private String requestingUser;
        private String target;
        private String channel;
    }
}
