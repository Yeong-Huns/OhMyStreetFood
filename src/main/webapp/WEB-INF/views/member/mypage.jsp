<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../chat/chatHandler.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <style>
        /*.custom-chat-room {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
            padding-bottom: 10px;
            border-bottom: 1px solid #ccc;
            cursor: pointer;
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
        }

        .custom-chat-room p {
            margin: 0;
        }*/
        .section-box {
            width: 100%;
            height: auto;
            background-color: #f6f6f6;
            border-radius: 10px; /* 전체 컨테이너의 테두리를 둥글게 */
            margin-bottom: 20px;
            padding: 20px;
        }

        .custom-chat-room {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 10px;
            padding-bottom: 10px;
            border-bottom: 1px solid #ccc; /* 구분선을 직선으로 유지 */
            border-radius: 0; /* 구분선에 대해 둥근 모서리 효과 없음 */
        }

        .custom-chat-room img {
            margin-right: 10px;
            border-radius: 50%; /* 이미지는 둥글게 유지 */
            width: 50px;
            height: 50px;
        }

        .custom-chat-room div {
            display: flex;
            flex-direction: column;
        }

        .custom-chat-room:last-child {
            border-bottom: none;
        }

        .custom-chat-room p {
            margin: 0;
        }

    </style>
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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

    <script>
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
        });
    </script>
</head>
<body>
<div class="col-md-12">
    <img src="${pageContext.request.contextPath}/img/logo.png" style="width: 200px">
</div>

<div class="main">
    <div class="row justify-content-center">
        <div class="col-md-10">
            <div class="col-md-12 text-center" id="title">
                <h2>마이페이지</h2>
            </div>

            <div align="center" style="margin-bottom: 20px;">
                <sec:authentication property="principal.username" var="username"/> <br>
                <input type="hidden" id="memberUsername" value="${username }">
                <sec:authorize access="hasRole('ROLE_OWNER')">${username }</sec:authorize>
                <sec:authorize access="hasRole('ROLE_USER')">
                    ${member.nickName } <br>
                    ${username }
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    관리자 계정입니다
                </sec:authorize>
            </div>

            <div style="width:100%;">
                <i class="fas fa-store"></i>&nbsp;<strong>내가 등록한 가게</strong>
            </div>
            <div style="width:100%; height:auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;">
                <c:forEach items="${registeredStores}" var="store" varStatus="status">
                    <p style="display: flex; flex-direction: row; justify-content: space-between; padding: 20px 0 0 20px;">
                        <a href="${pageContext.request.contextPath}/store/${store.storeNo} ">${store.storeName}</a>
                    </p>
                    <hr/>
                </c:forEach>
            </div>

            <sec:authorize access="hasRole('ROLE_USER')">
                <div style="width:100%;">
                    <i class="fas fa-heart"></i>&nbsp;<strong>내가 찜한 가게</strong>
                </div>
                <div style="width:100%; height:auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;">
                    <c:forEach items="${likeStores}" var="store" varStatus="status">
                        <p style="display: flex; flex-direction: row; justify-content: space-between; padding: 20px 0 0 20px;">
                            <a href="${pageContext.request.contextPath}/store/${store.storeNo} ">${store.storeName}</a>
                            <i class="like-btn far fa-heart" data-store-no="${store.storeNo}"></i>
                        </p>
                        <hr/>
                    </c:forEach>
                </div>

                <div style="width:100%;">
                    <i class="fa fa-pen"></i>&nbsp;<strong>내가 쓴 리뷰</strong>
                </div>
                <div style="width:100%; height:auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;">
                    <c:forEach items="${reviews}" var="review" varStatus="status">
                        <c:set var="loop_flag" value="true"/>
                        <c:forEach items="${reviewStores}" var="store" varStatus="status">
                            <c:if test="${review.storeStoreNo eq store.storeNo and loop_flag}">
                                <p style="display: flex; flex-direction: row; justify-content: space-between; padding: 20px 0 0 20px;">
                                <a href="${pageContext.request.contextPath}/store/${store.storeNo}">${store.storeName}</a>
                                <c:set var="loop_flag" value="false"/>
                            </c:if>
                        </c:forEach>
                        <span>
					    		<a href="<c:url value="/review/${review.reviewNo}?requestPage=mypage" />">${review.content}</a>
                        </p>
                        </span>
                        <hr/>
                    </c:forEach>
                </div>
            </sec:authorize>

            <div style="width:100%;">
                <i class="fa fa-comments"></i>&nbsp;<strong>나의 채팅방</strong>
            </div>
            <div class="section-box" id="custom-chat-room-container">

            </div>
        </div>

        <div class="col-md-12 text-center">
            <a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#confirmPasswordModal">회원 정보 수정</a><br>
            <a href="${pageContext.request.contextPath}/logout">로그아웃</a>
        </div>

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <div class="modal fade" id="confirmPasswordModal" tabindex="-1" aria-labelledby="confirmPasswordLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="confirmPasswordLabel">비밀번호 확인</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <input type="password" class="form-control" id="password" name="password" required>
                            <label id="confirmPasswordAlert" class="text-danger"></label>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" id="confirmPasswordBtn">확인</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<sec:authorize access="isAuthenticated()">
    <!-- like 요청 -->
    <script src="${pageContext.request.contextPath}/js/likeRequest.js"></script>
</sec:authorize>

<!-- Menu -->
<div class="row">
    <div class="col-md-12">
        <jsp:include page="../menu.jsp"/>
    </div>
</div>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        fetch(`/chat/getChatRoomsWithMessage?username=${username}`)
            .then(response => response.json())
            .then(data => {
                const chatRoomContainer = document.getElementById('custom-chat-room-container');
                chatRoomContainer.innerHTML = '';

                data.forEach(chatRoom => {
                    const chatRoomElement = document.createElement('div');
                    chatRoomElement.className = 'custom-chat-room';
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
                            openChatRoomMyPage(chatRoom, principal);
                        } else {
                            console.error("로그인 정보가 없습니다.");
                        }
                    };
                    chatRoomContainer.appendChild(chatRoomElement);
                });
            })
            .catch(error => console.error('Error fetching chat rooms:', error));
    });
</script>
</body>
<!-- jQuery UI JS -->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
</html>
