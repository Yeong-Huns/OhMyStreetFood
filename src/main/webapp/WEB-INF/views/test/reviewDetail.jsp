<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Review Detail Test</title>
</head>
<body>
	<main class="container">
		<div class="main-container">
			<c:if test="${empty review }">
				등록된 리뷰가 없습니다.
			</c:if>
			<c:if test="${!empty review}">
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
						
						<tr>
							<td>${review.memberUsername}</td>
							<!-- url태그 -->
							<td>${review.content}</a></td>
							<td>${review.rating}</td>
							<td>${review.createdAt}</td>
						</tr>				
					</tbody>
				</table>
				
				<a href="<c:url value="/test/review/${review.reviewNo}/update" />">수정</a>
				<a href="<c:url value="/test/review/${review.reviewNo}/delete" />" onclick="confirm('삭제하시겠습니까?')">삭제</a>
			</c:if>

		</div>
	</main>
</body>
</html>