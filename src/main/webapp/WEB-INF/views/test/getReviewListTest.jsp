<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Select Review Test</title>
</head>
<body>
	<main class="container">
		<div class="main-container">
			<c:if test="${empty list }">
				등록된 리뷰가 없습니다.
			</c:if>
			<c:if test="${!empty list}">
				<table style="width:100%; border-style: solid;">
					<thead>
						<tr>
							<td>작성자</td>
							<td>내용</td>
							<td>평점</td>
							<td>작성일</td>
						</tr>
					</thead>
					<tbody>
						<!-- 반복문 -->
						<c:forEach var="review" items="${list}" >
						<tr>
							<td>${review.memberUsername}</td>
							<!-- url태그 -->
							<td><a href="#">${review.content}</a></td>
							<td>${review.rating}</td>
							<td>${review.createdAt}</td>
						</tr>
						</c:forEach>					
					</tbody>
				</table>
			</c:if>
			
			
			
			
		</div>
	</main>
</body>
</html>