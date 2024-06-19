<!-- fileUploadMultipleResult.jsp -->
  <body>
  다음의 파일을 전송하였습니다.<br>
  <c:forEach items="${originalFileNameList}" var="file">
      ${file} <br>
  </c:forEach><br>
  (서버에만 출력) Users/gobyeongchae/Desktop/UploadServerFile 확인
  </body>