var stompClient = null;
var socket = null;
var currentPath = window.location.pathname;
var subscribedChannels = {};


function subscribeNewRoomRequest(currentUser, storeNo) {
    var combinedId = currentUser + storeNo;
    console.log("메세지 브로커에게 구독요청을 보내는중 ..")
    console.log("설정할 우리들의 구독주소 :: ", combinedId);
    if (!subscribedChannels[combinedId]) { // 중복 구독 방지
        console.log("브로커가 구독요청 처리중 ..")
        var channel = '/app/chat/subRequest';
        stompClient.send(channel, {}, JSON.stringify({
            customerId: currentUser,
            storeNo: storeNo
        }));
    } else console.log("브로커가 이미 처리한 구독 요청입니다. ")
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

    rooms.forEach(function (room) {
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
            chatroomItem.onclick = function () {
                startChat(senderId, room.name);
            };
            chatRoomListContainer.appendChild(chatroomItem);
        }
    });
}


function fetchChatRooms(username) {
    fetch('/chat/rooms')
        .then(response => response.json())
        .then(data => {
            console.log('Chat rooms fetched:', data);
        })
        .catch(error => console.error('Error fetching chat rooms:', error));
}

function connect(username) {
    if (!username) {
        console.log("로그인상태가 아닙니당.");
        return;
    }
    socket = new SockJS('/ws/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('웹소켓 연결됨 : ' + frame);
        console.log("유저명 : " + username);

        fetch('/chat/getAddress?username=' + username)
            .then(response => {
                if (!response.ok) throw new Error("서버 연결 상태를 확인해주세요.")
                return response.json();
            })
            .then(addressList => {
                addressList.forEach(function (address) {
                    subscribeAddress(address);
                    initialize(address, username);
                })
            }).catch(error=> console.error("/chat/getAddress 호출 오류 : " , error));
    }, function (error) {
        console.error('웹소켓 연결 재시도 .. : ', error);
        setTimeout(function () {
            connect(username);
        }, 5000);
    });
}

function subscribeAddress(address) {
    stompClient.subscribe('/topic/chat/' + address, function (chan) {
        var channel = chan.body;
        console.log("채널에 연결되었습니다: " + channel)
        if (!subscribedChannels[channel]) {
            subscribeToChannel(channel);
        }
    });
}

function subscribeToChannel(channel) {
    if (!subscribedChannels[channel]) {
        stompClient.subscribe('/queue/chat/' + channel, function (message) {
            console.log('메세지 큐 -> : ' + message.body);
            handleReceivedMessage(JSON.parse(message.body));
        });
        subscribedChannels[channel] = true;
    }
}

function initialize(address, username) {
    fetch('/chat/subscriptions?address=' + address)
        .then(response => {
            if (!response.ok) throw new Error(username + "님의 구독 리스트 조회에 실패했습니다.");
            return response.json();
        })
        .then(subscriptions => {
            subscriptions.forEach(subscription => {
                connectToChannel(subscription, address);
            });
        })
        .catch(error => {
            console.error('/chat/subscriptions 호출 중 오류 발생 : ', error);
        });
}

function startChat(customer, storeNo, address) {

    fetch('/chat/room?customer=' + customer + "&storeNo=" + storeNo, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if (!response.ok) {
            console.log("<<기존 채팅방을 찾지 못했습니다...>> ");
            console.log("새로운 채팅방 생성 중 ..")
            return [];
        }
        return response.json();
    })
        .then(data => {
            console.log("메세지목록 : ", data);
            subscribeNewRoomRequest(customer, storeNo);
            showChatRoom(data, customer); // 모달&메세지표시
        })
        .catch(error => {
            console.error(error);
        });
}

function connectToChannel(subscription, address) {
    const match = subscription.match(/(.*?)(\d+)$/);
    if(match) {
        const customer = match[1];
        const storeNo = match[2];
        const target = (customer === address) ? storeNo : customer;
        stompClient.send("/app/chat/subscribe", {}, JSON.stringify({
            requestingUser: address,
            target: target,
            channel: subscription
        }));
    }
}


function sendMessage(senderId, storeNo) {
    var messageInput = document.getElementById("message-input");
    var content = messageInput.value;
    if (!content) {
        return;
    }
    stompClient.send('/app/chat/sendRequest', {}, JSON.stringify({
        senderId: senderId,
        content: content,
        storeNo: storeNo
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

function handleReceivedMessage(message) {
    console.log(" -> 핸들러 accept : ", message);
    showMessage(message, message.senderId);
    // 모달 창 활성화 확인
    let chatRoomModal = document.getElementById('chatRoomModal');
    let isModalShown = chatRoomModal.classList.contains('show');
    if (!isModalShown) {
        showNotification(message);
    }
}

function showChatRoom(messages, senderId) {
    var chatMessagesContainer = document.getElementById('chat-messages');
    chatMessagesContainer.innerHTML = ''; // 초기화
    messages.forEach(function (message) {
        showMessage(message, senderId);
    });

    var chatRoomModal = new bootstrap.Modal(document.getElementById('chatRoomModal'));
    chatRoomModal.show();
}


function showMessage(message, sender) {
    var messageElement = document.createElement('div');
    var date = new Date(message.createdAt);
    var hours = date.getHours();
    var minutes = date.getMinutes().toString().padStart(2, '0');
    var ampm = hours >= 12 ? '오후' : '오전';
    var formattedTime = `${ampm} ${hours % 12 || 12}:${minutes}`;
    var chatRoomNo = message.storeNo;
    var isCurrentUser = message.senderId === sender;


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

    // 클릭 이벤트 추가
    notification.addEventListener('click', function () {
        openChatRoomModal(message);
    });

    setTimeout(function () {
        notification.remove();
    }, 3000);
}


function openChatRoomModal(message) {
    var sender = message.senderId;
    var storeNo = message.storeNo;
    var chatRoomNo = message.storeNo;

    //startChat(customer, storeNo);
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
    // 알람 아이콘 추가
    $('body').append('<i id="alarmIcon" class="fa fa-bell" style="font-size:24px; position: fixed; top: 10px; right: 10px; visibility: hidden;"></i>');

    // 모달이 닫힐 때 backdrop을 수동으로 제거
    $('#chatRoomModal').on('hidden.bs.modal', function () {
        $('.modal-backdrop').remove();
    });


    var chatRoomModal = new bootstrap.Modal(document.getElementById('chatRoomModal'));

    $('#openChatRoomButton').on('click', function () {
        chatRoomModal.show();
    });

    $('.btn-close').on('click', function () {
        chatRoomModal.hide();
    });
});