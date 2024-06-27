package org.omsf.chatRoom.model;

import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : org.omsf.chatRoom.model
 * fileName       : SubscribeRequest
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
public class SubscribeRequest {
    private String customerId;
    private long storeNo;

    public String getCombinedId(){
        return customerId + storeNo;
    }
}
