<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>OhMyStreetFood!</title>
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<!-- Font Awesome CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<!-- Google Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap"
	rel="stylesheet">
<!-- CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
	<div class="main">
		<div class="row">
			<div>
				<a href="javascript:history.go(-1);" style="text-decoration: none; color: inherit;"> 
					<i class="fas fa-arrow-left"></i>
				</a>
			</div>
			<div class="col-md-12 text-center" id="title">
				<h3>마이페이지</h3>
			</div>
			
			<div align="center" style="margin-bottom: 20px;">
				<img src="${pageContext.request.contextPath}/img/00.jpg" class="card-img-top rounded-circle" alt="사진" style="max-width: 120px; height: auto;">
				<p style="margin-top: 20px;">
					<a href="#">사진 수정</a>
				</p>
			</div>
			
			<div style="width:100%; height:auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;">
				<i class="fas fa-store"></i>&nbsp;<strong>내가 등록한 가게</strong>
				<p style="padding: 20px 0 0 20px;">
					마약닭꼬치
				</p>
				<p style="padding: 20px 0 0 20px;">
					하늘솜사탕
				</p>
			</div>
			
			<div style="width:100%; height:auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;">
				<i class="fas fa-heart"></i>&nbsp;<strong>내가 찜한 가게</strong>
				<p style="padding: 20px 0 0 20px;">
					마약닭꼬치
				</p>
				<p style="padding: 20px 0 0 20px;">
					하늘솜사탕
				</p>
			</div>
			
			<div style="width:100%; height:auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;">
				<i class="fa fa-pen"></i>&nbsp;<strong>내가 쓴 리뷰</strong>
				<p style="padding: 20px 0 0 20px;">
					마약닭꼬치
				</p>
				<p style="padding: 20px 0 0 20px;">
					하늘솜사탕
				</p>
			</div>
			
			<div style="width:100%; height:auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;">
				<i class="fa fa-comments"></i>&nbsp;<strong>나의 채팅방</strong>
				<p style="padding: 20px 0 0 20px;">
					마약닭꼬치
				</p>
				<p style="padding: 20px 0 0 20px;">
					하늘솜사탕
				</p>
			</div>
			
			<div class="col-md-12 text-center">
				<a href="#">회원 정보 수정</a><br>
				<a href="#">회원 탈퇴</a>
				<a href="${pageContext.request.contextPath}/logout">로그아웃</a>
			</div>
			
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</div>
	</div>
</body>
</html>
