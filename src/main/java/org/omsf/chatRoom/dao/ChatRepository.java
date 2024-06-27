package org.omsf.chatRoom.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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
    int isSubscribed(@Param("customerId") String customerId, @Param("storeNo") long storeNo);

    void subscribeToChatRoom(@Param("customer") String customerId, @Param("storeNo")long storeNo);

    @Select("SELECT customer || storeNo AS RoomAddress FROM CHATROOM WHERE chatRoomNo = #{chatRoomNo}")
    String findRoomAddressByChatRoomNo(@Param("chatRoomNo") long chatRoomNo);

    @Select("SELECT chatRoomNo FROM ChatRoom WHERE customer = #{currentUser} AND storeNo = #{storeNo}")
    Long findChatRoomNoByCurrentUserAndStoreNo(@Param("customer") String currentUser, @Param("storeNo") Long storeNo);

    Optional<String> findOwnerByRoomAddress(@Param("storeNo") long storeNo);

    String findOwnerByChatRoomNo(@Param("chatRoomNo") Long chatRoomNo);

    String findCustomerByChatRoomNo(@Param("chatRoomNo") Long chatRoomNo);


}
