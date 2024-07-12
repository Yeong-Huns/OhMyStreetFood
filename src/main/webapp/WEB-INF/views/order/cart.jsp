<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ include file="../chat/chatHandler.jsp" %>
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
    <link href="https://fonts.googleapis.com/css2?family=Chakra+Petch:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
    <!-- CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.theme.default.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="container" style="padding: 60px 40px 0px 40px;">
        <h2><center>주문요청하기</center></h2>
        <form id="orderForm" onsubmit="return submitOrder()">
            <div class="row">
		        <div class="col-sm-6 form-group">
		            <label for="pickup-date">픽업일자<span style="color: red;">&nbsp;*&nbsp;</span></label>
		            <input type="date" class="form-control" id="pickup-date" required>
		        </div>
		        <div class="col-sm-6 form-group">
		            <label for="pickup-time">픽업시간<span style="color: red;">&nbsp;*&nbsp;</span></label>
		            <input type="time" class="form-control" id="pickup-time" required>
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
                        <tr>
                            <td><input type="text" class="form-control" name="menu" required></td>
                            <td><input type="text" class="form-control price" name="price" required></td>
                            <td><input type="number" class="form-control quantity" name="quantity" required></td>
                        </tr>
                    </tbody>
                    <tfoot>
                        <tr>
                            <th colspan="2" style="text-align: center;">총 합 계</th>
                            <th id="total-price">0</th>
                        </tr>
                    </tfoot>
                </table>
            </div>
            
	        <div class="form-group">
	            <label>메모</label>
	            <input type="text" class="form-control">
	        </div>
    
            <div class="d-flex justify-content-end">
                <button type="submit" class="btn btn-primary">주문요청하기</button>
            </div>
        </form>
    </div>
    
    <!-- Modal -->
    <div class="modal fade" id="approvalModal" tabindex="-1" aria-labelledby="approvalModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="approvalModalLabel">주문 안내</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    해당 가게 사장님에게 주문이 요청되었습니다<br>
                    사장님 승인이 완료되어야 진짜 주문이 완료됩니다
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="window.location.href = '${pageContext.request.contextPath}/mypage'">확인</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Menu -->
    <jsp:include page="../menu.jsp" />

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    
    <script>
        function submitOrder() {
            // 여기에 실제 주문 제출 코드 추가

            // 모달 표시
            $('#approvalModal').modal('show');
            
            // 폼 전송 방지
            return false;
        }
    </script>
</body>
</html>
