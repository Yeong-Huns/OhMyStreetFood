<%--
  Created by IntelliJ IDEA.
  User: KOSA
  Date: 2024-06-21
  Time: AM 6:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <title>Chat Room List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/chat/chatForm.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/chat/chatForm.js"></script>

    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.0.2/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
</head>

<body>
<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#chatRoomListModal">
    채팅 시작
</button>

<!-- 채팅방 목록 모달 -->
<div class="modal fade" id="chatRoomListModal" tabindex="-1" aria-labelledby="chatRoomListModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content chatroom-container">
            <div class="modal-header chatroom-header" id="chatRoomListModalHeader">
                <h5 class="modal-title" id="chatRoomListModalLabel">채팅</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body chatroom-list" id="chatroom-list">
                <!-- 채팅방 리스트 표시 영역 -->
            </div>
        </div>
    </div>
</div>

<!-- 개별 채팅방 모달 -->
<div class="modal fade" id="chatRoomModal" tabindex="-1" aria-labelledby="chatRoomModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg chat-modal-dialog">
        <div class="modal-content chatroom-container">
            <div class="modal-header chatroom-header" id="chatRoomModalHeader">
                <div class="chat-avatar" id="chat-avatar">김</div>
                <h5 class="modal-title" id="chatRoomModalLabel">김창환</h5> <!-- Name dynamically updated here -->
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body chat-messages" id="chat-messages">
                <!-- 채팅 메시지 표시 영역 -->
            </div>
            <div class="modal-footer chat-footer">
                <input type="text" id="message-input" class="form-control message-input" placeholder="메시지를 입력하세요...">
                <button class="btn btn-primary send-button" onclick="sendMessage()">전송</button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>

</body>

</html>
