<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="chat/chatHandler.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>OhMyStreetFood!</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <!-- Font Awesome CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
    <!-- CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/like.css">
    <!-- JQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    
    <style>
    .section-box {
        width: 100%;
        height: auto;
        background-color: #f6f6f6;
        border-radius: 10px;
        margin-bottom: 20px;
        padding: 20px;
    }
	
    .custom-chat-room {
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-bottom: 10px;
        padding-bottom: 10px;
        border-bottom: 1px dotted #ccc;
        border-radius: 0;
    }

    .custom-chat-room img {
        margin-right: 10px;
        border-radius: 50%;
        width: 50px;
        height: 50px;
    }

    .custom-chat-room div {
        display: flex;
        flex-direction: column;
        justify-content: center;
    }

    .custom-chat-room:last-child {
        border-bottom: none;
    }

    .custom-chat-room p {
        margin: 0;
    }

    .hidden {
        display: none;
    }

    .btn-float-right {
        float: right;
        margin-top: -8px;
    }
        .custom-modal {
        position: fixed;
        top: 0;
        left: 0;
        z-index: 1055;
        width: 100%;
        height: 100%;
        overflow-x: hidden;
        overflow-y: auto;
        outline: 0;
    }

    .custom-modal-dialog {
        position: relative;
        width: auto;
        margin: 1.75rem auto;
        max-width: 500px; /* 모달의 최대 너비 설정 */
    }

    .modal-content {
    	width: ;
        height: 500px; /* 모달의 최대 높이를 화면 높이의 80%로 설정 */
        overflow-y: auto; /* 내용이 넘칠 경우 스크롤 생성 */
    }

    @media (max-width: 576px) {
        .custom-modal-dialog {
            margin: 1rem;
        }
    }
    </style>
</head>
<body>
<div class="container" style="padding: 60px 40px 0px 40px;">
	<div class="row justify-content-center">
	    <div class="col-md-10">
	        <div style="width:100%;">
	            <i class="fas fa-store"></i>&nbsp;<strong>점포별 공지사항</strong>
	        </div>
	        <div style="width:100%; height:auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;">
	            
	            <c:forEach items="${notices}" var="notice">
					<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#noticeModal${notice.noticeNo}"
						data-notice-id="${notice.noticeNo}"  style="text-decoration-line: none; color: black;">
						<div class="custom-chat-room">
		                    <img src="${ notice.storePicture }" width="50" height="50">
		                    <div>
		                        <p><strong>${notice.title}</strong></p>
		                        <p>${fn:substring(notice.content, 0, 50)}${fn:length(notice.content) > 50 ? '...' : ''}</p>
		                    	 <small>
					                <fmt:formatDate value="${notice.createdAt}" pattern="yyyy-MM-dd" />
					            </small>
		                    </div>
	                	</div>
                	</a>
                	
                	 <!-- 각 공지사항에 모달 -->
				    <div class="modal fade custom-modal" id="noticeModal${notice.noticeNo}" tabindex="-1" aria-labelledby="noticeModalLabel${notice.noticeNo}" aria-hidden="true">
					    <div class="modal-dialog modal-dialog-centered custom-modal-dialog">
					        <div class="modal-content">
					            <div class="modal-header">
					                <h5 class="modal-title" id="noticeModalLabel${notice.noticeNo}">공지사항 상세</h5>
					                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					            </div>
					            <div class="modal-body">
					                <h3>${notice.title}</h3>
					                <p>${notice.content}</p>
					                <small><fmt:formatDate value="${notice.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></small>
					            </div>
					            <div class="modal-footer">
					                <form action="${pageContext.request.contextPath}/deleteNotice" method="post">
					                    <input type="hidden" name="noticeId" value="${notice.noticeNo}">
					                    <button type="button" class="btn btn-danger" onclick="deleteNotice(${notice.noticeNo})">삭제</button>
					                </form>
					                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
					            </div>
					        </div>
					    </div>
					</div>
				</c:forEach>
	            
	        </div>
	
	        <div style="width:100%;">
	            <i class="fa fa-comments"></i>&nbsp;<strong>나의 채팅방</strong>
	            <button id="show-more-chatrooms" class="btn btn-primary btn-float-right hidden">더보기</button>
	        </div>
	        <div class="section-box" id="custom-chat-room-container">
	        </div>
	
	    </div>
	
	    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	
	</div>
</div>

	<sec:authorize access="isAuthenticated()">
	    <!-- like 요청 -->
	    <script src="${pageContext.request.contextPath}/js/likeRequest.js"></script>
	</sec:authorize>

	<!-- Menu -->
    <jsp:include page="menu.jsp"/>
    
    <!-- Bootstrap JS -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
        <script>
        function deleteNotice(noticeId) {
            if (confirm('정말로 이 공지사항을 삭제하시겠습니까?')) {
                fetch('/notice/delete', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ noticeId: noticeId })
                })
                .then(response => {
                    if (response.ok) {
                    	alert('공지사항이 성공적으로 삭제되었습니다.');
                    	location.reload();
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert(error.message);
                });
            }
        }
        
        
        $(document).ready(function () {
        	$("#confirmPasswordBtn").click(function () {
                var password = $("#password").val();
                $.ajax({
                    type: "POST",
                    url: "./confirmPassword",
                    data: {
                        password: password
                    },
                    success: function (result) {
                        if (result === true) {
                            window.location.href = "./modifyMember";
                        } else {
                            $("#confirmPasswordAlert").text("비밀번호가 일치하지 않습니다.");
                        }
                    },
                    error: function (xhr, status, error) {
                        $("#confirmPasswordAlert").text("서버 오류입니다. 다시 시도해 주세요.");
                    }
                });
            });
            $("#password").on("input", function () {
                $("#confirmPasswordAlert").text("");
            });
            
            $(".delete-btn").click(function(){
            	if(confirm("정말로 삭제하시겠습니까?") == true){
            		var storeNo = Number($(this).data('store-no'));
            		console.log("삭제할 가게번호 : " + storeNo);
            		
            		$.ajax({
            			url : '/store/delete/' + storeNo,
            			type : 'DELETE',
            			success : function(data, textStatus, xhr){
            				if (textStatus === "success") {
                                console.log("삭제 성공");
                                alert("가게가 성공적으로 삭제되었습니다.");
                                location.reload();
                            } else {
                                console.log("삭제 실패");
                                alert("삭제에 실패했습니다. 다시 시도해주세요.");
                            }
            				
            			},
            			error : function(xhr, status, error){
            				if (xhr.status === 400) {
                                console.error("Bad Request: " + xhr.responseText);
                                alert("삭제할 권한이 없습니다.");
                            } else {
                                console.error("AJAX Error: " + status + " " + error);
                                alert("서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
                            }
            				
            			}
            		});
            	} else{
            		return false;
            	} 
            	
            });
            
            $('#show-more-chatrooms').off('click').on('click', function() {
                const hiddenRooms = $('.custom-chat-room.hidden');
                if (hiddenRooms.length > 0) {
                    hiddenRooms.css('display', 'flex').hide().slideDown().removeClass('hidden');
                    $(this).text('접기');
                } else {
                    $('.custom-chat-room').each(function(index) {
                        if (index >= 5) {
                            $(this).slideUp(function() {
                                $(this).css('display', 'none').addClass('hidden');
                            });
                        }
                    });
                    $(this).text('더보기');
                }
            });
        });


        document.addEventListener("DOMContentLoaded", function () {
            loadChatRooms();
        });


        function loadChatRooms(){
            fetch(`/chat/getChatRoomsWithMessage?username=${username}`)
                .then(response => response.json())
                .then(data => {
                    const chatRoomContainer = document.getElementById('custom-chat-room-container');
                    chatRoomContainer.innerHTML = '';

                    data.forEach((chatRoom, index) => {
                        const chatRoomElement = document.createElement('div');
                        chatRoomElement.className = 'custom-chat-room';
                        if (index >= 5) chatRoomElement.classList.add('hidden');
                        chatRoomElement.innerHTML = `
                    <img src="` + chatRoom.img + `" alt="Avatar" width="50" height="50">
                    <div>
                        <p><strong>` + chatRoom.target + `와의 대화</strong></p>
                        <p>` + chatRoom.lastMessage + `</p>
                        <input type="hidden" value="` + chatRoom.chatroomNo + `">
                    </div>
                `;
                        chatRoomElement.onclick = function () {
                            const principal = '${pageContext.request.userPrincipal.name}';
                            if (principal) {
                                console.log("조회 : " +  chatRoom.chatroomNo)
                                openChatRoomMyPage(chatRoom, chatRoom.myAddress);
                            } else {
                                console.error("로그인 정보가 없습니다.");
                            }
                        };
                        chatRoomContainer.appendChild(chatRoomElement);
                    });

                    var showMoreButton = document.getElementById('show-more-chatrooms');
                    if (data.length > 5) {
                        showMoreButton.style.display = 'block'; // 더보기 버튼 보이기
                    } else {
                        showMoreButton.style.display = 'none'; // 더보기 버튼 숨기기
                    }
                })
                .catch(error => console.error('Error fetching chat rooms:', error));
        }
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/notice.js"></script>
</body>
<!-- jQuery UI JS -->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
</html>