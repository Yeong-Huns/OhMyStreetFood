package org.omsf.chatRoom.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omsf.chatRoom.model.ChatRoomVO;
import org.omsf.chatRoom.model.DisplayName;
import org.omsf.chatRoom.model.GetChatRoomNoBySubscriptionRequest;
import org.omsf.chatRoom.service.ChatService;
import org.omsf.error.Exception.ErrorCode;
import org.omsf.error.Exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : org.omsf.chatRoom.controller
 * fileName       : ChatRoomController
 * author         : Yeong-Huns
 * date           : 2024-06-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-18        Yeong-Huns       최초 생성
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
@RestController
public class ChatRoomController {

    private final ChatService chatService;

    //1. 고유 Address 조회
    @GetMapping("/getAddress")
    public ResponseEntity<List<String>> getAddress(@RequestParam String username) {
        List<String> Address = chatService.getUserAddress(username);
        if(Address.isEmpty()) throw new NotFoundException(ErrorCode.NOT_FOUND_STORE);
        return ResponseEntity.ok(chatService.getUserAddress(username));
    }

    //2. 구독목록 가져오기
    @GetMapping("/subscriptions")
    public ResponseEntity<List<String>> getUserSubscriptions(@RequestParam String address) {
        return ResponseEntity.ok(chatService.getUserSubscriptions(address));
    }
    //3. 챗룸넘버로 구독주소 가져오기
    @GetMapping("/subscription")
    public ResponseEntity<String> getSubscriptionByChatRoomNo(@RequestParam long chatRoomNo){
        return ResponseEntity.ok(chatService.getSubscriptionByChatRoomNo(chatRoomNo));
    }
    //4. 구독주소로 챗룸넘버
    @GetMapping("/chatRoomNoBySubscription")
    public ResponseEntity<Long> getChatRoomNoBySubscription(String customer, long storeNo){
        return ResponseEntity.ok(chatService.getChatRoomNoBySubscription(customer, storeNo));
    }
    //5. 채팅방 5개 조회
    @GetMapping("/getChatRoomsWithMessage")
    public ResponseEntity<List<GetChatRoomNoBySubscriptionRequest>> getChatRoomsWithLastMessage(String username){
        return ResponseEntity.ok(chatService.getChatRoomsWithLastMessage(username));
    }
    //6. displayName
    @GetMapping("/getDisPlayName")
    public ResponseEntity<DisplayName> getDisplayNameByIdentifier(String identifier){
        return ResponseEntity.ok(chatService.getDisplayNameByIdentifier(identifier));
    }

    @GetMapping("/chatList")
    public ResponseEntity<List<ChatRoomVO>> findSubListByAddress(@RequestParam String address){
        return ResponseEntity.ok(chatService.findSubListByAddress(address));
    }

    // 여러개의 storeNo
    @GetMapping("/storeList")
    public ResponseEntity<List<Integer>> findStoreList(@RequestParam String address){
        return ResponseEntity.ok(chatService.findStoreListByAddress(address));
    }

    @PostMapping("/saveChatRoom")
    public void saveChatRoom(@RequestBody ChatRoomVO chatRoomVO){
        chatService.saveChatRoom(chatRoomVO.getCustomer(), chatRoomVO.getStoreNo());
    }

    @PostMapping("/updateChatRoom")
    public void updateChatRoom(@RequestBody ChatRoomVO chatRoomVO){
        chatService.updateChatRoom(chatRoomVO);
    }


    // 테스트 폼
    @GetMapping("/test")
    public String testForm(){
        return "/chat/chatHandlerTest";
    }

//    // 1대 1 채팅방 메세지 Load
//    @GetMapping("/room")
//    @ResponseBody
//    public ChatRoom roomInfo(@PathVariable String roomId) {
//        return chatService.findById(roomId);
//    }
//


}



