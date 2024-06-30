package org.omsf.chatRoom.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName    : org.omsf.chatRoom.model
 * fileName       : ChatRoomVO
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
public class ChatRoomVO {
    private long chatroomNo;
    private String customer;
    private long storeNo;
    private boolean ownerVisible;
    private boolean customerVisible;
    private LocalDateTime updatedAt;

    public String getChannel(){
        return customer+storeNo;
    }

}
