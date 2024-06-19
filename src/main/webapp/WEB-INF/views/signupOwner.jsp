<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
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
			<div class="col-md-12 text-center" id="logo">
				<h3>사장님 회원가입</h3>
			</div>
			<form action="${pageContext.request.contextPath}/signupOwner" method="post">
				<div class="form-group">
					<label for="email">이메일 주소</label>
					<input type="email" class="form-control" id="email" name="username" aria-describedby="emailHelp" placeholder="Email" required>
				</div>
				<div class="form-group">
					<label for="password">비밀번호</label>
					<input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
				</div>
				<div class="form-group">
					<label for="confirmPassword">비밀번호 확인</label>
					<input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Confirm Password" required>
				</div>
				<div class="col-md-12">
					<button type="submit" class="btn btn-primary" style="height: 50px; width: 100%; margin-bottom: 10px;">회원가입</button>
				</div>
			</form>
		</div>
	</div>

	<!-- Bootstrap JS -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
