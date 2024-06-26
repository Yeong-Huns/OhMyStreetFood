<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>
        $(document).ready(function() {
            $("#passwordConfirm").click(function() {
                var password = $("#inputPassword").val();

                $.ajax({
                    type: "POST",
                    url: "./confirmPassword",
                    data: {
                        password: password
                    },
                    success: function(result) {
                        if (result === true) {
                            window.location.href = "./modifyMemberForm" ;
                        } else {
                            alert("비밀번호가 일치하지 않습니다.");
                        }
                    },
                    error: function(xhr, status, error) {
                        alert("서버 오류입니다. 다시 시도해 주세요.");
                    }
                });
            });
        });
    </script>
</head>
<body>
	<h3>비밀번호 확인</h3>
	<input type="password" id="inputPassword"/>
	<input type="button" value="확인" id="passwordConfirm">
</body>
</html>