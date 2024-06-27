<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../chat/chatHandler.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <title>Chat Room List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

</head>

<body>
<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#chatRoomModal" onclick="startChat(senderId, 0)">
    채팅 시작
</button>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
<!-- JavaScript로 값을 전달하기 위해 HTML 내에서 변수를 설정 -->
<c:set var="username" value="${pageContext.request.userPrincipal.name}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
    var receiverId = "sonjoung@gmail.com";
    var senderId = "${username}"; // JSP 태그를 통해 값 할당
    var contextPath = "${contextPath}";
    document.addEventListener("DOMContentLoaded", function() {
        console.log("Sender ID: " + senderId);
        console.log("Receiver ID: " + receiverId);
        console.log("contextPath : " + contextPath);
    });
</script>

</body>

</html>
