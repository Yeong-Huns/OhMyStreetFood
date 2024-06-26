<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<div class="main">
		<div class="row">
			<div>
				<a href="javascript:history.go(-1);" style="text-decoration: none; color: inherit;"> 
					<i class="fas fa-arrow-left"></i>
				</a>
			</div>
			<div class="col-md-12 text-center" id="title">
				<h3>관리자 페이지</h3>
			</div>
			
			<c:forEach items="${reports}" var="report" varStatus="status">
			    <div class="report-container mb-4">
			        <div class="card">
			            <div class="card-header d-flex justify-content-between align-items-center">
			                <h5 class="d-inline">점포번호: ${report.storeNo}</h5>
			                <button type="button" class="btn btn-danger btn-sm float-right deleteStore" data-store-no="${report.storeNo}">X</button>
			            </div>
			            <div class="card-body">
			                <table class="table table-bordered">
			                    <thead class="thead-dark">
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
			        </div>
			    </div>
			</c:forEach>
	
			<div class="col-md-12 text-center">
				<a href="${pageContext.request.contextPath}/logout">로그아웃</a>
			</div>
			
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</div>
	</div>
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
				            	alert("서버 오류입니다. 다시 시도해 주세요.");
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
</body>
</html>
