package org.omsf.chatRoom.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omsf.chatRoom.model.MessageWithOwnerResponse;
import org.omsf.chatRoom.model.SubscribeRequest;
import org.omsf.chatRoom.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Controller
@RequestMapping("/chat")
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/room")
    @ResponseBody
    public ResponseEntity<List<MessageWithOwnerResponse>> findAllByCustomerAndStoreNo(SubscribeRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(messageService.findAllByCurrentUserAndStoreNo(request.getCustomerId(), request.getStoreNo()));
    }


    //       messageList.add(new GetChatRoomMessagesRequest("sonjoung@gmail.com", "네번째 메세지", 1, LocalDateTime.of(2024, 6, 22, 22, 45, 54)).toEntity());

}
