package org.omsf.chatRoom.dao;

import org.apache.ibatis.annotations.Param;
import org.omsf.chatRoom.controller.StompHandler;
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

    //1. Channel 명으로 모든 Message 조회
    List<MessageVO> findAllMessageBySubscription(@Param("customer") String subscription, @Param("storeNo") String storeNo);

    //2. 수신처리
    void updateMessageStatus(@Param("messageNo") long messageNo);

    //3. 메세지 저장
    void save(StompHandler.SendRequest request);

    //4. chatRoomNo로 모든 Message 조회
    List<MessageVO> findAllMessageByChatRoomNo(@Param("chatRoomNo") long chatRoomNo);



    HandleSendMessageResponse handleSendMessage(MessageVO message);
    List<MessageWithOwnerResponse> findAllByChatRoomNo(@Param("chatRoomNo") Long chatRoomNo);
}
