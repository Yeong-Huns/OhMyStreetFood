package org.omsf.chatRoom.service;

import lombok.RequiredArgsConstructor;
import org.omsf.chatRoom.dao.ChatRepository;
import org.omsf.chatRoom.model.ChatRoomVO;
import org.omsf.chatRoom.model.SubscribeRequest;
import org.omsf.chatRoom.model.chat.ChatRoom;
import org.omsf.error.Exception.CustomBaseException;
import org.omsf.error.Exception.ErrorCode;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * packageName    : org.omsf.chatRoom.service
 * fileName       : chatService
 * author         : Yeong-Huns
 * date           : 2024-06-20
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-20        Yeong-Huns       최초 생성
 */
@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService{
    private final ChatRepository chatRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public List<ChatRoomVO> findAllRoom() {
        return Collections.emptyList();
    }

    @Override
    public ChatRoom findById(String roomId) {
        return null;
    }

    @Override
    public ChatRoom createRoom(String name) {
        return null;
    }

    @Override
    public void subscribeToChatRoom(SubscribeRequest request) {
        if(!isSubscribed(request)){
            String owner = chatRepository.findOwnerByRoomAddress(request.getStoreNo())
                    .orElseThrow(()-> new CustomBaseException(ErrorCode.NOT_FOUND_STORE_OWNER));
            chatRepository.subscribeToChatRoom(request.getCustomerId(), request.getStoreNo());

            messagingTemplate.convertAndSend("/topic/chat/" + request.getCustomerId(), request.getCombinedId());
            messagingTemplate.convertAndSend("/topic/chat/" + owner, request.getCombinedId());
        }
    }

    @Override
    public boolean isSubscribed(SubscribeRequest request) {
        return chatRepository.isSubscribed(request.getCustomerId(), request.getStoreNo()) > 0;
    }


}
