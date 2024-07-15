package org.omsf.chatRoom.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * packageName    : org.omsf.chatRoom.model.chat
 * fileName       : MessageVO
 * author         : Yeong-Huns
 * date           : 2024-06-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-24        Yeong-Huns       최초 생성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MessageVO {
    private long messageNo;
    private String content;
    private String senderId;
    private long chatRoomNo;
    private boolean isReceived;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder
    public MessageVO(String content, String senderId, long chatRoomNo, boolean isReceived) {
        this.content = content;
        this.senderId = senderId;
        this.chatRoomNo = chatRoomNo;
    }
}
