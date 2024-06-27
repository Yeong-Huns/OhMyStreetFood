package org.omsf.chatRoom.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omsf.chatRoom.model.ChatRoomVO;
import org.omsf.chatRoom.model.MessageVO;
import org.omsf.chatRoom.service.ChatService;
import org.omsf.error.Exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
@Controller
public class ChatRoomController {

    private final ChatService chatService;
    private List<MessageVO> messageList = new ArrayList<>();


    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoomVO> getChatRooms(@RequestParam String userId) {
        return chatService.findAllRoom();
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

    @PostMapping("/saveMessage")
    @ResponseBody
    public ResponseEntity<?> saveMessage(@RequestBody MessageVO message) {
        try {
            message.setCreatedAt(LocalDateTime.now());
            messageList.add(message); // 실제 DB 대신 리스트에 저장하여 테스트
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            log.error("메시지 저장 중 오류 발생: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BadRequestException());
        }
    }



}



