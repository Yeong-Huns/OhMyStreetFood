package org.omsf.chatRoom.controller;

import org.omsf.chatRoom.model.Message;
import org.omsf.chatRoom.model.OutputMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * packageName    : org.omsf.chatRoom.controller
 * fileName       : WebSocketHandler
 * author         : Yeong-Huns
 * date           : 2024-06-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-18        Yeong-Huns       최초 생성
 */

public class WebSocketHandler extends TextWebSocketHandler {
    @MessageMapping("/chatRoomList")
    @SendTo("/sub/myChat")
    public OutputMessage greeting(Message message) {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getFrom(), message.getText(), time);
    }
}