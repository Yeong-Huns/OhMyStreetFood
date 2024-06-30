<%--
  Created by IntelliJ IDEA.
  User: KOSA
  Date: 2024-06-21
  Time: AM 11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ko">
<head>
    <title>Websocket ChatRoom</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        .hidden {
            display: none;
        }
    </style>
</head>
<body>
<div>
    <button onclick="location.href='${pageContext.request.contextPath}/chat/room'">돌아가기</button>
</div>
<div class="container hidden" id="app">
    <div>
        <h2 id="room-name"></h2>
    </div>
    <div class="input-group">
        <div class="input-group-prepend">
            <label class="input-group-text">내용</label>
        </div>
        <input type="text" class="form-control" id="message-input">
        <div class="input-group-append">
            <button class="btn btn-primary" type="button" id="send-button">보내기</button>
        </div>
    </div>
    <ul class="list-group" id="messages-list">
    </ul>
    <div></div>
</div>
<!-- JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1.5.1/dist/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script>
    const contextPath = "<%= request.getContextPath() %>";
    var sock = new SockJS(contextPath + "/ws/chat");
    var ws = Stomp.over(sock);
    var reconnect = 0;
    var roomId = localStorage.getItem('wschat.roomId');
    var sender = localStorage.getItem('wschat.sender');
    var room = {};
    var messages = [];

    document.addEventListener("DOMContentLoaded", function() {
        findRoom();
        connect();

        document.getElementById("send-button").addEventListener("click", sendMessage);
        document.getElementById("message-input").addEventListener("keypress", function(event) {
            if (event.key === "Enter") {
                sendMessage();
            }
        });
    });

    function findRoom() {
        fetch(contextPath + '/chat/room/' + roomId)
            .then(response => response.json())
            .then(data => {
                room = data;
                document.getElementById("room-name").textContent = room.roomName;
                document.getElementById("app").classList.remove("hidden");
            });
    }

    function sendMessage() {
        var messageInput = document.getElementById("message-input");
        var messageContent = messageInput.value;
        if (messageContent.trim() !== "") {
            ws.send("/app/chat/message", {}, JSON.stringify({type: 'TALK', roomId: roomId, sender: sender, message: messageContent}));
            messageInput.value = '';
        }
    }

    function recvMessage(recv) {
        var messageList = document.getElementById("messages-list");
        var messageItem = document.createElement("li");
        messageItem.className = "list-group-item";
        messageItem.textContent = (recv.type === 'ENTER' ? '[알림]' : recv.sender) + " - " + recv.message;
        messageList.prepend(messageItem);
    }

    function connect() {
        ws.connect({}, function(frame) {
            ws.subscribe("/topic/chat/room/" + roomId, function(message) {
                var recv = JSON.parse(message.body);
                recvMessage(recv);
            });
            ws.send("/app/chat/message", {}, JSON.stringify({type: 'ENTER', roomId: roomId, sender: sender}));
        }, function(error) {
            if(reconnect++ <= 5) {
                setTimeout(function() {
                    console.log("connection reconnect");
                    sock = new SockJS(contextPath + "/ws/chat");
                    ws = Stomp.over(sock);
                    connect();
                }, 10 * 1000);
            }
        });
    }
</script>
</body>
</html>
