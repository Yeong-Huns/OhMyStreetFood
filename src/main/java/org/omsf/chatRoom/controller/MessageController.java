package org.omsf.chatRoom.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omsf.chatRoom.model.MessageVO;
import org.omsf.chatRoom.model.MessageWithOwnerResponse;
import org.omsf.chatRoom.model.SubscribeRequest;
import org.omsf.chatRoom.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : org.omsf.chatRoom.controller
 * fileName       : MessageController
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
@RestController
@RequestMapping("/chat")
public class MessageController {

    private final MessageService messageService;


    @GetMapping("/room")
    public ResponseEntity<List<MessageVO>> findAllMessageBySubscription(@RequestParam String customer, @RequestParam String storeNo) {
        return ResponseEntity.ok(messageService.findAllMessageBySubscription(customer, storeNo));
    }

    @PutMapping("/updateMessage")
    public void updateMessageStatus(@RequestParam long messageNo){
        messageService.updateMessageStatus(messageNo);
    }

    @GetMapping("/chatRoomNo")
    public List<MessageVO> findAllMessageByChatRoomNo(@RequestParam long chatRoomNo){
        return messageService.findAllMessageByChatRoomNo(chatRoomNo);
    }

    @GetMapping
    public String findRoomAddressByChatRoomNo(long chatRoomNo) {
        return messageService.findRoomAddressByChatRoomNo(chatRoomNo);
    }


}
