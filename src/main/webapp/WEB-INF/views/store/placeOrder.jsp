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
</head>
<body>
    <div class="container" style="padding: 60px 40px 0px 40px;">
        <h3 style="text-align: center;">주문요청하기</h3>
        <form id="orderForm" method="post" action="${pageContext.request.contextPath}/store/${store.storeNo}/order/submit">
		    <div class="row">
		        <div class="col-sm-6 form-group">
		            <label for="pickup-date">픽업일자<span style="color: red;">&nbsp;*&nbsp;</span></label>
		            <input type="date" class="form-control" id="pickup-date" name="pickupDate" required>
		        </div>
		        <div class="col-sm-6 form-group">
		            <label for="pickup-time">픽업시간<span style="color: red;">&nbsp;*&nbsp;</span></label>
		            <input type="time" class="form-control" id="pickup-time" name="pickupTime" required>
		        </div>
		    </div>
		
		    <div class="form-group">
		        <label>주문 내역<span style="color: red;">&nbsp;*&nbsp;</span></label>
		        <table class="table table-bordered">
		            <thead>
		                <tr>
		                    <th style="text-align: center;">메 뉴</th>
		                    <th style="text-align: center;">가 격</th>
		                    <th style="text-align: center;">수 량</th>
		                </tr>
		            </thead>
		            <tbody>
		                <c:forEach items="${menus}" var="menu">
		                    <tr>
		                        <td><input type="text" class="form-control" name="menuNames" value="${menu.menuName}" style="text-align: right;" readonly></td>
		                        <td><input type="text" class="form-control price" name="prices" value="${menu.price}" style="text-align: right;" readonly></td>
		                        <td><input type="number" class="form-control quantity" name="quantities" min="0" style="text-align: right;" onchange="calculateTotal()" required></td>
		                    </tr>
		                </c:forEach>
		            </tbody>
		            <tfoot>
		                <tr>
		                    <th colspan="2" style="text-align: center;">총 합 계</th>
		                    <th id="totalPriceInput" style="text-align: right;">0</th>
		                </tr>
		            </tfoot>
		        </table>
		    </div>
		
		    <div class="form-group">
		        <label>메모</label>
		        <input type="text" class="form-control" name="memo">
		    </div>
		
		    <div class="d-flex justify-content-end">
		        <button type="submit" class="btn btn-primary">주문요청하기</button>
		    </div>
		    
		    <input type="hidden" name="totalPrice" id="totalPrice">
		</form>
    </div>
    
    <!-- Menu -->
    <jsp:include page="../menu.jsp" />

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    
    <script>
        function calculateTotal() {
            let total = 0;
            document.querySelectorAll('tbody tr').forEach(row => {
                const price = parseFloat(row.querySelector('.price').value);
                const quantity = parseInt(row.querySelector('.quantity').value);
                if (!isNaN(price) && !isNaN(quantity)) {
                    total += price * quantity;
                }
            });
            document.getElementById('totalPriceInput').innerText = total.toFixed(0);
            document.getElementById('totalPrice').value = total.toFixed(0);
        }

        document.addEventListener('DOMContentLoaded', (event) => {
            const today = new Date().toISOString().split('T')[0];
            document.getElementById('pickup-date').setAttribute('min', today);
        });
    </script>
</body>
</html>
