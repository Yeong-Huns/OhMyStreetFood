package org.omsf.chatRoom.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
@ToString
@Getter
@Setter
public class MessageWithOwnerResponse {
    private long messageNo;
    private String content;
    private String senderId;
    private Long chatRoomNo;
    private boolean isReceived;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    private String owner; // 추가된 필드
}
