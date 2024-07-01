package org.omsf.chatRoom.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.omsf.chatRoom.controller.StompHandler;
import org.omsf.chatRoom.dao.ChatRepository;
import org.omsf.chatRoom.dao.MessageRepository;
import org.omsf.chatRoom.model.MessageResponse;
import org.omsf.chatRoom.model.MessageVO;
import org.omsf.chatRoom.model.MessageWithOwnerResponse;
import org.omsf.error.Exception.ErrorCode;
import org.omsf.error.Exception.NotFoundException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
@Slf4j
@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final SimpMessagingTemplate messagingTemplate;

    // 1. channel 명으로 모든 Message 조회
    @Override
    public List<MessageVO> findAllMessageBySubscription(String customer, String storeNo){
        List<MessageVO> messages = messageRepository.findAllMessageBySubscription(customer, storeNo);
        if(messages.isEmpty()) throw new NotFoundException(ErrorCode.NOT_FOUND_MESSAGE);
        return messages;
    }

    // 2. 수신처리
    @Override
    public void updateMessageStatus(long messageNo) {
        messageRepository.updateMessageStatus(messageNo);
    }

    //3. 저장
    @Override
    public MessageResponse handleSendMessage(MessageVO messageVo) {
        messageRepository.saveMessage(messageVo);
        String subscription = chatRepository.getSubscriptionByChatRoomNo(messageVo.getChatRoomNo())
                .orElseThrow(()->new NotFoundException(ErrorCode.NOT_ALLOWED_REQUEST));
        return MessageResponse.of(messageVo, subscription); //정적 팩토리 메서드
    }

    //4. PK로 찾기
    @Override
    public MessageVO getMessageById(long messageNo) {
        return null;
    }
    //4. chatRoomNo로 모든 Message 조회
    @Override
    public List<MessageVO> findAllMessageByChatRoomNo(long chatRoomNo) {
        List<MessageVO> messages = messageRepository.findAllMessageByChatRoomNo(chatRoomNo);
        if(messages.isEmpty()) throw new NotFoundException(ErrorCode.NOT_FOUND_MESSAGE);
        return messages;
    }

    @Override
    public List<MessageWithOwnerResponse> findAllByChatRoomNo(long chatRoomNo) {
        return messageRepository.findAllByChatRoomNo(chatRoomNo);
    }

    @Override
    public List<MessageWithOwnerResponse> findAllByCurrentUserAndStoreNo(String customerId, long storeNo) {
        long chatRoomNo = chatRepository.findChatRoomNoByCurrentUserAndStoreNo(customerId, storeNo);
        List<MessageWithOwnerResponse> messages = messageRepository.findAllByChatRoomNo(chatRoomNo);
        log.info(String.valueOf(chatRoomNo) + "  :  " + customerId + "  :  " + storeNo + "  :  " + messages);
        return messages;
    }

    @Override
    public String findRoomAddressByChatRoomNo(long chatRoomNo) {
        return chatRepository.findRoomAddressByChatRoomNo(chatRoomNo);
    }

}
