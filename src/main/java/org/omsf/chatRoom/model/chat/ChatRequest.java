package org.omsf.chatRoom.model.chat;

import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : org.omsf.chatRoom.model
 * fileName       : ChatRoomMember
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
public class ChatRequest {
    private String channelName;
    private String ownerName;
    private String customerName;
    private Long chatRoomId;
}
