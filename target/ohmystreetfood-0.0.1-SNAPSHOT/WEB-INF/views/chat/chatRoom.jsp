<%--
  Created by IntelliJ IDEA.
  User: KOSA
  Date: 2024-06-19
  Time: PM 2:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chat WebSocket</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/chat/chatTest.css">

    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.0.2/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/chat/chat.js"></script>
</head>
<body onload="">
<div class="chat-container">
    <div class="input-group">
        <input type="text" id="from" class="form-control" placeholder="Choose a nickname"/>
    </div>
    <div class="input-group">
        <button id="connect" class="btn btn-primary" onclick="connect();">Connect</button>
        <button id="disconnect" class="btn btn-secondary" disabled="disabled" onclick="disconnect();">
            Disconnect
        </button>
    </div>
    <div class="input-group">
        <input type="text" height="auto" id="text" class="form-control" placeholder="Write a message..."/>
        <div id="conversationDiv" class="input-group-append">
            <button id="sendMessage" class="btn btn-success" onclick="sendMessage();">Send</button>
        </div>
    </div>
    <div class="chat-messages" >
        <p id="response"></p>
    </div>
</div>
</body>
</html>