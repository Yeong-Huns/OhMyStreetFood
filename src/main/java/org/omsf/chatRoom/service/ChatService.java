package org.omsf.chatRoom.service;

import org.apache.ibatis.annotations.Param;
import org.omsf.chatRoom.model.ChatRoomVO;
import org.omsf.chatRoom.model.GetChatRoomNoBySubscriptionRequest;
import org.omsf.chatRoom.model.SubscribeRequest;
import org.omsf.chatRoom.model.chat.ChatRoom;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * packageName    : org.omsf.chatRoom.service
 * fileName       : ChatService
 * author         : Yeong-Huns
 * date           : 2024-06-20
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-20        Yeong-Huns       최초 생성
 */
public interface ChatService {

    //1. 고유 Address
    List<String> getUserAddress(String username);
    //2. 구독목록 가져오기
    List<String> getUserSubscriptions(String address);
    //3. 챗룸번호로 구독주소 가져오기
    String getSubscriptionByChatRoomNo(long chatRoomNo);
    //4. 구독주소로 챗룸번호
    long getChatRoomNoBySubscription(String customer, long storeNo);
    //5. get 5 chatroom
    List<GetChatRoomNoBySubscriptionRequest> getChatRoomsWithLastMessage(String username);


    List<ChatRoomVO> findSubListByAddress(String address);
    List<Integer> findStoreListByAddress(@Param("address") String address);
    void saveChatRoom(String address, long storeNo);
    void updateChatRoom(@RequestBody ChatRoomVO chatRoomVO);

    void subscribeToChatRoom(SubscribeRequest request);
    boolean isSubscribed(SubscribeRequest request);
    ChatRoom findById(String roomId);
    ChatRoom createRoom(String name);

}
