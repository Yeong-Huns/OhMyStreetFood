<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/logout" method="post">
		<input type="submit" value="logout">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form> 
</body>
</html>