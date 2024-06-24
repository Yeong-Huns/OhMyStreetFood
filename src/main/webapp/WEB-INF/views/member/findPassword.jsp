<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		//var csrfToken = $('meta[name="_csrf"]').attr('content');
        //var csrfHeader = $('meta[name="_csrf_header"]').attr('content');
        
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
</head>
<body>
	<div>
		<form action="${pageContext.request.contextPath}/findPassword" method="post" id="sendEmail">
			<label> 비밀번호를 찾고자하는 아이디(이메일)를 입력해주세요. </label>
			<input type="email" id="username" name="username" placeholder="email" >
			<input type="button" id="confirmExistedId" value="비밀번호 발송">
			<%-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> --%>
		</form>
	</div>
</body>
</html>