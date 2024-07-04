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
<!-- jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<!-- Logo -->
	<div style="text-align: center; margin-top: 55px;">
		<a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/img/logo.png" style="width: 450px"></a>
	</div>
	
	<div class="main">
    	<div class="row justify-content-center">
        	<div class="col-md-10">
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
						<div class="col-md-12" style="padding: 0 20px;">
							<div class="alert alert-danger">
		                		사용자ID 또는 비밀번호를 확인해 주세요.
		            		</div>
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
					<a href="javascript:void(0)" id="findPassword" data-bs-toggle="modal" data-bs-target="#passwordRecoveryModal">비밀번호 찾기</a>
				</div>
				
				<!-- 비밀번호 찾기 모달 -->
				<div class="modal fade" id="passwordRecoveryModal" tabindex="-1" aria-labelledby="passwordRecoveryModalLabel" aria-hidden="true">
				    <div class="modal-dialog modal-dialog-centered">
				        <div class="modal-content">
				            <div class="modal-header">
				                <h5 class="modal-title" id="passwordRecoveryModalLabel">비밀번호 찾기</h5>
				                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				            </div>
				            <div class="modal-body">
				                <form action="${pageContext.request.contextPath}/findPassword" method="post" id="sendEmailForm">
				                    <div class="mb-3">
				                        <label for="username" class="form-label">아이디(이메일)를 입력해주세요</label>
				                        <input type="email" class="form-control" id="findPassword-username" name="username" placeholder="Email">
				                        <label id="usernameAlert" class="text-danger"></label>
				                    </div>
				                </form>
				            </div>
				            <div class="modal-footer">
				                <button type="button" class="btn btn-primary" id="confirmExistedIdBtn">비밀번호 발송</button>
				                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				            </div>
				        </div>
				    </div>
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
	
	
	<script>
        window.onload = function() {
            var success = '${success}';
            if (success === true) {
                alert('회원가입이 완료되었습니다.');
            }
        }
	</script>
	<script>
	
	$(document).ready(function(){
	    $("#confirmExistedIdBtn").click(function(event){
	        event.preventDefault();
	        
	        var id = $("#findPassword-username").val();
	        console.log(id);
	        
	        if(id == '' || id.length == 0) {
	            $("#usernameAlert").removeClass("text-success").addClass("text-danger").text("이메일을 입력해주세요.");
	            return;
	        }
	        
	        $.ajax({
	            url: './findPassword/confirmId',
	            data: {
	                username: id
	            },
	            type: 'POST',
	            dataType: 'json',
	            success: function(result) {
	                if (result == true) {
	                    $("#usernameAlert").removeClass("text-success").addClass("text-danger").text("존재하지 않는 아이디입니다.");
	                } else {
	                    $.ajax({
	                        url: './findPassword',
	                        data: {
	                            username: id
	                        },
	                        type: 'POST',
	                        dataType: 'text',
	                        success: function(response) {
	                            $("#usernameAlert").removeClass("text-danger").addClass("text-success").text(response);
	                        },
	                        error: function(xhr, status, error) {
	                            $("#usernameAlert").removeClass("text-success").addClass("text-danger").text("서버 오류입니다. 다시 시도해주세요.");
	                        }
	                    });
	                }
	            },
	            error: function(xhr, status, error) {
	                console.error("AJAX Error: " + status + error);
	            }
	        });
	    });
	});
	
	$('#findPassword-username').on('input', function() {
        $('#usernameAlert').text("");
    });
	</script>
</body>
</html>