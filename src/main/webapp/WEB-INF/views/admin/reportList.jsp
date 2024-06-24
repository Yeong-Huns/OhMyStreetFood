<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		$(".deleteStore").click(function(){
			if(confirm("정말로 삭제하시겠습니까?") == true){
				var storeNo = Number($(this).data('store-no'));
				var $button = $(this);  
				$.ajax({
			    	url : './deleteStore',
			        data : {
			        	storeNo : storeNo,
			        },
			        type : 'POST',
			        dataType : 'json',
			        success : function(result) {
			        	if(result == true){
			            	alert("삭제가 완료되었습니다.");
			            	$button.closest('.report-container').remove();
			            } else {
			            	alert("오류");
			            }
			        },
			        error: function(xhr, status, error) {
			            console.error("AJAX Error: " + status + error);
			        }
				});
			} else {
				return false;
			}
		});
	});
</script>
</head>
<body>
	<c:forEach items="${reports }" var="report" varStatus="status">
		<div class="report-container">
			<h3>${report.storeNo }</h3>
			<input type="button" class="deleteStore" data-store-no="${report.storeNo}" value="가게 삭제">
			<table border="1">
				<thead>
					<tr>
						<th>제목</th>
						<th>내용</th>
						<th>작성자</th>
						<th>등록일자</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${report.title}</td>
						<td>${report.content}</td>
						<td>${report.username}</td>
						<td>${report.createdAt}</td>
					</tr>
				</tbody>
			</table>
		</div>
	</c:forEach>	
</body>
</html>
