<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OhMyStreetFood!</title>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light fixed-bottom" style="max-width: 800px; background-color: white; margin: 0 auto; padding: 0 20px; text-align: center; border-top: 1px solid #ccc;">
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav d-flex justify-content-between w-100">
				<li class="nav-item active"><a class="nav-link" href="./">
						<i class="fas fa-home"></i><br>홈
				</a></li>
				<li class="nav-item">
					<a class="nav-link" href="./search">
						<i class="fas fa-search"></i><br>검색
					</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="./store/list">
						<i class="fas fa-store"></i><br>점포
					</a>
				</li>
				<sec:authorize access="isAnonymous()">
					<li class="nav-item">
						<a class="nav-link" href="./signin">
							<i class="fas fa-user"></i><br>로그인
						</a>
					</li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="nav-item">
						<a class="nav-link" href="./mypage">
							<i class="fas fa-user"></i><br>마이페이지
						</a>
					</li>
				</sec:authorize>
			</ul>
		</div>
	</nav>
</body>
</html>