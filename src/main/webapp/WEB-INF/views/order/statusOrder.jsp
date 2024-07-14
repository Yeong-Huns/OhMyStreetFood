<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
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
    <link href="https://fonts.googleapis.com/css2?family=Chakra+Petch:ital,wght@0,300;0,400;0,500;0,600;0,700&family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
    <!-- CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.theme.default.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
	.icons {
	    display: flex;
	    justify-content: space-around;
	    position: relative; /* 부모 요소에 대해 상대 위치 설정 */
	    top: -50px; /* 원하는 만큼 프로그레스바 위로 이동 */
	}
	.icons i {
		font-size: 1.5rem;
		color: black;
		background-color: white;
		border-radius: 50%;
		border: 1px solid #007bff;
		padding: 10px;
	}
    </style>
</head>
<body>
    <div class="container">
	    <h3 style="text-align: center;">주문내역</h3>
		    
        <div style="text-align: center">
   	    	해당 가게 사장님에게 주문이 요청되었습니다<br>
	        사장님 승인 후 결제까지 진행해야 진짜 주문이 완료됩니다
    	</div>
    	
	    <!-- Progress bar -->
	    <div style="display: flex; flex-direction: column; justify-content: center; padding: 50px 0 0 0;">
			<div class="progress" style="padding: 0px; margin: 20px;">
			    <div class="progress-bar" role="progressbar" style="width: progress(${order})%;" aria-valuenow="progress(${order})" aria-valuemin="0" aria-valuemax="100"></div>
			    <div class="progress-bar" role="progressbar" style="width: progress(${order})%;" aria-valuenow="progress(${order})" aria-valuemin="0" aria-valuemax="100"></div>
			    <div class="progress-bar" role="progressbar" style="width: progress(${order})%;" aria-valuenow="progress(${order})" aria-valuemin="0" aria-valuemax="100"></div>
			    <div class="progress-bar" role="progressbar" style="width: progress(${order})%;" aria-valuenow="progress(${order})" aria-valuemin="0" aria-valuemax="100"></div>
			</div>
	
		    <div class="icons" style="padding: 0px;">
		        <div style="text-align: center; padding: 0px;">
					<i class="fas fa-shopping-cart"></i><br>
					주문 요청<br>
					${empty order.createdat ? '' : order.createdat}
				</div>
		        <div style="text-align: center; padding: 0px;">
		        	<i class="fas fa-check"></i><br>
		        	주문 승인<br>
					${empty order.approvedat ? '' : order.approvedat}
		        </div>
		        <div style="text-align: center; padding: 0px;">
		        	<i class="fas fa-credit-card"></i><br>
		        	결제 완료<br>
					${empty order.paidat ? '' : order.paidat}
		        </div>
		        <div style="text-align: center; padding: 0px;">
					<i class="fas fa-box"></i><br>
					픽업 완료<br>
					${empty order.pickupat ? '' : order.pickupat}
		        </div>
		    </div>
	  	</div>
	  	
    	<!-- Order details table -->
        <div>
        	<span style="display: flex; flex-direction: row; justify-content: space-between; margin-bottom: 20px;">
			    <h4>주문 정보 #${empty order.orderno ? 'Unknown' : order.orderno} </h4>
			    <span>
				    <button class="btn btn-primary">(사장님)승인하기</button>
				    <button class="btn btn-danger">(사장님)거절하기</button>
				    /////
				    <button class="btn btn-primary">(주문자)결제하기</button>
			    </span>
			</span>
		    <p><strong>가게번호:</strong> ${empty order.storeno ? 'Unknown' : order.storeno}</p>
		    <p><strong>주문자:</strong> ${empty order.username ? 'Unknown' : order.username}</p>
		    <p><strong>결제수단:</strong> ${empty order.paymethod ? 'Unknown' : order.paymethod}</p>
		    <p><strong>요청사항:</strong> ${empty order.memo ? '' : order.memo}</p>
		    <p><strong>예약일시:</strong> ${empty order.reservedat ? 'Unknown' : order.reservedat}</p>
		</div>
		<div>
		    <h4>결제 정보</h4>
			<table class="table">
			    <tr>
			        <th>메뉴 이름</th>
			        <th>메뉴 가격</th>
			        <th>메뉴 수량</th>
			    </tr>
			    <c:forEach var="menu" items="${menus}">
			        <tr>
			            <td>${empty menu.ordername ? 'Unknown' : menu.ordername}</td>
			            <td>${empty menu.orderprice ? 'Unknown' : menu.orderprice}</td>
			            <td>${empty menu.orderquantity ? 'Unknown' : menu.orderquantity}</td>
			        </tr>
			    </c:forEach>
			    	<tr>
			            <td colspan="2">총 가격</td>
			            <td> ${empty order.totalprice ? 'Unknown' : order.totalprice}</td>
			        </tr>
			</table>
		</div>
    </div>
    
    <!-- Menu -->
    <jsp:include page="../menu.jsp" />

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <script>
    function progress(order) {
        if (order.approval && order.paystatus && order.pickupat) {
            return '100';
        } else if (order.approval && order.paystatus) {
            return '75';
        } else if (order.approval !== null) {
            return '50';
        } else if (order.createdat !== null) {
            return '25';
        } else {
            return '0';
        }
    }
	</script>
</body>
</html>
