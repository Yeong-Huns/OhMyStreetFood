<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
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
	<link href="https://fonts.googleapis.com/css2?family=Chakra+Petch:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
    <!-- CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light fixed-bottom">
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav d-flex justify-content-between w-100">
				<li class="nav-item active"><a class="nav-link" href="${pageContext.request.contextPath}/">
						<i class="fas fa-home"></i><br>홈
				</a></li>
				<li class="nav-item">
					<a class="nav-link" href="${pageContext.request.contextPath}/store/search">
						<i class="fas fa-search"></i><br>검색
					</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" id="storeLink" href="${pageContext.request.contextPath}/store/list">
						<i class="fas fa-store"></i><br>점포
					</a>
				</li>
				<sec:authorize access="isAnonymous()">
					<li class="nav-item">
						<a class="nav-link" href="${pageContext.request.contextPath}/signin">
							<i class="fas fa-user"></i><br>로그인
						</a>
					</li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="nav-item">
						<a class="nav-link" href="${pageContext.request.contextPath}/mypage">
							<i class="fas fa-user"></i><br>마이페이지
						</a>
					</li>
				</sec:authorize>
			</ul>
		</div>
	</nav>
	
	<!-- 위도, 경도값 적용 JS -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/sessionStorage.js"></script>
    
</body>
</html>