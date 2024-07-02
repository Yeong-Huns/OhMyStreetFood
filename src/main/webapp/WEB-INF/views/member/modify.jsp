<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
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
<!-- jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<script>
	var isNickNameDuplicateChecked = false;
	
	$(document).ready(function(){
		// 닉네임 중복 확인 버튼 클릭 시
		$("#nickNameDuplicateConfirm").click(function() {
			var nickName = $("#nickName").val();
          
			// 닉네임 입력 유효성 검사
            if(nickName == '' || nickName.length == 0) {
            	$("#nickNameAlertLabel").removeClass("text-success").addClass("text-danger").text("닉네임을 입력해주세요.");
             	return false;
          	}
          
            // Ajax로 서버에 중복 확인 요청
            $.ajax({
            	url : './modifyMember/confirmNickName',
                data : {
                	nickName : nickName
              	},
                type : 'POST',
                dataType : 'json',
                success : function(result) {
                	if (result == true) {
                    	$("#nickNameAlertLabel").removeClass("text-danger").addClass("text-success").text("사용 가능한 닉네임 입니다.");
                    	isNickNameDuplicateChecked = true;
                 	} else{
                 		$("#nickNameAlertLabel").removeClass("text-success").addClass("text-danger").text("이미 사용 중인 닉네임입니다.");
                    	$("#nickName").val('');
                    	isNickNameDuplicateChecked = false;
                 	}
              	},
	            error: function(xhr, status, error) {
	            	console.error("AJAX Error: " + status + error);
	                isNickNameDuplicateChecked = false;
	            }
           });
        });
		
		// 닉네임 입력 시 경고 메시지 초기화
        $("#nickName").on('input', function() {
        	$("#nickNameAlertLabel").empty();
        	isNickNameDuplicateChecked = false;
        });
		
		// 회원 탈퇴 버튼 클릭 시
		$("#withdrawal").click(function(){
			if (confirm("정말로 탈퇴하시겠습니까?")) {
				$.ajax({
                    url: './withdrawal',
                    type: 'POST',
                    success: function(response) {
                        window.location.href = response.redirectUrl;
                    },
                    error: function(xhr, status, error) {
                        alert("서버 오류입니다. 다시 시도해 주세요.");
                    }
                });
			}
		});
	});
</script>
</head>
<body>
	<div class="main">
		<div class="col-md-12">
			<img src="${pageContext.request.contextPath}/img/logo.png" style="width: 200px">
		</div>
		
    	<div class="row justify-content-center">
        	<div class="col-md-10">
			<div class="col-md-12 text-center" id="title">
				<h3>회원 정보 수정</h3>
			</div>
			<form:form modelAttribute="member" action="${pageContext.request.contextPath}/modifyMember/${member.memberType}" method="post">
				<sec:authorize access="hasRole('ROLE_USER')">
					<div class="form-group">
						<label for="nickName">닉네임 변경</label>
						<span style="display: flex; align-items: center;"> 
						<form:input path="nickName" class="form-control" id="nickName" value="${member.nickName}"/>
						<input type="button" id="nickNameDuplicateConfirm" class="btn btn-primary" value="중복 확인">
						</span>
						<label id="nickNameAlertLabel"></label>
					</div>
				</sec:authorize>
				<div class="form-group">
					<label for="password">비밀번호 변경</label>
					<span style="display: flex; align-items: center;"> 
					<form:password path="password" class="form-control" placeholder="Password"/>
					</span>
					<form:errors path="password" cssClass="text-danger"/>
				</div>
				<div class="form-group">
					<label for="passwordConfirm">비밀번호 확인</label>
					<form:password path="passwordConfirm" class="form-control" placeholder="Password Confirm"/>
					<form:errors path="passwordConfirm" cssClass="text-danger"/>
				</div>
				<div class="col-md-12">
					<form:hidden path="memberType" value="${member.memberType}" />
					<form:hidden path="username" value="${member.username }" />
					<input type="submit" value="변경" class="btn btn-primary" style="height: 50px; width: 100%; margin-bottom: 10px;">
				</div>
			</form:form>
			<div class="col-md-12 text-center">
				<a href="#" id="withdrawal">회원 탈퇴</a>
			</div>
		</div>
	</div>
	</div>		

	<!-- Menu -->
    <div class="row">
        <div class="col-md-12">
            <jsp:include page="../menu.jsp" />
        </div>
    </div>
    
	<!-- Bootstrap JS -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>