<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
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
	<link href="https://fonts.googleapis.com/css2?family=Chakra+Petch:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
    <!-- CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.theme.default.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/spinner.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/customOverlay.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/chatbot.css">
</head>
<body>
    <!-- chatbot -->
    <button class="image-button" id="chatbot-button">
       	<img src="${pageContext.request.contextPath}/img/chatbot_icon1.png" alt="Button Image">
   	</button>
	
    <div id="chatbot-popup" class="popup">
        <div class="popup-content">
            <span class="chatbot-close-button">&times;</span>
            <h2>Chat with our bot</h2>
            <div class="modal-body chat-messages" id="chat-messages">
                <!-- 채팅 메시지 표시 영역 -->
            </div>
            <div class="modal-footer chat-footer">
                <input type="text" id="message-input" class="form-control message-input" placeholder="메시지를 입력하세요...">
                <button class="btn btn-primary send-button" id="send-button">전송</button>
            </div>
        </div>
    </div>
   
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/chatbot.js"></script>
</body>
</html>
