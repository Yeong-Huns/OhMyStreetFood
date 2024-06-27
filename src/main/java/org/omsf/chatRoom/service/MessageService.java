package org.omsf.chatRoom.service;

import org.omsf.chatRoom.model.MessageVO;
import org.omsf.chatRoom.model.MessageWithOwnerResponse;

import java.util.List;

/**
 * packageName    : org.omsf.chatRoom.service
 * fileName       : MessageService
 * author         : Yeong-Huns
 * date           : 2024-06-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-26        Yeong-Huns       최초 생성
 */
public interface MessageService {
    void handleSendMessage(MessageVO message);

    List<MessageWithOwnerResponse> findAllByChatRoomNo(long chatRoomNo);
    List<MessageWithOwnerResponse> findAllByCurrentUserAndStoreNo(String customerId, long storeNo);

    String findRoomAddressByChatRoomNo(long chatRoomNo);
}
