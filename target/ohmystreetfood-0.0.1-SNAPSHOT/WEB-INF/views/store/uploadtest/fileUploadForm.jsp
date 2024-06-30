<!-- fileUploadForm.jsp -->
<body>
    <h3>파일 업로드 (여러 개 파일 업로드)</h3>
        <form id="fileUploadFormMulti" method="post" action="/upload/fileUploadMultiple" enctype="multipart/form-data">
        파일 : <input type="file" id="uploadFileMulti" name="uploadFileMulti" multiple="multiple"><br><br>
        <input type="submit" value="업로드">
        </form>
  </body>