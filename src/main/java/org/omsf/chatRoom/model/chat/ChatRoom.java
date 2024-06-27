package org.omsf.chatRoom.model.chat;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * packageName    : org.omsf.chatRoom.model
 * fileName       : ChatRoom
 * author         : Yeong-Huns
 * date           : 2024-06-20
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-20        Yeong-Huns       최초 생성
 */
@Getter
@Setter
public class ChatRoom {
    private String roomId;
    private String roomName;



    public static ChatRoom create(String name) {
        ChatRoom room = new ChatRoom();
        room.roomId = UUID.randomUUID().toString();
        room.roomName = name;
        return room;
    }
}
