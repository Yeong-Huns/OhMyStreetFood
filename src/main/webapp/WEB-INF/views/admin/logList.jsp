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
        .spinner {
		    display: none;
		    width: 40px;
		    height: 40px;
		    border: 4px solid rgba(0, 0, 0, 0.1);
		    border-top: 4px solid #3498db;
		    border-radius: 50%;
		    animation: spin 1s linear infinite;
		    position: fixed;
		    z-index: 1;
		    bottom: 20px; /* Adjust this value to set how far from the bottom you want the spinner */
		    left: 50%;
		    transform: translateX(-50%);
		}
		
		@keyframes spin {
		    0% { transform: rotate(0deg); }
		    100% { transform: rotate(360deg); }
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
                            <tbody id="log-data" class="log-data">
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
                        <div id="spinner" class="spinner"></div>
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
		$(document).on("click", ".rollback", function(){
				if(confirm("정말로 롤백하시겠습니까?") == true){
					var logNo = Number($(this).data('log-no'));
					var $button = $(this);  
					$.ajax({
				    	url : '/admin/store/log/update',
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
	</script>
	<script>
	document.addEventListener('DOMContentLoaded', () => {
	    let page = 2; // 페이지 번호
	    const logContainer = document.getElementById('log-data');

	    // 더미 데이터를 추가하는 함수
	    function addLogs(logs) {
	    	logs.forEach(log => {
	            const logTr = document.createElement('tr');
	            logTr.className = "log-row";
	            
	            // 날짜 포맷
	            const createdAt = new Date(log.createdAt).toLocaleDateString('ko-KR', {
	                year: 'numeric',
	                month: '2-digit',
	                day: '2-digit'
	            }).replace(/\./g, '-').replace(/ /g, '').replace('년', '').replace('월', '').replace('일', '').slice(0,-1);
	            console.log(log);
	            console.log('수정일자 : '+ log.MODIFIEDAT);
	            logTr.innerHTML = `
	            	<td>` + log.LOGNO + `</td>
                    <td>` + log.STORENO + `</td>
                    <td>` + log.STORENAME + `</td>
                    <td>` + log.LATITUDE + `</td>
                    <td>` + log.LONGITUDE + `</td>
                    <td>` + log.ADDRESS + `</td>
                    <td>` + nvl(log.INTRODUCE) + `</td>
                    <td>` + nvl(log.OPERATINGDATE) + `</td>
                    <td>` + log.OPERATINGHOURS + `</td>
                    <td>` + log.TOTALREVIEW + `</td>
                    <td>` + log.TOTALRATING + `</td>
                    <td>` + log.LIKES + `</td>
                    <td>` + dateFormat(log.CREATEDAT) + `</td>
                    <td>` + dateFormat(log.MODIFIEDAT) + `</td>
                    <td>` + log.MODIFIER + `</td>
                    <td><i class="fa fa-undo rollback" aria-hidden="true" data-log-no="` + log.LOGNO + `" style="cursor: pointer;"></i></td>
					<td></td>`;
	            
	            logContainer.appendChild(logTr);
	        });
	    }

		 // 실제 서버에 요청할 때 사용할 함수
		 function fetchReviews(page) {
		     return fetch(`${pageContext.request.contextPath}/admin/store/log/${storeId}/api?page=` + page)
		         .then(response => {
		             if (response.ok) {
		            	 return response.json();
		             }
		         })
		         .then(logs => logs)
		         .catch(error => console.error('Error fetching logs:', error));
		 }

	    // 스크롤 이벤트를 감지하는 함수
	    function handleScroll() {
	        
	        if (window.innerHeight + window.scrollY >= document.documentElement.scrollHeight) {
	            window.removeEventListener('scroll', handleScroll);

	            showSpinner(); // 스피너 표시

	            console.log("스크롤 이벤트 발생");
	            setTimeout(async () => { // 3초 지연
	            fetchReviews(page).then(logs => {
	            	addLogs(logs);
	                page++;
	                hideSpinner(); // 스피너 숨김
	                window.addEventListener('scroll', handleScroll);
	            }).catch(error => {
	                console.error('Error fetching logs:', error);
	                hideSpinner(); // 스피너 숨김
	                window.addEventListener('scroll', handleScroll);
	            })
	            }, 1000);
	        }
	    }
	    
	    function dateFormat(date){
	    	if(date === undefined) return '';
	    	
	    	// 문자열을 Date 객체로 변환
	        const parsedDate = new Date(date);
	    	
	    	// 날짜 부분 포맷팅
	    	const formattedDate = parsedDate.toLocaleDateString('ko-KR', {
	    	    year: 'numeric',
	    	    month: '2-digit',
	    	    day: '2-digit'
	    	}).replace(/\./g, '-').replace(/ /g, '').replace('년', '').replace('월', '').replace('일', '');

	    	// 시간 부분 포맷팅
	    	const hours = String(parsedDate.getHours()).padStart(2, '0');
	    	const minutes = String(parsedDate.getMinutes()).padStart(2, '0');
	    	const seconds = String(parsedDate.getSeconds()).padStart(2, '0');
	    	const milliseconds = String(parsedDate.getMilliseconds()).charAt(0);

	    	// 최종 포맷팅
	    	const formattedDateTime = formattedDate + ' ' + hours + ':' + minutes + ':' 
	    				+ seconds + '.' + milliseconds;
	    	return formattedDateTime;
	    }
	    
	    function nvl(data){
	    	if(data === undefined) return '';
	    	return data;
	    }
	    
	 	// 스피너 표시 함수
	    function showSpinner() {
	    	document.getElementById('spinner').style.display = 'block';
	    }

	    // 스피너 숨김 함수
	    function hideSpinner() {
	    	document.getElementById('spinner').style.display = 'none';
	    }

	    // 스크롤 이벤트 리스너 추가
	    window.addEventListener('scroll', handleScroll);

	});
	</script>
	
</body>
</html>