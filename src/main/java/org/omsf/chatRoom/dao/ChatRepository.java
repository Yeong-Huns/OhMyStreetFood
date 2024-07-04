package org.omsf.chatRoom.dao;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.omsf.chatRoom.model.ChatRoomVO;
import org.omsf.chatRoom.model.DisplayName;
import org.omsf.chatRoom.model.GetChatRoomNoBySubscriptionRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * packageName    : org.omsf.chatRoom.dao
 * fileName       : ChatRepository
 * author         : Yeong-Huns
 * date           : 2024-06-20
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-20        Yeong-Huns       최초 생성
 */
@Repository
public interface ChatRepository {

    // owner 확인


    //1. 고유 Address 조회
    Boolean isOwner(@Param("username") String username);
    List<String> getOwnerAddress(@Param("username") String username);

    //2. 구독목록 가져오기
    List<ChatRoomVO> getUserSubscriptions(@Param("address") String address);

    //3. chatRoomNo 로 Subscription 가져오기
    Optional<String> getSubscriptionByChatRoomNo(@Param("chatRoomNo") long chatRoomNo);

    //4.Subscription 으로 chatRoomNo 가져오기
    Optional<Long> getChatRoomNoBySubscription(@Param("customer") String customer, @Param("storeNo") long storeNo);
    //5. username chatroom get 5 order by updatedAt 😋
    List<GetChatRoomNoBySubscriptionRequest> getChatRoomsWithLastMessage(@Param("username") String username);
    //6. find DisplayName
    Optional<DisplayName> getDisplayNameByIdentifier(@Param("identify") String identify);


    List<ChatRoomVO> findSubListByAddress(@Param("address") String address);
    List<Integer> findStoreListByAddress(@Param("address") String address);
    void saveChatRoom(@Param("address") String address, @Param("storeNo")long storeNo);
    void updateChatRoom(ChatRoomVO chatRoomVO);


    int isSubscribed(@Param("customerId") String customerId, @Param("storeNo") long storeNo);

    void subscribeToChatRoom(@Param("customerId") String customerId, @Param("storeNo")long storeNo);

    @Select("SELECT customer || storeNo AS RoomAddress FROM CHATROOM WHERE chatRoomNo = #{chatRoomNo}")
    String findRoomAddressByChatRoomNo(@Param("chatRoomNo") long chatRoomNo);

    @Select("SELECT chatRoomNo FROM ChatRoom WHERE customer = #{customerId} AND storeNo = #{storeNo}")
    Long findChatRoomNoByCurrentUserAndStoreNo(@Param("customerId") String customerId, @Param("storeNo") Long storeNo);

    Optional<String> findOwnerByRoomAddress(@Param("storeNo") long storeNo);

    String findOwnerByChatRoomNo(@Param("chatRoomNo") Long chatRoomNo);

    String findCustomerByChatRoomNo(@Param("chatRoomNo") Long chatRoomNo);


}
