package org.omsf.chatRoom.model;

import lombok.Getter;
import lombok.ToString;

/**
 * packageName    : org.omsf.chatRoom.model
 * fileName       : HandleSendMessageRequest
 * author         : Yeong-Huns
 * date           : 2024-06-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-27        Yeong-Huns       최초 생성
 */
@Getter
@ToString
public class HandleSendMessageResponse {
    private final String ownerId;
    private final String customerId;
    private final String content;
    private final String senderId;

    public HandleSendMessageResponse(String ownerId, String customerId, String content, String senderId) {
        this.ownerId = ownerId;
        this.customerId = customerId;
        this.content = content;
        this.senderId = senderId;
    }
}
