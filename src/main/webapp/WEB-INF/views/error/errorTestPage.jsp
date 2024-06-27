<%--
  Created by IntelliJ IDEA.
  User: KOSA
  Date: 2024-06-19
  Time: PM 6:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../chat/chatHandler.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error/styles.css">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <script src="/js/error/errorTest.js"></script>
</head>
<body>

<table id="header">
    <tr>
        <td colspan="3"><h1>통합 에러 테스트 페이지입니다. </h1></td>
    </tr>
    <tr>
        <td id="appleTd">&#127823;</td>
        <td id="scoreTd">
            0
        </td>
        <td id="playTd">
            <button id="playBtn" onclick="gameStart()">▷ Play</button>
        </td>
    </tr>
</table>

<table id="content">

    <tr>
        <td id="myGame" colspan="3"></td>
    </tr>
</table>

<div id="msg"></div>
</body>
</html>
