<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
<!-- Jquery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<!-- CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/review.css">

</head>
<body>			
	<!-- 모달 화면 -->
	<div id="review-detail" class="review-detail">
        <div class="modal-content">
			<div class="review-container">
				<span class="close-button">&times;</span>
				<h1>Review</h1>
				<form:form id="reviewForm" modelAttribute="review">
					<p>
						<form:hidden path="storeStoreNo" />
						<form:errors path="storeStoreNo" id="error"/>
					</p>
					<label for="username">Name:</label>
					<form:input path="memberUsername" disabled="true"/>
					<form:hidden path="memberUsername" />
					
					<label for="rating">Rating:</label>
					<div class="rating">
						<form:radiobutton path="rating" value="1" id="star1" disabled="true"/>
						<label for="star1" title="1 stars"><i class="fas fa-star"></i></label>
						<form:radiobutton path="rating" value="2" id="star2" disabled="true"/>
						<label for="star2" title="2 stars"><i class="fas fa-star"></i></label>
						<form:radiobutton path="rating" value="3" id="star3" disabled="true"/>
						<label for="star3" title="3 stars"><i class="fas fa-star"></i></label>
						<form:radiobutton path="rating" value="4" id="star4" disabled="true"/>
						<label for="star4" title="4 stars"><i class="fas fa-star"></i></label>
						<form:radiobutton path="rating" value="5" id="star5" disabled="true"/>
						<label for="star5" title="5 stars"><i class="fas fa-star"></i></label>
					</div>
					<label for="content">Review:</label>
					<form:textarea path="content" rows="5" disabled="true"/>
<!-- 					<input id="review-btn" type="submit" value="등록" /> -->
				</form:form>
			</div>
        </div>
    </div>


	<!-- 리뷰 모달 -->
	<script src="${pageContext.request.contextPath}/js/reviewDetail.js"></script>

</body>
</html>
