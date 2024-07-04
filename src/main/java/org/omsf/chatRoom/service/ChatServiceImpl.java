package org.omsf.chatRoom.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omsf.chatRoom.dao.ChatRepository;
import org.omsf.chatRoom.model.ChatRoomVO;
import org.omsf.chatRoom.model.DisplayName;
import org.omsf.chatRoom.model.GetChatRoomNoBySubscriptionRequest;
import org.omsf.chatRoom.model.SubscribeRequest;
import org.omsf.chatRoom.model.chat.ChatRoom;
import org.omsf.error.Exception.CustomBaseException;
import org.omsf.error.Exception.ErrorCode;
import org.omsf.error.Exception.NotFoundException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

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
@Slf4j
@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService{
    private final ChatRepository chatRepository;
    private final SimpMessagingTemplate messagingTemplate;

    //1. 고유 Address 조회
    @Override
    public List<String> getUserAddress(String username) {
        if(isOwner(username)) return chatRepository.getOwnerAddress(username);
        return Collections.singletonList(username);
    }

    //2. 구독 목록 가져오기
    public List<String> getUserSubscriptions(String address) {
        return chatRepository.getUserSubscriptions(address).stream()
                .map(ChatRoomVO::getChannel)
                .collect(Collectors.toList());
    }

    //3. 챗룸번호로 구독주소 가져오기
    @Override
    public String getSubscriptionByChatRoomNo(long chatRoomNo) {
        return chatRepository.getSubscriptionByChatRoomNo(chatRoomNo)
                .orElseThrow(()->new CustomBaseException(ErrorCode.NOT_ALLOWED_REQUEST));
    }

    //4. 구독주소로 chatRoomNo
    @Override
    public long getChatRoomNoBySubscription(String customer, long storeNo) {
        return chatRepository.getChatRoomNoBySubscription(customer, storeNo)
                .orElseThrow(()->new NotFoundException(ErrorCode.NOT_FOUND_STORENO));
    }

    //5. 5개 가져오기
    @Override
    public List<GetChatRoomNoBySubscriptionRequest> getChatRoomsWithLastMessage(String username){
        return chatRepository.getChatRoomsWithLastMessage(username);
    }

    @Override
    public DisplayName getDisplayNameByIdentifier(String identifier) {
        return chatRepository.getDisplayNameByIdentifier(identifier).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<ChatRoomVO> findSubListByAddress(String address) {
        return chatRepository.findSubListByAddress(address);
    }

    @Override
    public List<Integer> findStoreListByAddress(String address) {
        return chatRepository.findStoreListByAddress(address);
    }

    @Override
    public void saveChatRoom(String address, long storeNo) {
        chatRepository.saveChatRoom(address, storeNo);
    }

    @Override
    public void updateChatRoom(ChatRoomVO chatRoomVO) {
        chatRepository.updateChatRoom(chatRoomVO);
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
        if(!isSubscribed(request)) chatRepository.subscribeToChatRoom(request.getCustomerId(), request.getStoreNo());
        String owner = chatRepository.findOwnerByRoomAddress(request.getStoreNo())
                .orElseThrow(()-> new CustomBaseException(ErrorCode.NOT_FOUND_STORE_OWNER));
        messagingTemplate.convertAndSend("/topic/chat/" + request.getCustomerId(), request.getCombinedId());
        messagingTemplate.convertAndSend("/topic/chat/" + request.getStoreNo(), request.getCombinedId());
        log.info("발신주소 send : /topic/chat/"+request.getCustomerId()+" : combinedId? = " + request.getCustomerId());
        log.info("수신주소 send : /topic/chat/"+owner+" : combinedId? = " + request.getCustomerId());
    }

    @Override
    public boolean isSubscribed(SubscribeRequest request) {
        return chatRepository.isSubscribed(request.getCustomerId(), request.getStoreNo()) > 0;
    }


    private boolean isOwner(String username){
        Boolean result = chatRepository.isOwner(username);
        if(result == null) throw new NotFoundException(ErrorCode.NOT_FOUND_USER);
        return result;
    }

}
