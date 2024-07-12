/**
 * author         : Yeong-Huns
 * ===========================================================
 AUTHOR             NOTE
 * -----------------------------------------------------------
 Yeong-Huns       최초 생성
 */

var stompClient = null;
var socket = null;
var currentPath = window.location.pathname;
let subscribedChannels = {};
let subscribedLikes = {};


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

function sendTestMessage(content, event){
    stompClient.send("/app/chat/chatBot", {}, JSON.stringify(content));
}

function onMessageReceived(payload){
    console.log(payload);
}

function connect(username) {
    if (!username) {
        console.log("로그인상태가 아닙니당.");
        return;
    }
    socket = new SockJS('/ws/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

        // 테스트 용
        stompClient.subscribe('/topic/public', onMessageReceived);
        sendTestMessage("지금 뭐하고 있니");
        // 테스트 용 종료

        console.log('웹소켓 연결됨 : ' + frame);
        console.log("유저명 : " + username);

        fetch('/chat/getAddress?username=' + username)
            .then(response => {
                if (!response.ok) throw new Error("서버 연결 상태를 확인해주세요.")
                return response.json();
            })
            .then(addressList => {
                addressList.forEach(function (address) {
                    subscribeAddress(address, username);
                    initialize(address, username);
                })
            }).then(()=>noticeAlarm(username))
            .catch(error => console.error("/chat/getAddress 호출 오류 : ", error))


    }, function (error) {
        console.error('웹소켓 연결 재시도 .. : ', error);
        setTimeout(function () {
            connect(username);
        }, 5000);
    });
}

function noticeAlarm(username){
    fetch('/chat/getNoticeList?username='+username)
        .then(res=>res.json())
        .then(noticeList=>{
            if(noticeList.length===0) {console.log("좋아요 누른 가게가 없어용"); return}
            noticeList.forEach(notice => noticeAlarmRequest(notice));
        })
        .catch(err => console.error(err));
}

function noticeAlarmRequest(notice){
    if((!subscribedLikes[notice])){
    stompClient.subscribe('/topic/notice/' + notice, (payload)=> alarmToModal(payload))
    console.log(`${notice} 채널의 뉴스피드 구독 진행중입니다.`);
    subscribedLikes[notice] = true;
    }
}

function unfollowAlarmRequest(notice){
    subscribedLikes[notice] = false;
    stompClient.unsubscribe('topic/notice'+notice);
    console.log(`${notice} 채널의 뉴스피드 구독 취소`);
}

const alarmToModal = (payload) => {
    const body = JSON.parse(payload.body);
    const name = body.displayName;
    const img = body.displayImg;
    console.log(`${name} 사장님이 공지사항을 업로드 하셨어요!`);
    console.log(`${img} 사장님 이미지 링크`);
}

function registerNewsFeed(storeNo){
    stompClient.send("/app/notice/newsFeed", {}, storeNo);
}

function subscribeAddress(address) {
    stompClient.subscribe('/topic/chat/' + address, function (chan) {
        var channel = chan.body;
        console.log("채널에 연결되었습니다: " + channel)
        if (!subscribedChannels[channel]) {
            subscribeToChannel(channel, address);
        }
    });
}


function updateMessageStatus(messageNo) {
    fetch('/chat/updateMessage?messageNo=' + messageNo, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if (!response.ok) throw new Error("메세지 수신처리 실패")
    })
        .catch(error => {
            console.error("/chat/updateMessage 호출 중 에러 발생 .." + error)
        })
}

function subscribeToChannel(channel, address) {
    if (!subscribedChannels[channel]) {
        stompClient.subscribe('/queue/chat/' + channel, function (message) {
            console.log('메세지 큐 -> : ' + message.body);
            handleReceivedMessage(message, channel, address);
        });
        subscribedChannels[channel] = true;
    }
}

//qwer@gmail.com -> /queue/chat/qwer@gmail.com281 ->

function isValidJSON(str) {
    try {
        JSON.parse(str);
    } catch (e) {
        return false;
    }
    return true;
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

function connectToChannelWithOutLoginCheck(subscription, address) {
    if (!address) {
        console.log("로그인 상태가 아닙니당.");
        return
    }
    const match = subscription.match(/(.*?)(\d+)$/);
    if (match) {
        const customer = match[1];
        const storeNo = match[2];
        stompClient.send("/app/chat/subRequest", {}, JSON.stringify({
            customerId: customer,
            storeNo: storeNo,
        }));
    }
}


function connectToChannel(subscription, address) {
    const match = subscription.match(/(.*?)(\d+)$/);
    if (match) {
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

function startChat(customer, storeNo, address) {
    const url = '/chat/room?customer=' + customer + '&storeNo=' + storeNo
    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if (response.status===404) {
            console.log("채팅기록 없음")
            connectToChannelWithOutLoginCheck((customer + storeNo), address)
            showChatRoom([], (customer + storeNo), address);
            return;
        } else if(!response.ok) throw new Error("Start Chat 호출 중 에러 발생 !! : ")
        return response.json();
    })
        .then(data => {
            connectToChannelWithOutLoginCheck((customer + storeNo), address);
            showChatRoom(data, (customer + storeNo), address); // 모달&메세지표시
        })
        .catch(error => {
            console.error(error);
            //showLoginModal();
        });
}


function showLoginModal() {
    const modalHtml = `
        <div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="loginModalLabel">로그인 필요</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        채팅을 하려면 로그인이 필요합니다. 로그인해 주세요.
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" onclick="redirectToLogin()">로그인</button>
                    </div>
                </div>
            </div>
        </div>
    `;

    document.body.insertAdjacentHTML('afterbegin', modalHtml);
    var loginModal = new bootstrap.Modal(document.getElementById('loginModal'));
    loginModal.show();
}

function redirectToLogin() {
    window.location.href = '/signin';
}

//messages, subscription, target 281
function showChatRoom(messages, subscription, address) {
    if (!Array.isArray(messages) || messages.length === 0) {
        messages = [{
            messageNo: 0,
            content: '첫 방문 고객! 환영합니다',
            senderId: null,
            createdAt: null,
            isReceived: true,
            chatRoomNo: null
        }];
    }
    const match = subscription.match(/(.*?)(\d+)$/);
    const customer = match[1];
    const storeNo = match[2];
    const target = (customer === address) ? customer : storeNo;

    let identifier = target === customer ? storeNo : customer;

    chatroomTitle(identifier).then(() => {
        console.log("😋target: " + address);
        var chatMessagesContainer = document.getElementById('chat-messages');
        chatMessagesContainer.innerHTML = ''; // 초기화
        let avatarImg = document.querySelector("#chat-avatar img");
        messages.forEach(function (message) {
            showMessage(message, address, avatarImg);
        });
    });

    var chatRoomModalElement = document.getElementById('chatRoomModal');
    chatRoomModalElement.setAttribute('data-subscription', subscription);
    var chatRoomModal = new bootstrap.Modal(chatRoomModalElement);
    chatRoomModal.show();
    let sendButton = document.getElementById('send-button')

    document.getElementById('send-button').setAttribute('onclick', `sendMessage('${subscription}', '${target}')`);
    chatMessagesContainer.scrollTop = chatMessagesContainer.scrollHeight;
}

function chatroomTitle(identifier) {
    return fetch("/chat/getDisPlayName?identifier=" + identifier)
        .then(response => response.json())
        .then(data => {
            document.getElementById("chatRoomModalLabel").innerText = data.displayName;
            document.getElementById("chat-avatar").innerHTML = `<img src="` + data.displayImg + `" alt="Avatar" style="width:100%;">`;
        })
}


async function getDisplay(identifier) {
    fetch("/chat/getDisPlayName?identifier=" + identifier)
        .then(response => response.json())
        .then(data => {
            return {
                displayName: data.displayName,
                displayImg: data.displayImg
            }
        }).catch(error => {
        console.error("디스플레이 네임을 찾던 중 오류 발생 : " + error)
        return {
            displayName: null,
            displayImg: null
        }
    })
}


function showMessage(message, sender, avatarImg) {
    console.log("new Avatar : " + avatarImg);
    console.log("new Avatar SRC : " + avatarImg.src)
    if (message.messageNo === 0) {
        let messageElement = document.createElement('div');
        messageElement.className = 'chat-message received';
        messageElement.innerHTML = `
             <div class="chat-avatar">
                <img src=" ` + avatarImg.src + ` " alt="Avatar">
            </div>
            <div class="message-content">
                <div>${message.content}</div>
                <div class="chat-time"></div>
            </div>
        `;
        document.getElementById('chat-messages').appendChild(messageElement);
        document.getElementById('chat-messages').scrollTop = document.getElementById('chat-messages').scrollHeight;
        return;
    }

    let messageNo = message.messageNo;
    let messageElement = document.createElement('div');
    let avatarElement = document.getElementById("chat-avatar");
    let chatRoomModalLabelElement = document.getElementById("chatRoomModalLabel");
    let date = new Date(message.createdAt);
    let hours = date.getHours();
    let minutes = date.getMinutes().toString().padStart(2, '0');
    let ampm = hours >= 12 ? '오후' : '오전';
    let formattedTime = `${ampm} ${hours % 12 || 12}:${minutes}`;
    let chatRoomNo = message.chatRoomNo;
    let isReceived = message.isReceived;
    let isCurrentUser = message.senderId === sender;
    let avatarSrc = isCurrentUser ? message.picture : "/img/00.jpg";
    messageElement.className = 'chat-message ' + (isCurrentUser ? 'sent' : 'received');
    messageElement.innerHTML = `
        ${!isCurrentUser ? `
        <div class="chat-avatar">
            <img src=" ` + avatarImg.src + ` " alt="Avatar">
        </div>` : ''}
        <div class="message-content">
            <div>${message.content.replace(/\n/g, '<br>')}</div>
            <div class="chat-time">${message.createdAt ? formattedTime : ''}
            ${isCurrentUser && isReceived ? '<span class="read-receipt"></span>' : ''}
            </div>
        </div>
    `;

    document.getElementById('chat-messages').appendChild(messageElement);
    document.getElementById('chat-messages').scrollTop = document.getElementById('chat-messages').scrollHeight;

    // avatarElement와 chatRoomModalLabelElement 설정
    //avatarElement.innerHTML = `<img src="${message.picture}" alt="Avatar" style="width:100%;">`;
    //chatRoomModalLabelElement.textContent = message.displayName;
}

function sendMessage(subscription, address) {
    var messageInput = document.getElementById("message-input");
    var content = messageInput.value;
    // let sendButton = document.getElementById("send-button");
    if (!content) {
        return;
    }

    //address = target (sendButton 에서 넘어옴) //검증 결과 -> qwer@gmail.com
    console.log("sendMessage 의 address : " + address)

    const match = subscription.match(/(.*?)(\d+)$/);
    const customer = match[1];
    const storeNo = match[2];
    const recentUser = (address === customer) ? customer : storeNo;


    console.log("resentUser 검증 : address = " + address + " , customer = " + customer + " , storeNo = " + storeNo + " recentUser = " + recentUser);
    //qwer@gmail.com  / 281

    fetch('/chat/chatRoomNoBySubscription?customer=' + customer + '&storeNo=' + storeNo)
        .then(response => {
            if (!response.ok) console.log("구독주소로 chatRoomNo 조회 실패 : ")
            return response.json()
        })
        .then(data => {
            console.log("chatRoomNo : " + data)
            chatRoomNoBySubscription(content, recentUser, data);
        })
        .catch(error => {
            console.error("/chat/chatRoomNoBySubscription 호출 중 에러 발생 : " + error)
        })
    messageInput.value = '';
}


function chatRoomNoBySubscription(content, address, data) {
    console.log("sendRequest 브로커에 요청한 내역 : content -> " + content + ", address : " + address + ", data : " + data)
    stompClient.send('/app/chat/sendRequest', {}, JSON.stringify({
        content: content,
        senderId: address,
        chatRoomNo: data
    }))
    //senderId => 브로커가 뿌리는 데이터는 기본적으로 sender 와 receiver 둘 다 동일한 데이터를 뿌려준다.
    //수신하는 입장에서 senderId 를 통해 자신이 발신한 메세지인지 수신한 메세지인지 검증하기 위한 데이터.
    //그럼 애초에 사진+ 표시할 이름까지도 보내는게 나을듯?
    //
}


function handleReceivedMessage(message, channel, address) {
    console.log("handle message : " + message)
    const messageBody = JSON.parse(message.body);
    console.log(" -> 핸들러 accept : ", messageBody);
    const match = channel.match(/(.*?)(\d+)$/);
    const customer = match[1];
    const storeNo = match[2];

    const target = (customer === messageBody.senderId) ? customer : storeNo;

    //showMessage(messageBody, address);

    let identifier = address === customer ? storeNo : customer;
    //chatRoomTitle을  address 로 변경 -> address = !address 와 동일하게 변경
    chatroomTitle(identifier).then(() => {
        console.log("😋handleReceivedMessage Target : " + address);
        console.log("수신자 message.senderId : " + messageBody.senderId);
        let avatarImg = document.querySelector("#chat-avatar img");
        showMessage(messageBody, address, avatarImg);
        // 여기선 초기화가 필요없지않나?

        // 모달 창 활성화 확인
        let chatRoomModal = document.getElementById('chatRoomModal');
        let isModalShown = chatRoomModal.classList.contains('show');
        if (!isModalShown) {
            showNotification(messageBody);
        }
    });
    loadChatRooms()
}


function showNotification(message) {
    let notification = document.createElement('div');
    let displayName = document.getElementById("chatRoomModalLabel").innerText;
    let displayImg = document.querySelector("#chat-avatar img").src;
    console.log("showNoti : " + message.senderId + " : " + message.content);
    notification.className = 'chat-notification';
    notification.innerHTML = `
        <div class="chat-notification-content">
            <img src="`+ displayImg +`" alt="Profile Picture">
            <span>` + displayName + `</span>
            <span class="message">` + message.content + `</span>
        </div>
    `;
    document.body.appendChild(notification);
    // 클릭 이벤트 추가
    notification.addEventListener('click', function () {
        console.log("addEventListener : " + message.content)
        openChatRoomModal(message);
    });
    setTimeout(function () {
        notification.remove();
    }, 3000);
}


function openChatRoomModal(message, username) {
    let sender = message.senderId;
    let chatRoomNo = message.chatRoomNo;
    console.log("message" + chatRoomNo + " sender : " + sender);
    console.log("messageBody" + message.chatRoomNo)
    fetch('/chat/subscription?chatRoomNo=' + chatRoomNo)
        .then(Response => {
            if (!Response.ok) throw new Error("구독목록 조회에 실패!") //response
            return Response.text()
        }).then(data => {
        let match = data.match(/(.*?)(\d+)$/);
        const customer = match[1];
        const storeNo = match[2];
        const target = (customer === sender) ? storeNo : customer;
        connectModalToChatRoom(chatRoomNo, data, target);
    }).catch(error => {
        console.error("/chat/subscsription 호출 에러" + error)
    })

    // fetch('/chat/chatRoomNo?chatRoomNo='+ chatRoomNo)
    //     .then(response=>{
    //         if(!response.ok) throw new Error("메세지 조회 실패!")
    //     }).then(data=>{
    //
    // })
}

function openChatRoomMyPage(message, myAddress) {

    fetch('/chat/subscription?chatRoomNo=' + message.chatroomNo)
        .then(Response => {
            if (!Response.ok) throw new Error("구독목록 조회에 실패!") //response
            return Response.text()
        }).then(data => {
        let match = data.match(/(.*?)(\d+)$/);
        const customer = match[1];
        const storeNo = match[2];
        //const target = (customer === myAddress) ? storeNo : customer;
        connectToChannelWithOutLoginCheck((customer + storeNo), myAddress);
        connectModalToChatRoom(message.chatroomNo, data, myAddress);
    }).catch(error => {
        console.error("/chat/subscsription 호출 에러" + error)
    })
}

function connectModalToChatRoom(chatRoomNo, subscription, address) {

    let match = subscription.match(/(.*?)(\d+)$/);
    const customer = match[1];
    const storeNo = match[2];
    const target = (customer === address) ? storeNo : customer;

    console.log("connectModalToChatRoom target -> : " + address)

    fetch('/chat/chatRoomNo?chatRoomNo=' + chatRoomNo)
        .then(response => {
            if (!response.ok) {
                return []
            }
            return response.json()
        }).then(messages => {
        showChatRoom(messages, subscription, address)
    }).catch(error => {
        console.log("/chat/chatRoomNo 호출 중 예외 " + error);
    })
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

document.addEventListener('DOMContentLoaded', function () {
    var chatRoomModalElement = document.getElementById('chatRoomModal');
    var chatRoomModal = new bootstrap.Modal(chatRoomModalElement);

    var closeButton = chatRoomModalElement.querySelector('.btn-close');
    closeButton.addEventListener('click', function () {
        chatRoomModal.hide();
    });

    const scrollContainer = document.getElementById('chat-messages');
    scrollContainer.scrollTop = 20000;


    var input = document.getElementById('message-input');
    var sendButton = document.getElementById('send-button');


    input.addEventListener('keypress', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            sendButton.click();
        }
    });

    // 메시지 전송 함수
    sendButton.onclick = function () {
        var message = input.value;
        if (message.trim() !== '') {
            sendMessage('${subscription}', '${target}')
            input.value = '';
        }
    };

});

document.getElementById('loginModal').addEventListener('hidden.bs.modal', function (event) {
    event.target.remove();
});