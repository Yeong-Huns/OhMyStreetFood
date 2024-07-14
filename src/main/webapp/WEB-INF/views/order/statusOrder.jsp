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
	    <h3 style="text-align: center;">주문정보</h3>
		    
        <div style="text-align: center">
	    <c:choose>
	        <c:when test="${!empty order.createdat && empty order.approvedat && empty order.paidat && empty order.pickupat}">
	            해당 가게 사장님에게 주문이 요청되었습니다<br>
	            사장님 승인 후 결제까지 진행해야 진짜 주문이 완료됩니다
	        </c:when>
	        <c:when test="${!empty order.createdat && !empty order.approvedat && empty order.paidat && empty order.pickupat}">
	            <c:choose>
				    <c:when test="${order.approval == 'O'}">
				        해당 가게 사장님이 주문을 승인하였습니다<br>
				        등록된 결제 방법으로 결제를 진행해주세요
				    </c:when>
				    <c:when test="${order.approval == 'X'}">
				        해당 가게 사장님이 주문을 거절하였습니다<br>
				        다시 주문을 진행해주세요
				    </c:when>
				</c:choose>
	        </c:when>
	        <c:when test="${!empty order.createdat && !empty order.approvedat && !empty order.paidat && empty order.pickupat}">
	            결제가 완료되었으니 픽업을 해주세요
	        </c:when>
	        <c:when test="${!empty order.createdat && !empty order.approvedat && !empty order.paidat && !empty order.pickupat}">
	            주문 픽업이 완료되었습니다
	        </c:when>
	    </c:choose>
	</div>
    	
	    <!-- Progress bar -->
	    <div style="display: flex; flex-direction: column; justify-content: center; padding: 50px 0 0 0;">
			<div class="progress" style="padding: 0px; margin: 20px;">
		    <c:choose>
		        <c:when test="${!empty order.createdat && empty order.approvedat && empty order.paidat && empty order.pickupat}">
		            <div class="progress-bar" role="progressbar" style="width: 25%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
		        </c:when>
		        <c:when test="${!empty order.createdat && !empty order.approvedat && empty order.paidat && empty order.pickupat}">
		            <div class="progress-bar" role="progressbar" style="width: 50%;" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"></div>
		        </c:when>
		        <c:when test="${!empty order.createdat && !empty order.approvedat && !empty order.paidat && empty order.pickupat}">
		            <div class="progress-bar" role="progressbar" style="width: 75%;" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100"></div>
		        </c:when>
		        <c:when test="${!empty order.createdat && !empty order.approvedat && !empty order.paidat && !empty order.pickupat}">
		            <div class="progress-bar" role="progressbar" style="width: 100%;" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
		        </c:when>
		    </c:choose>			
		</div>
	
		    <div class="icons" style="padding: 0px;">
		        <div style="width: 100px; text-align: center; padding: 0px;">
					<i class="fas fa-shopping-cart"></i><br>
					주문 요청<br>
					${empty order.createdat ? '' : order.createdat}
				</div>
		        <div style="width: 100px; text-align: center; padding: 0px;">
		        	<i class="fas fa-check"></i><br>
		        	승인/거절<br>
					${empty order.approvedat ? '' : order.approvedat}
		        </div>
		        <div style="width: 100px; text-align: center; padding: 0px;">
		        	<i class="fas fa-credit-card"></i><br>
		        	결제 완료<br>
					${empty order.paidat ? '' : order.paidat}
		        </div>
		        <div style="width: 100px; text-align: center; padding: 0px;">
					<i class="fas fa-box"></i><br>
					픽업 완료<br>
					${empty order.pickupat ? '' : order.pickupat}
		        </div>
		    </div>
	  	</div>
	  	
    	<!-- Order details table -->
        <div>
        	<span style="display: flex; flex-direction: row; justify-content: space-between; margin-bottom: 20px;">
			    <h4>주문 내역 #${empty order.orderno ? 'Unknown' : order.orderno} </h4>
			    <span>
				    <form id="approveOrder" data-store-no="{storeNo}" data-order-no="${order.orderno}">
					    <button type="button" class="btn btn-primary" onclick="approveOrder()">(사장님)승인하기</button>
					</form>
					<form id="rejectOrder" data-store-no="{storeNo}" data-order-no="${order.orderno}">
					    <button type="button" class="btn btn-danger" onclick="rejectOrder()">(사장님)거절하기</button>
					</form>
				    /////
				    <button class="btn btn-primary" onclick="showPaymentModal()">(주문자)카드결제</button>
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
    
    <!-- Payment -->
	<div class="modal fade" id="paymentModal" tabindex="-1" aria-labelledby="paymentModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title" id="paymentModalLabel">결제하기</h5>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	            </div>
	            <div class="modal-body">
	                <!-- 결제 정보 입력 폼 -->
	                <form id="paymentForm">
	                    <div class="mb-3">
	                        <label for="cardNumber" class="form-label">카드 번호</label>
	                        <input type="text" class="form-control" id="cardNumber" required>
	                    </div>
	                    <div class="mb-3">
	                        <label for="expiryDate" class="form-label">유효기간</label>
	                        <input type="text" class="form-control" id="expiryDate" required>
	                    </div>
	                    <div class="mb-3">
	                        <label for="cvc" class="form-label">CVC</label>
	                        <input type="text" class="form-control" id="cvc" required>
	                    </div>
	                    <button type="submit" class="btn btn-primary">결제하기</button>
	                </form>
	            </div>
	        </div>
	    </div>
	</div>

    <!-- Menu -->
    <jsp:include page="../menu.jsp" />

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
    function showPaymentModal() {
        var paymentModal = new bootstrap.Modal(document.getElementById('paymentModal'), {
            keyboard: false
        });
        paymentModal.show();
    }

    // 결제 폼 제출 시 처리
    document.getElementById('paymentForm').addEventListener('submit', function(event) {
        event.preventDefault();
        alert('결제 처리가 완료되었습니다.');
        var paymentModal = bootstrap.Modal.getInstance(document.getElementById('paymentModal'));
        paymentModal.hide();
    });
	</script>
	<script>
	function approveOrder() {
	    const form = document.getElementById('approveOrder');
	    const storeNo = form.getAttribute('data-store-no');
	    const orderNo = form.getAttribute('data-order-no');

	    fetch(`/order/${storeNo}/${orderNo}/approve`, {
	        method: 'PUT',
	        headers: {
	            'Content-Type': 'application/json'
	        }
	    })
	    .then(response => {
	        if (response.ok) {
	            alert('주문 승인이 완료되었습니다');
	            location.reload();
	        } else {
	            alert('주문 승인이 실패되었습니다');
	            location.reload();
	        }
	    })
	    .catch(error => {
	        console.error('Error:', error);
	        alert('An error occurred. Please try again.');
	    });
	}

	function rejectOrder() {
	    const form = document.getElementById('rejectOrder');
	    const storeNo = form.getAttribute('data-store-no');
	    const orderNo = form.getAttribute('data-order-no');

	    fetch(`/order/${storeNo}/${orderNo}/reject`, {
	        method: 'PUT',
	        headers: {
	            'Content-Type': 'application/json'
	        }
	    })
	    .then(response => {
	        if (response.ok) {
	            alert('주문 거절이 완료되었습니다');
	            location.reload();
	        } else {
	            alert('주문 거절이 실패되었습니다');
	            location.reload();
	        }
	    })
	    .catch(error => {
	        console.error('Error:', error);
	        alert('An error occurred. Please try again.');
	    });
	}
	</script>
</body>
</html>
