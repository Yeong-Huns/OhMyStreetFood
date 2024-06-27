package org.omsf.chatRoom.service;

import lombok.RequiredArgsConstructor;
import org.omsf.chatRoom.dao.ChatRepository;
import org.omsf.chatRoom.dao.MessageRepository;
import org.omsf.chatRoom.model.MessageVO;
import org.omsf.chatRoom.model.MessageWithOwnerResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

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
@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void handleSendMessage(MessageVO message) {
        String owner = chatRepository.findOwnerByChatRoomNo(message.getChatRoomNo());
        String customer = chatRepository.findCustomerByChatRoomNo(message.getChatRoomNo());
        messageRepository.save(message);
        messagingTemplate.convertAndSend("/queue/chat/" + owner, message);
        messagingTemplate.convertAndSend("/queue/chat/" + customer, message);
    }

    @Override
    public List<MessageWithOwnerResponse> findAllByChatRoomNo(long chatRoomNo) {
        return messageRepository.findAllByChatRoomNo(chatRoomNo);
    }

    @Override
    public List<MessageWithOwnerResponse> findAllByCurrentUserAndStoreNo(String customerId, long storeNo) {
        long chatRoomNo = chatRepository.findChatRoomNoByCurrentUserAndStoreNo(customerId, storeNo);
        return messageRepository.findAllByChatRoomNo(chatRoomNo);
    }

    @Override
    public String findRoomAddressByChatRoomNo(long chatRoomNo) {
        return chatRepository.findRoomAddressByChatRoomNo(chatRoomNo);
    }

}
