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
	<!-- Logo -->
	<div style="text-align: center; margin-top: 55px;">
		<img src="${pageContext.request.contextPath}/img/logo.png" style="width: 450px">
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
					<a href="#" id="findPassword" data-bs-toggle="modal" data-bs-target="#passwordRecoveryModal">비밀번호 찾기</a>
				</div>
				
				<!-- Modal -->
				<div class="modal fade" id="passwordRecoveryModal" tabindex="-1" aria-labelledby="passwordRecoveryModalLabel" aria-hidden="true">
				    <div class="modal-dialog">
				        <div class="modal-content">
				            <div class="modal-header">
				                <h5 class="modal-title" id="passwordRecoveryModalLabel">비밀번호 찾기</h5>
				                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				            </div>
				            <div class="modal-body">
				                <form action="${pageContext.request.contextPath}/findPassword" method="post" id="sendEmailModal">
				                    <div class="mb-3">
				                        <label for="usernameModal" class="form-label">아이디(이메일)를 입력해주세요</label>
				                        <input type="email" class="form-control" id="usernameModal" name="username" placeholder="Email">
				                    </div>
				                    
				                </form>
				            </div>
				            <div class="modal-footer">
				                <button type="button" class="btn btn-primary" id="confirmExistedIdModal">비밀번호 발송</button>
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
		//var csrfToken = $('meta[name="_csrf"]').attr('content');
        //var csrfHeader = $('meta[name="_csrf_header"]').attr('content');
        
        $("#findPassword").click(function(){
	    	$('#passwordRecoveryModal').modal('show');
		});

		$("#confirmExistedId").click(function(){
			var id = $("#username").val();
			
			if(id == '' || id.length == 0) {
				alert("이메일을 입력해주세요.");
				return;
			}
			
			$.ajax({
        		url : './findPassword/confirmId',
        		data : {
        			username : id,
        			memberType : 'general'
        		},
        		type : 'POST',
        		dataType : 'json',
        		//beforeSend: function(xhr) {
                    //xhr.setRequestHeader(csrfHeader, csrfToken);
                //},
        		success : function(result) {
        			if (result == true) {
        				alert("존재하지 않는 아이디입니다.");
        			} else{
        				alert('임시비밀번호를 전송 했습니다.');
        				sendEmail.submit();
        			}
        		},
        		error: function(xhr, status, error) {
                    console.error("AJAX Error: " + status + error);
                }
        	});
		})
	})
	</script>
</body>
</html>