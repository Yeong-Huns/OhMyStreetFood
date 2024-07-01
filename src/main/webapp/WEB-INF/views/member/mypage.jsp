<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
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
<!-- CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/like.css">

<!-- JQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<script>
    $(document).ready(function() {
        $("#confirmPasswordBtn").click(function() {
            var password = $("#password").val();

            $.ajax({
                type: "POST",
                url: "./confirmPassword",
                data: {
                    password: password
                },
                success: function(result) {
                    if (result === true) {
                        window.location.href = "./modifyMember" ;
                    } else {
                        $("#confirmPasswordAlert").text("비밀번호가 일치하지 않습니다.");
                    }
                },
                error: function(xhr, status, error) {
                	$("#confirmPasswordAlert").text("서버 오류입니다. 다시 시도해 주세요.");
                }
            });
        });
        $("#password").on("input", function() {
            $("#confirmPasswordAlert").text("");
        });
    });
</script>
</head>
<body>
			<div class="col-md-12 text-center" id="title">
				<h3>마이페이지</h3>
			</div>
			
			<div align="center" style="margin-bottom: 20px;">
				<sec:authentication property="principal.username" var="username"/> <br>
				<input type="hidden" id="memberUsername" value="${username }">
				<sec:authorize access="hasRole('ROLE_OWNER')">${username }</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
					${member.nickName } <br>
					${username } 	
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					관리자 계정입니다.
				</sec:authorize>
			</div>
			
			<div style="width:100%;">
				<i class="fas fa-store"></i>&nbsp;<strong>내가 등록한 가게</strong>
			</div>
			<div style="width:100%; height:auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;">
				<c:forEach items="${registeredStores}" var="store" varStatus="status">
					<a href="${pageContext.request.contextPath}/store/${store.storeNo} ">${store.storeName}</a> <br>
				</c:forEach>
			</div>
			
			<sec:authorize access="hasRole('ROLE_USER')">
				<div style="width:100%;">
					<i class="fas fa-heart"></i>&nbsp;<strong>내가 찜한 가게</strong>
				</div>
				<div style="width:100%; height:auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;">
					<c:forEach items="${likeStores}" var="store" varStatus="status">
						<span><i class="like-btn far fa-heart" data-store-no="${store.storeNo}"></i></span>
						<a href="${pageContext.request.contextPath}/store/${store.storeNo} ">${store.storeName}</a> <br>
					</c:forEach>
				</div>
				
				<div style="width:100%;">
					<i class="fa fa-pen"></i>&nbsp;<strong>내가 쓴 리뷰</strong>
				</div>
				<div style="width:100%; height:auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;">
					<c:forEach items="${reviews}" var="review" varStatus="status">
						<c:set var="loop_flag" value="true" />
					    <c:forEach items="${reviewStores}" var="store" varStatus="status">
					    	<c:if test="${review.storeStoreNo eq store.storeNo and loop_flag}">
					        	<a href="${pageContext.request.contextPath}/store/${store.storeNo}"> ${store.storeName}</a>
					           	<c:set var="loop_flag" value="false" />
					        </c:if>
					    </c:forEach>
					    <span><a href="<c:url value="/review/${review.reviewNo}?requestPage=mypage" />">${review.content}</a></span> <br>
					</c:forEach>
				</div>
			</sec:authorize>
			
			<div style="width:100%;">
				<i class="fa fa-comments"></i>&nbsp;<strong>나의 채팅방</strong>
			</div>
			<div style="width:100%; height:auto; background-color:#f6f6f6; border-radius:10px; margin-bottom: 20px;">
				<p style="padding: 20px 0 0 20px;">
					마약닭꼬치
				</p>
				<p style="padding: 20px 0 0 20px;">
					하늘솜사탕
				</p>
			</div>
			
			<div class="col-md-12 text-center">
				<a href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#confirmPasswordModal">회원 정보 수정</a><br>
				<a href="${pageContext.request.contextPath}/logout">로그아웃</a>
			</div>
			
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

	<!-- Menu -->
    <div class="row">
        <div class="col-md-12">
            <jsp:include page="../menu.jsp" />
        </div>
    </div>
	
	<div class="modal fade" id="confirmPasswordModal" tabindex="-1" aria-labelledby="confirmPasswordLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmPasswordLabel">비밀번호 확인</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                	<div class="mb-3">
                    	<input type="password" class="form-control" id="password" name="password" required>
                    	<label id="confirmPasswordAlert" class="text-danger"></label>
                    </div>
                </div>
                <div class="modal-footer">
                	<button type="button" class="btn btn-primary" id="confirmPasswordBtn">확인</button>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Bootstrap JS -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
	<sec:authorize access="isAuthenticated()">
		<!-- like 요청 -->
		<script src="${pageContext.request.contextPath}/js/likeRequest.js"></script>
	</sec:authorize>

	<!-- Menu -->
    <div class="row">
        <div class="col-md-12">
            <jsp:include page="../menu.jsp" />
        </div>
    </div>
</body>
</html>
