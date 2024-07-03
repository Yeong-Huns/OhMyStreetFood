package org.omsf.chatRoom.service;

import org.apache.ibatis.annotations.Param;
import org.omsf.chatRoom.controller.StompHandler;
import org.omsf.chatRoom.model.MessageResponse;
import org.omsf.chatRoom.model.MessageVO;
import org.omsf.chatRoom.model.MessageWithOwnerResponse;
import org.omsf.chatRoom.model.MessageWithProfile;

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

    //1. channel 명으로 모든 Message 조회
    List<MessageVO> findAllMessageBySubscription(String customer, String storeNo);
    //2. 수신처리
    void updateMessageStatus(long messageNo);
    //3. 메세지 저장
    MessageResponse handleSendMessage(MessageVO messageVo);
    //4. PK 로 메세지 찾기
    MessageVO getMessageById(long messageNo);
    //5. chatRoomNo로 모든 Message 조회
    List<MessageWithProfile> findAllMessageByChatRoomNo(long chatRoomNo);
    //6.



    List<MessageWithOwnerResponse> findAllByChatRoomNo(long chatRoomNo);
    List<MessageWithOwnerResponse> findAllByCurrentUserAndStoreNo(String customerId, long storeNo);

    String findRoomAddressByChatRoomNo(long chatRoomNo);
}
