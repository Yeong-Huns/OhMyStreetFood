package org.omsf.chatRoom.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName    : org.omsf.chatRoom.model
 * fileName       : MessageWithProfile
 * author         : Yeong-Huns
 * date           : 2024-07-03
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-03        Yeong-Huns       최초 생성
 */
@Getter
@Setter
public class MessageWithProfile {
    private long messageNo;
    private String content;
    private String senderId;
    private long chatRoomNo;
    private boolean isReceived;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
    private String storeName;
    private String nickName;
    private String picture;
}
