package org.omsf.chatRoom.dao;

import org.omsf.chatRoom.model.HandleSendMessageResponse;
import org.omsf.chatRoom.model.MessageVO;
import org.omsf.chatRoom.model.MessageWithOwnerResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName    : org.omsf.chatRoom.dao
 * fileName       : MessageRepository
 * author         : Yeong-Huns
 * date           : 2024-06-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-26        Yeong-Huns       최초 생성
 */
@Repository
public interface MessageRepository {
    void save(MessageVO vo);


    HandleSendMessageResponse handleSendMessage(MessageVO message);
    List<MessageWithOwnerResponse> findAllByChatRoomNo(long chatRoomNo);
}
