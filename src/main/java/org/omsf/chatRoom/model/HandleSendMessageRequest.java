package org.omsf.chatRoom.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * packageName    : org.omsf.chatRoom.model
 * fileName       : HandleSendMessageRequest
 * author         : Yeong-Huns
 * date           : 2024-06-28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-28        Yeong-Huns       최초 생성
 */
@Getter
@Setter
@ToString
public class HandleSendMessageRequest {
    private String senderId;
    private String content;
    private long storeNo;
}
