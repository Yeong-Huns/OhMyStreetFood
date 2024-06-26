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
				<a href="javascript:history.go(-1);"
					style="text-decoration: none; color: inherit;"> <i
					class="fas fa-arrow-left"></i>
				</a>
			</div>
			<div class="col-md-12 text-center" id="title">
				<h3>로그인</h3>
			</div>
			<form action="${pageContext.request.contextPath}/signin" method="post">
				<div class="form-group">
					<label for="username">이메일 주소</label> 
					<input type="email" class="form-control" id="username" name="username" aria-describedby="emailHelp" placeholder="Email">
				</div>
				<div class="form-group">
					<label for="password">비밀번호</label> 
					<input type="password" class="form-control" id="password" name="password" placeholder="Password">
				</div>

				<c:if test="${param.error}">
					<div class="alert alert-danger">
                		사용자ID 또는 비밀번호를 확인해 주세요.
            		</div>
				</c:if>
				<div class="col-md-12">
					<button type="submit" class="btn btn-primary"
						style="height: 50px; width: 100%; margin-bottom: 20px;">로그인하기</button>
					<button type="button" onclick="window.location.href='${pageContext.request.contextPath}/signup/general';" class="btn btn-outline-primary"
						style="height: 50px; width: 100%; margin-bottom: 20px;">회원가입하기</button>
				</div>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
			<div class="col-md-12 text-center">
				<a href="${pageContext.request.contextPath}/findPassword">비밀번호 찾기</a>				
			</div>
		</div>
	</div>

	<!-- Bootstrap JS -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script>
        window.onload = function() {
            var success = '${success}';
            if (success === true) {
                alert('회원가입이 완료되었습니다.');
            }
        }
	</script>
</body>
</html>
