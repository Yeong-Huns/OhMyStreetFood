<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>이동 이벤트 등록하기</title>
    
</head>
<body>
<div id="map" style="width:100%;height:350px;"></div>
<p><em>지도를 움직여 주세요!</em></p> 
<p id="result"></p>
<div>현재 주소: <p id = address></p></div> 
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=cc9b229707b1e16ac706e8a9a8b939d8&libraries=services,clusterer,drawing"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/kakaomap.js"></script>

</body>
</html>