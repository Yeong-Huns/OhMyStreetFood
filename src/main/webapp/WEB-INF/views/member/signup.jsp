<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<!-- <meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/> -->

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

    $(document).ready(function() {

//     	var csrfToken = $('meta[name="_csrf"]').attr('content');
//         var csrfHeader = $('meta[name="_csrf_header"]').attr('content');

//         $.ajaxSetup({
//             beforeSend: function(xhr) {
//                 xhr.setRequestHeader(csrfHeader, csrfToken);
//             }
//         });

		var isIdDuplicateChecked = false;
		var isNickNameDuplicateChecked = false;
		var isBusinessRegistrationNumberChecked = false;
        
		//ID 중복 확인
		$("#idDuplicateConfirm").click(function() {
			var id = $("#username").val();
		    var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
		     
		    if(id == '' || id.length == 0) {
		    	$("#idAlertLabel").css("color", "red").text("공백은 ID로 사용할 수 없습니다.");
		      	isIdDuplicateChecked = false;
		      	return false;
		    }
		   
		    // 이메일 형식 확인
		    if (!emailPattern.test(id)) {
		    	$("#idAlertLabel").css("color", "red").text("사용 불가능한 ID 입니다.");
		        isIdDuplicateChecked = false;
		        return false; 
		    }
          
			//Ajax로 전송
	        $.ajax({
	        	url : './confirmId',
	            data : {
	            	username : id
	            },
	            type : 'POST',
	            dataType : 'json',
	            success : function(result) {
	            	if (result == true) {
	                	$("#idAlertLabel").css("color", "black").text("사용 가능한 ID 입니다.");
	                    isIdDuplicateChecked = true;
	                } else{
	                    $("#idAlertLabel").css("color", "red").text("사용 불가능한 ID 입니다.");
	                    isIdDuplicateChecked = false;
	                }
	            },
	            error: function(xhr, status, error) {
	            	console.error("AJAX Error: " + status + error);
	                isIdDuplicateChecked = false;
	            }
	        });
        	
    	});
       
		// 닉네임 중복 확인
        $("#nickNameDuplicateConfirm").click(function() {
          
        	var nickName = $("#nickName").val();
        	var nickNamePattern = /^[a-zA-Z가-힣0-9]{2,20}$/;
          
            if(nickName == '' || nickName.length == 0) {
            	$("#nickNameAlertLabel").css("color", "red").text("공백은 닉네임으로 사용할 수 없습니다.");
            	isNickNameDuplicateChecked = false;
             	return false;
          	}
            
         	// 닉네임 형식 확인
		    if (!nickNamePattern.test(nickName)) {
		    	$("#nickNameAlertLabel").css("color", "red").text("2~20자의 닉네임만 가능합니다.");
		        isNickNameDuplicateChecked = false;
		        return false; 
		    }
          
            //Ajax로 전송
            $.ajax({
            	url : './confirmNickName',
                data : {
                	nickName : nickName
              	},
                type : 'POST',
                dataType : 'json',
                success : function(result) {
                	if (result == true) {
                    	$("#nickNameAlertLabel").css("color", "black").text("사용 가능한 닉네임 입니다.");
                    	isNickNameDuplicateChecked = true;
                 	} else{
                 		$("#nickNameAlertLabel").css("color", "red").text("사용 불가능한 닉네임 입니다.");
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
		
       // 사업자 등록 번호 중복 확인 
       $("#businessRegistrationNumberConfirm").click(function() {
          
        	var businessRegistrationNumber = $("#businessRegistrationNumber").val();
        	var businessRegistrationNumberPattern = /^\d{10}$/;
        	
            if(businessRegistrationNumber == '' || businessRegistrationNumber.length == 0) {
            	$("#businessRegistrationNumberAlertLabel").css("color", "red").text("공백은 등록할 수 없습니다.");
            	isBusinessRegistrationNumberDuplicateChecked = false;
             	return false;
          	}
            
         	// 사업자 등록 번호 형식 확인
		    if (!businessRegistrationNumberPattern.test(businessRegistrationNumber)) {
		    	$("#businessRegistrationNumberAlertLabel").css("color", "red").text("10자리 숫자만 입력 가능합니다.");
		        isBusinessRegistrationNumberDuplicateChecked = false;
		        return false; 
		    }
          
            //Ajax로 전송
            $.ajax({
            	url : './confirmBusinessRegistrationNumber',
                data : {
                	businessRegistrationNumber : businessRegistrationNumber
              	},
                type : 'POST',
                dataType : 'json',
                success : function(result) {
                	if (result == true) {
                    	$("#businessRegistrationNumberAlertLabel").css("color", "black").text("등록 가능한 사업자 등록 번호입니다.");
                    	isBusinessRegistrationNumberDuplicateChecked = true;
                 	} else{
                 		$("#businessRegistrationNumberAlertLabel").css("color", "red").text("이미 등록된 사업자 등록 번호입니다.");
                    	$("#businessRegistrationNumber").val('');
                    	isBusinessRegistrationNumberDuplicateChecked = false;
                 	}
              	},
	            error: function(xhr, status, error) {
	            	console.error("AJAX Error: " + status + error);
	                isBusinessRegistrationNumberDuplicateChecked = false;
	            }
           });
           
       });
       
       // 중복 확인 후 수정했을 때 중복 확인 다시 해야 함
       $("#username").on('input', function() {
          $("#idAlertLabel").empty();
           isIdDuplicateChecked = false;
        });
       
       $("#nickName").on('input', function() {
          $("#nickNameAlertLabel").empty();
           isNickNameDuplicateChecked = false;
        });
       
       $("#businessRegistrationNumber").on('input', function() {
           $("#businessRegistrationNumberAlertLabel").empty();
           isBusinessRegistrationNumberDuplicateChecked = false;
        });
       
       // 중복 확인 안했을 때 폼 제출 막기
       $("#signupForm").submit(function(event) {
    	   	var memberType = "${member.memberType}";
    	   	
            if (!isIdDuplicateChecked) {
                alert("아이디 중복 확인을 해주세요.");
                event.preventDefault();
            }
            
            if (memberType == 'general' && !isNickNameDuplicateChecked) {
                alert("닉네임 중복 확인을 해주세요.");
                event.preventDefault();
            }
            
            if (memberType == 'owner' && !isBusinessRegistrationNumberDuplicateChecked) {
                alert("사업자 등록 번호 중복 확인을 해주세요.");
                event.preventDefault();
            }
            
        });
       
    });
</script>
</head>
<body>
	<div class="main">
		<div class="row">
			<div class="col-md-12 text-center" id="title">
				<c:choose>
				    <c:when test="${member.memberType eq 'owner'}">
				        <h3>사장님 회원가입</h3>
				    </c:when>
				    <c:otherwise>
				        <h3>회원가입</h3>
				    </c:otherwise>
				</c:choose>
			</div>

			<form:form modelAttribute="member" id="signupForm" action="${pageContext.request.contextPath}/signup/${member.memberType}" method="post">
				<div class="form-group">
					<label for="username">아이디(이메일 주소)</label> 
					<span style="display: flex; align-items: center;"> 
					<form:input type="email" path="username" class="form-control" aria-describedby="emailHelp" placeholder="Id" /> 
					<input type="button" id="idDuplicateConfirm" class="btn btn-primary" value="중복 확인" />
					</span> 
					<label id="idAlertLabel"></label>
					<form:errors path="username" cssClass="text-danger" />
				</div>
				<c:if test="${member.memberType != 'owner'}">
					<div class="form-group" id="nickNameGroup">
						<label for="nickName">닉네임</label> 
						<span style="display: flex; align-items: center;"> 
						<form:input type="text" path="nickName" class="form-control" placeholder="NickName" /> 
						<input type="button" id="nickNameDuplicateConfirm" class="btn btn-primary" value="중복 확인" />
						</span> 
						<label id="nickNameAlertLabel"></label>
						<form:errors path="nickName" cssClass="text-danger" />
					</div>
				</c:if>
				<c:if test="${member.memberType == 'owner'}">
					<div class="form-group" id="businessRegistrationNumberGroup">
						<label for="businessRegistrationNumber">사업자 등록 번호</label> 
						<span style="display: flex; align-items: center;"> 
						<form:input type="text" path="businessRegistrationNumber" class="form-control" placeholder="businessRegistrationNumber" /> 
						<input type="button" id="businessRegistrationNumberConfirm" class="btn btn-primary" value="중복 확인" />
						</span> 
						<label id="businessRegistrationNumberAlertLabel"></label>
						<form:errors path="businessRegistrationNumber" cssClass="text-danger" />
					</div>
				</c:if>
				<div class="form-group">
					<label for="password">비밀번호</label>
					<form:password path="password" class="form-control" placeholder="비밀번호는 8~16자의 영문 대/소문자, 숫자, 특수문자를 포함해야 합니다." />
					<form:errors path="password" cssClass="text-danger" />
				</div>
				<div class="form-group">
					<label for="passwordConfirm">비밀번호 확인</label>
					<form:password path="passwordConfirm" class="form-control" placeholder="Password Confirm" />
					<form:errors path="passwordConfirm" cssClass="text-danger" />
				</div>
				<div class="col-md-12">
					<form:hidden path="memberType" value="${member.memberType}" />
					<form:hidden path="loginType" value="email" />
					<input type="submit" value="회원가입" class="btn btn-primary" style="height: 50px; width: 100%; margin-bottom: 10px;">
				</div>
			</form:form>
		</div>
	</div>
	
    <!-- Menu -->
    <div class="row">
        <div class="col-md-12">
            <jsp:include page="../menu.jsp" />
        </div>
    </div>
    
	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>