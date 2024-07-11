<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<div class="container">
		<div class="row">
			<div class="col-md-12 d-flex justify-content-between align-items-center" id="title">
				<h3 class="text-center flex-grow-1">관리자 페이지</h3>
				<a href="${pageContext.request.contextPath}/logout" class="text-end">로그아웃</a>
			</div>
			<c:forEach items="${groupedReports}" var="entry">
			    <div class="report-container mb-4">
			        <div class="card">
			            <div class="card-header d-flex justify-content-between align-items-center">
			                <h5 class="d-inline">점포번호: <a href="${pageContext.request.contextPath}/admin/store/log/${entry.key}">${entry.key}</a></h5>
			                <button type="button" class="btn btn-danger btn-sm float-right deleteStore" data-store-no="${entry.key}">X</button>
			            </div>
			            <div class="card-body">
			                <table class="table table-bordered">
			                    <thead class="thead-dark">
			                        <tr>
			                            <th>제목</th>
			                            <th>내용</th>
			                            <th>작성자</th>
			                            <th>등록일자</th>
			                            <th>삭제</th>
			                        </tr>
			                    </thead>
			                    <tbody>
			                        <c:forEach items="${entry.value}" var="report">
			                            <tr>
			                                <td>${report.title}</td>
			                                <td>${report.content}</td>
			                                <td>${report.username}</td>
			                                <td>${report.createdAt}</td>
			                                <td><button type="button" class="btn btn-danger btn-sm float-right deleteReport" data-report-no="${report.reportNo}">X</button>
			                            </tr>
			                        </c:forEach>
			                    </tbody>
			                </table>
			            </div>
			        </div>
			    </div>
			</c:forEach>
			
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</div>
	</div>
	<!-- Menu -->
    <div class="row">
        <div class="col-md-12">
            <jsp:include page="../menu.jsp" />
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
			$(".deleteReport").click(function(){
				if(confirm("정말로 삭제하시겠습니까?") == true){
					var reportNo = Number($(this).data('report-no'));
					var $button = $(this);  
					$.ajax({
				    	url : './deleteReport',
				        data : {
				        	reportNo : reportNo,
				        },
				        type : 'POST',
				        dataType : 'json',
				        success : function(result) {
				        	if(result == true){
				            	alert("삭제가 완료되었습니다.");
				            	$button.closest('tr').remove();
				            } else {
				            	alert("서버 오류입니다. 다시 시도해 주세요.");
				            }
				        },
				        error: function(xhr, status, error) {
				        	alert("서버 오류입니다. 다시 시도해 주세요.");
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
