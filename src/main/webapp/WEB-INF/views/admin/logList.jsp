<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>OhMyStreetFood!</title>
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.10.3/font/bootstrap-icons.min.css">
<!-- Font Awesome CSS -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<!-- Google Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
<!-- CSS -->
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"> --%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style>
        .custom-container {
            max-width: 90%; /* 필요한 만큼 너비를 조정하세요. */
            margin: 20px auto; /* 중앙 정렬을 위해 추가 */
        }
        .custom-card {
            width: 100%; /* 카드의 너비를 100%로 설정 */
        }
        .main {
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }
</style>
</head>

<body>
	<div class="main">
        <div class="row w-100">
            <div>
                <a href="javascript:history.go(-1);" style="text-decoration: none; color: inherit;">
                    <i class="fas fa-arrow-left"></i>
                </a>
            </div>
            <div class="col-md-12 text-center" id="title">
                <h3>관리자 페이지</h3>
            </div>
            <div class="container custom-container">
                <div class="card custom-card">
<!--                     <div class="card-header d-flex justify-content-between align-items-center"> -->
<!--                         <h5 class="d-inline">점포번호: temp</h5> -->
<!--                     </div> -->
                    <div class="card-body">
                        <table class="table table-bordered">
                            <thead class="thead-dark">
                                <tr>
                                    <th>로그번호</th>
                                    <th>가게번호</th>
                                    <th>가게이름</th>
                                    <th>위도</th>
                                    <th>경도</th>
                                    <th>주소</th>
                                    <th>소개</th>
                                    <th>운영일자</th>
                                    <th>운영시간</th>
                                    <th>리뷰수</th>
                                    <th>평점</th>
                                    <th>찜</th>
                                    <th>생성일자</th>
                                    <th>수정일자</th>
                                    <th>수정자</th>
                                    <th>롤백</th>
                                    <th>now</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${list}" var="log" varStatus="status">
                                    <tr class="log-row">
                                        <td>${log.LOGNO}</td>
                                        <td>${log.STORENO}</td>
                                        <td>${log.STORENAME}</td>
                                        <td>${log.LATITUDE}</td>
                                        <td>${log.LONGITUDE}</td>
                                        <td>${log.ADDRESS}</td>
                                        <td>${log.INTRODUCE}</td>
                                        <td>${log.OPERATINGDATE}</td>
                                        <td>${log.OPERATINGHOURS}</td>
                                        <td>${log.TOTALREVIEW}</td>
                                        <td>${log.TOTALRATING}</td>
                                        <td>${log.LIKES}</td>
                                        <td>${log.CREATEDAT}</td>
                                        <td>${log.MODIFIEDAT}</td>
                                        <td>${log.MODIFIER}</td>
                                        <td><i class="fa fa-undo rollback" aria-hidden="true" data-log-no="${log.LOGNO}" style="cursor: pointer;"></i></td>
                                        <td>
                                        <c:if test="${status.index == 0}">
                                        	<i class="bi bi-flag"></i>
                                        </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="col-md-12 text-center">
                <a href="${pageContext.request.contextPath}/logout">로그아웃</a>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </div>
    </div>
    <script>
		$(document).ready(function(){
			$(".rollback").click(function(){
				if(confirm("정말로 롤백하시겠습니까?") == true){
					var logNo = Number($(this).data('log-no'));
					var $button = $(this);  
					$.ajax({
				    	url : '/store/log/update',
				        data : {
				        	logNo : logNo,
				        },
				        type : 'POST',
				        dataType : 'json',
				        success : function(result) {
				        	if(result == true){	
			            		alert("롤백이 완료되었습니다.");
			            		location.reload();
				        	}else{
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
