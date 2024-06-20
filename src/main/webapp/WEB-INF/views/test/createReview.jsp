<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert Review Test</title>
</head>
<body>
	<!-- message 출력 -->
			<h2>리뷰 입력</h2>
			
			<!-- form 바인딩 -->
			<form:form method="post" modelAttribute="requestReview">
				<form:errors path="*" />
				<p>
					<form:hidden path="memberUsername" />
					<form:errors path="memberUsername" />
				</p>
				<p>
					<form:hidden path="storeStoreNo" />
					<form:errors path="storeStoreNo" />
				</p>
				<p>
					<label>리뷰 평점</label>
					<form:input path="rating" />
					<form:errors path="rating" />
				</p>
				<p>
					<label>내용</label>
					<form:textarea path="content" />
					<form:errors path="content" />
				</p>

				<p>
					<input type="submit" value="등록" />
				</p>
			</form:form>
</body>
</html>