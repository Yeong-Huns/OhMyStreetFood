package org.omsf.chatRoom.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName    : org.omsf.chatRoom.model
 * fileName       : GetChatRoomNoBySubscriptionRequest
 * author         : Yeong-Huns
 * date           : 2024-07-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-02        Yeong-Huns       최초 생성
 */
@Getter
@Setter
public class GetChatRoomNoBySubscriptionRequest {
    private Long chatroomNo;
    private boolean ownerVisible;
    private boolean customerVisible;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
    private String target;
    private String lastMessage;
}
