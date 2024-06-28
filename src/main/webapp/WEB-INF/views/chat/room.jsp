<%--
  Created by IntelliJ IDEA.
  User: KOSA
  Date: 2024-06-21
  Time: AM 11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ko">
<head>
    <title>Websocket Chat</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <!-- CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        .hidden {
            display: none;
        }
    </style>
</head>
<body>
<div class="container" id="app">
    <div class="row">
        <div class="col-md-12">
            <h3>채팅방 리스트</h3>
        </div>
    </div>
    <div class="input-group">
        <div class="input-group-prepend">
            <label class="input-group-text">방제목</label>
        </div>
        <input type="text" class="form-control" id="room_name">
        <div class="input-group-append">
            <button class="btn btn-primary" type="button" onclick="createRoom()">채팅방 개설</button>
        </div>
    </div>
    <ul class="list-group" id="chatrooms">
    </ul>
</div>
<!-- JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1.5.1/dist/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script>
    const contextPath = "<%= request.getContextPath() %>";

    document.addEventListener("DOMContentLoaded", function() {
        connectWebSocket();
        findAllRoom();
    });

    let stompClient = null;
    let socket = null;

    function connectWebSocket(username) {
        if(!username){
            console.log("로그인하지 않으면 웹소켓 연결 X");
            return;
        }
        const socket = new SockJS(contextPath + '/ws/chat');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log("연결됨 : " + frame)


            stompClient.subscribe('/topic/chat/' + username, function (message) {
                console.log("개별 채팅방 주소 : " + "/topic/chat/" + username);
                console.log("구독 요청받은 채널 주소 : " + message.body);
                subscribeToRoom(message.body);
                //findAllRoom();
            });
        }, function (error){
            console.error('webSocket error : ', error);
            // 에러시 연결 복구 시도
            setTimeout(function (){
                connectWebSocket(username);
            }, 3000);
            });
    }

    function subscribeToRoom(combinedId) {
        stompClient.subscribe('/queue/chat/' + combinedId, function (message) {
            console.log('Received message in room: ' + message.body);
            handleReceivedMessage(JSON.parse(message.body));
        });
    }

    function findAllRoom() {
        fetch(contextPath + '/chat/rooms')
            .then(response => response.json())
            .then(data => {
                const chatrooms = data;
                const chatroomList = document.getElementById('chatrooms');
                chatroomList.innerHTML = '';
                chatrooms.forEach(item => {
                    const li = document.createElement('li');
                    li.className = 'list-group-item list-group-item-action';
                    li.textContent = item.roomName;
                    li.setAttribute('data-room-id', item.roomId);
                    li.onclick = function() {
                        enterRoom(item.roomId);
                    };
                    chatroomList.appendChild(li);
                });
            })
            .catch(error => console.error('Error:', error));
    }

    function createRoom() {
        const roomNameInput = document.getElementById('room_name');
        const roomName = roomNameInput.value;
        if (roomName === "") {
            alert("방 제목을 입력해 주십시요.");
            return;
        } else {
            const params = new URLSearchParams();
            params.append("name", roomName);
            fetch(contextPath + '/chat/room', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: params.toString()
            })
                .then(response => response.json())
                .then(data => {
                    alert(data.roomName + " 방 개설에 성공하였습니다.");
                    roomNameInput.value = '';
                    findAllRoom(); // 방 생성 후 방 목록 갱신
                })
                .catch(error => {
                    alert("채팅방 개설에 실패하였습니다.");
                    console.error('Error:', error);
                });
        }
    }

    function enterRoom(roomId) {
        const sender = prompt('대화명을 입력해 주세요.');
        if (sender !== "") {
            localStorage.setItem('wschat.sender', sender);
            localStorage.setItem('wschat.roomId', roomId);
            console.log(sender+" : "+roomId)
            location.href = contextPath + "/chat/room/enter/" + roomId;
        }
    }
</script>
</body>
</html>
