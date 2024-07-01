package org.omsf.chatRoom.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omsf.chatRoom.service.ChatService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * packageName    : org.omsf.chatRoom.controller
 * fileName       : WebSocketEventListener
 * author         : Yeong-Huns
 * date           : 2024-06-29
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-29        Yeong-Huns       최초 생성
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
    private final ConcurrentHashMap<String, Boolean> activeUsers = new ConcurrentHashMap<String, Boolean>();
    private final ChatService chatService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = Objects.requireNonNull(headerAccessor.getUser()).getName();
        chatService.getUserAddress(username).forEach(address->{
            log.info("접속 유저 감지 : {}", username);
            activeUsers.put(address, Boolean.TRUE);
        });
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = Objects.requireNonNull(headerAccessor.getUser()).getName();
        chatService.getUserAddress(username).forEach(address->{
            log.info("접속 해제 감지 : {}", username);
            activeUsers.remove(address);
        });
    }

    public boolean isActive(String target) {
        return activeUsers.getOrDefault(target, Boolean.FALSE);
    }

}
