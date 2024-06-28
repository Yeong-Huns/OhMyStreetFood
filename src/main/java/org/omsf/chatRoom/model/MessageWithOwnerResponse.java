package org.omsf.chatRoom.model;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * packageName    : org.omsf.chatRoom.model
 * fileName       : MessageWithOwnerResponse
 * author         : Yeong-Huns
 * date           : 2024-06-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-27        Yeong-Huns       최초 생성
 */
@Getter
public class MessageWithOwnerResponse {
    private final String content;
    private final String senderId;
    private final Long chatRoomNo;
    private final boolean isReceived;
    private final LocalDateTime createdAt;
    private final String owner; // 추가된 필드

    public MessageWithOwnerResponse(String content, String senderId, Long chatRoomNo, boolean isReceived, LocalDateTime createdAt, String owner) {
        this.content = content;
        this.senderId = senderId;
        this.chatRoomNo = chatRoomNo;
        this.isReceived = isReceived;
        this.createdAt = createdAt;
        this.owner = owner;
    }
}
