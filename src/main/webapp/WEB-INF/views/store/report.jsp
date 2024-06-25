<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/store/report/${storeNo}" method="post">
		<label>가게 신고</label>
		<input type="text" name="title" placeholder="제목">
		<textarea name="content" placeholder="신고 내용"></textarea>
		<input type="hidden" name="storeNo" value="${storeNo}"/>
		<input type="submit" value="신고">
	</form>
</body>
</html>