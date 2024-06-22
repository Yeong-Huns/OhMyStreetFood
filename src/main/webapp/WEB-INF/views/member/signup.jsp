<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>
   var isIdDuplicateChecked = false;
   var isNickNameDuplicateChecked = false;
   
    $(document).ready(function() {

       var csrfToken = $('meta[name="_csrf"]').attr('content');
        var csrfHeader = $('meta[name="_csrf_header"]').attr('content');

        $.ajaxSetup({
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            }
        });
        
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
                 username : id,
                 memberType : "${memberType}"
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
          
          if(nickName == '' || nickName.length == 0) {
             $("#nickNameAlertLabel").css("color", "red").text("공백은 닉네임으로 사용할 수 없습니다.");
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
       
       // 중복 확인 후 수정했을 때 중복 확인 다시 해야 함
       $("#username").on('input', function() {
          $("#idAlertLabel").empty();
           isIdDuplicateChecked = false;
        });
       
       $("#nickName").on('input', function() {
          $("#nickNameAlertLabel").empty();
           isNickNameDuplicateChecked = false;
        });
       
       // 중복 확인 안했을 때 폼 제출 막기
       $("#generalMember").submit(function(event) {
            if (!isIdDuplicateChecked) {
                alert("아이디 중복 확인을 해주세요.");
                event.preventDefault();
            }
            
            if (!isNickNameDuplicateChecked) {
                alert("닉네임 중복 확인을 해주세요.");
                event.preventDefault();
            }
            
        });
       
    });
</script>
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
            <h3>회원가입</h3>
         </div>
         
         <form:form modelAttribute="member" action="${pageContext.request.contextPath}/signup/${member.memberType}" method="post">
            <div class="form-group">
               <label for="username">아이디(이메일 주소)</label>
               <span style="display: flex; align-items: center;">
                <form:input type="email" path="username" class="form-control" aria-describedby="emailHelp" placeholder="Id" /> 
                <input type="button" id="idDuplicateConfirm" class="btn btn-primary" value="중복 확인" /> 
               </span>
                <label id="idAlertLabel"></label>
                <form:errors path="username" cssClass="text-danger"/>
            </div>
            <c:if test="${member.memberType != 'owner'}">
                 <div class="form-group" id="nickNameGroup">
                     <label for="nickName">닉네임</label>
                     <span style="display: flex; align-items: center;">
                      <form:input type="text" path="nickName" class="form-control" placeholder="NickName" />
                      <input type="button" id="nickNameDuplicateConfirm" class="btn btn-primary" value="중복 확인" />
                     </span>
                     <label id="nickNameAlertLabel"></label>
                     <form:errors path="nickName" cssClass="text-danger"/>
                 </div>
             </c:if>
            <div class="form-group">
               <label for="password">비밀번호</label> 
               <form:password path="password" class="form-control" placeholder="비밀번호는 8~16자의 영문 대/소문자, 숫자, 특수문자를 포함해야 합니다."/>
               <form:errors path="password" cssClass="text-danger"/>
            </div>
            <div class="form-group">
               <label for="passwordConfirm">비밀번호 확인</label> 
               <form:password path="passwordConfirm" class="form-control" placeholder="Password Confirm"/>
               <form:errors path="passwordConfirm" cssClass="text-danger"/>
            </div>
            <div class="col-md-12">
               <form:hidden path="memberType" value="${member.memberType}"/>
               <form:hidden path="loginType" value="email"/>
               <input type="submit" value="회원가입" class="btn btn-primary" style="height: 50px; width: 100%; margin-bottom: 10px;">
            </div>
         </form:form>
      </div>
   </div>

   <!-- Bootstrap JS -->
   <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>