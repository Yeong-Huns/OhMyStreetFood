var stompClient = null;
var socket = null;
var currentPath = window.location.pathname;
var subscribedChannels = {};

function startChat(currentUser, storeNo) {
    var params = "customerId=" + currentUser + "&storeNo=" + storeNo;

    fetch('/chat/room?' + params, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if(!response.ok) {
            console.log("<<기존 채팅방을 찾지 못했습니다...>> " + response.statusText);
            console.log("새로운 채팅방 생성 중 ..")
            subscribeNewRoomRequest(currentUser, storeNo);
        }
        return response.json();
    })
        .then(data => {
            console.log("메세지목록 : ", data);
            showChatRoom(data, currentUser); // 모달&메세지표시
        })
        .catch(error => {
            console.error(error);
        });
}

function showChatRoom(messages, senderId) {
    var chatMessagesContainer = document.getElementById('chat-messages');
    chatMessagesContainer.innerHTML = ''; // 초기화

    messages.forEach(function (message) {
        showMessage(message, senderId);
    });

    var chatRoomModal = new bootstrap.Modal(document.getElementById('chatRoomModal'));
    chatRoomModal.show();

    // 모달창이 열릴 때 종모양 알람을 숨김
    $('#alarmIcon').removeClass('shake').css('visibility', 'hidden');
}

function showMessage(message, currentUserId) {
    var messageElement = document.createElement('div');
    var date = new Date(message.createdAt);
    var hours = date.getHours();
    var minutes = date.getMinutes().toString().padStart(2, '0');
    var ampm = hours >= 12 ? '오후' : '오전';
    var formattedTime = `${ampm} ${hours % 12 || 12}:${minutes}`;

    var isCurrentUser = message.senderId === currentUserId;

    messageElement.className = 'chat-message ' + (isCurrentUser ? 'sent' : 'received');
    messageElement.innerHTML = `
        ${!isCurrentUser ? `
        <div class="chat-avatar">
            <img src="../../img/00_1.jpg" alt="Avatar">
        </div>` : ''}
        <div class="message-content">
            <div>${message.content.replace(/\n/g, '<br>')}</div>
            <div class="chat-time">${message.createdAt ? formattedTime : ''}</div>
        </div>
    `;

    document.getElementById('chat-messages').appendChild(messageElement);
    document.getElementById('chat-messages').scrollTop = document.getElementById('chat-messages').scrollHeight;
}

function subscribeNewRoomRequest(currentUser, storeNo) {
    var combinedId = currentUser + storeNo;
    if (!subscribedChannels[combinedId]) { // 중복 구독 방지
        var channel = '/app/chat/subRequest';
        stompClient.send(channel, {}, JSON.stringify({
            customerId: currentUser,
            storeNo: storeNo
        }));

    }
}

function handleReceivedMessage(message) {
    console.log("메세지수신완료 : ", message);
    showMessage(message, message.senderId);

    // 모달 창이 활성화되어 있는지 확인
    var chatRoomModal = document.getElementById('chatRoomModal');
    var isModalShown = chatRoomModal.classList.contains('show'); // 부트스트랩 모달이 활성화된 경우 'show' 클래스를 가집니다.
    if (!isModalShown) {
        showNotification(message);
    }
}

function updateChatList(message) {
    console.log('Update chat list with message:', message);
    fetch("/chat/rooms?userId=" + senderId)
        .then(response => response.json())
        .then(data => {
            console.log("구독한 방 주소 : ", data);
            updateChatWindow(data);
            var chatRoomListModal = new bootstrap.Modal(document.getElementById('chatRoomListModal'));
            chatRoomListModal.show();
        })
        .catch(error => {
            console.error("채팅방을 불러오는 중 오류 발생 .. ")
        })
}

function updateChatWindow(rooms) {
    var chatRoomListContainer = document.getElementById('chatroom-list');
    chatRoomListContainer.innerHTML = ''; // 초기화

    rooms.forEach(function(room) {
        var existingChatroom = document.getElementById('chatroom-' + room.chatroomno);
        if (existingChatroom) {
            existingChatroom.querySelector('.chatroom-last-message').innerText = room.lastMessage;
            existingChatroom.querySelector('.chatroom-time').innerText = room.time;
        } else {
            var chatroomItem = document.createElement('div');
            chatroomItem.id = 'chatroom-' + room.chatroomno;
            chatroomItem.className = 'chatroom-item';
            chatroomItem.innerHTML = `
                <div class="chatroom-avatar"><img src="${room.avatar}" alt="Avatar" /></div>
                <div class="chatroom-content">
                    <div class="chatroom-name">${room.name}</div>
                    <div class="chatroom-last-message">${room.lastMessage}</div>
                </div>
                <div class="chatroom-time">${room.time}</div>
            `;
            chatroomItem.onclick = function() {
                startChat(senderId, room.name);
            };
            chatRoomListContainer.appendChild(chatroomItem);
        }
    });
}

function showNotification(message) {
    var notification = document.createElement('div');
    notification.className = 'chat-notification';
    notification.innerHTML = `
        <div class="chat-notification-content">
            <img src="../../img/00_1.jpg" alt="Profile Picture">
            <span>${message.senderId}</span>
            <span class="message">${message.content}</span>
        </div>
    `;
    document.body.appendChild(notification);

    setTimeout(function () {
        notification.remove();
    }, 3000);
}

function fetchChatRooms(username) {
    fetch('/chat/rooms')
        .then(response => response.json())
        .then(data => {
            console.log('Chat rooms fetched:', data);
        })
        .catch(error => console.error('Error fetching chat rooms:', error));
}

function connectWebSocket(username) {
    if (!username) {
        console.log("로그인상태가 아닙니다.");
        return;
    }
    socket = new SockJS('/ws/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        console.log("유저명 : " + username);
        stompClient.subscribe('/topic/chat/' + username, function (message) {
            var combinedId = message.body;
            subscribeToRoom(combinedId);
        });
    }, function (error) {
        console.error('WebSocket error:', error);
        setTimeout(function () {
            connectWebSocket(username);
        }, 10000);
    });
}

function subscribeToRoom(combinedId) {
    if (!subscribedChannels[combinedId]) {
        stompClient.subscribe('/queue/chat/' + combinedId, function (message) {
            console.log('수신함 : ' + message.body);
            handleReceivedMessage(JSON.parse(message.body));
        });
        subscribedChannels[combinedId] = true;
    }
}

function sendMessage(senderId, chatRoomNo) {
    var messageInput = document.getElementById("message-input");
    var content = messageInput.value;
    if (!content) {
        return;
    }


    stompClient.send('app/chat/sendRequest', {}, JSON.stringify({
        senderId: senderId,
        content: content,
        chatRoomNo: chatRoomNo
    }))
    messageInput.value = '';


    // fetch('/chat/sendRequest', {
    //     method: 'POST',
    //     headers: {
    //         'Content-Type': 'application/json'
    //     },
    //     body: JSON.stringify(message)
    // })
    //     .then(response => response.json())
    //     .then(data => {
    //         if (data.code === 'E3') {
    //             console.error('서버 에러가 발생했습니다: ', data.message);
    //         } else {
    //             console.log('메세지 저장완료 😋😋😋 :', data);
    //             stompClient.send("/app/chat/sendRequest", {}, JSON.stringify({
    //                 senderId: senderId,
    //                 receiverId: receiverId,
    //                 content: content,
    //                 createdAt: data.createdAt
    //             }));
    //             messageInput.value = '';
    //         }
    //     })
    //     .catch(error => {
    //         console.error('Error:', error);
    //     });
    //

}

function initializeWebSocket(username) {
    if (!username) {
        console.log("로그인 상태가 아닙니당.");
        logout();
        return
    }
    if(!stompClient || !stompClient.connect){
        connectWebSocket(username);
    }else{
        console.log("이미 접속중입니다.")
    }

    //
    // if (sessionStorage.getItem('alarmVisible') === 'true') {
    //     $('#alarmIcon').css('visibility', 'visible');
    // }
}

function disconnectWebSocket() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function logout() {
    fetch('/logout', {
        method: 'POST'
    })
        .then(response => {
            if (response.ok) {
                sessionStorage.removeItem('username');
                sessionStorage.removeItem('alarmVisible');
                localStorage.removeItem('token');
                disconnectWebSocket();
                //window.location.href = '/';
            } else {
                throw new Error('Logout failed');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

$(document).ready(function () {
    $('body').append('<i id="alarmIcon" class="fa fa-bell" style="font-size:24px; position: fixed; top: 10px; right: 10px; visibility: hidden;"></i>');
    // 모달이 닫힐 때 backdrop을 수동으로 제거
    $('#chatRoomModal').on('hidden.bs.modal', function () {
        $('.modal-backdrop').remove();
    });
});
