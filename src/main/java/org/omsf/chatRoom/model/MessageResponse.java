package org.omsf.chatRoom.model;

import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : org.omsf.chatRoom.model
 * fileName       : MessageResponse
 * author         : Yeong-Huns
 * date           : 2024-07-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-01        Yeong-Huns       최초 생성
 */
@Getter
@Setter
public class MessageResponse {
    MessageVO messageVO;
    String subscription;

    public MessageResponse() {
    }

    public MessageResponse(MessageVO messageVO, String subscription) {
        this.messageVO = messageVO;
        this.subscription = subscription;
    }

    public static MessageResponse of(MessageVO messageVO, String subscription) {
        return new MessageResponse(messageVO, subscription);
    }
}
