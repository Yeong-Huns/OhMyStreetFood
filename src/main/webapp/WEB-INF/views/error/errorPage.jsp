<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%--* author         : Yeong-Huns
* ===========================================================
AUTHOR             NOTE
* -----------------------------------------------------------
Yeong-Huns       최초 생성--%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error Occurred</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            color: #333;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            text-align: center;
        }
        .container {
            background-color: #fff;
            padding: 20px;
            border: 1px solid #ccc;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 500px;
            width: 100%;
        }
        .error-icon {
            font-size: 100px;
            color: #ccc;
            cursor: pointer;
            transition: transform 0.2s, color 0.2s;
            display: inline-block;
        }
        .error-icon:hover {
            color: #333;
        }
        h1 {
            font-size: 24px;
            margin: 20px 0;
        }
        p {
            font-size: 16px;
            margin: 10px 0;
        }
        .code {
            font-weight: bold;
            color: #e74c3c;
        }
        .footer {
            margin-top: 20px;
            font-size: 14px;
            color: #aaa;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="error-icon" id="error-icon">&#x1F615;</div>
    <h1>Error Occurred</h1>
    <p>관리자에게 문의 하세요. ErrorCode: <span class="code"><%= request.getAttribute("code") %></span></p>
    <div class="footer">DNS_PROBE_FINISHED_NXDOMAIN</div>
</div>
<script>

    const errorIcon = document.getElementById('error-icon');
    const errorMessage = '<%= request.getAttribute("message") %>'; // 실제 메시지를 서버에서 받아와야 함

    console.log(errorMessage);

    errorIcon.addEventListener('mouseover', () => {
        errorIcon.style.transform = 'scale(1.2)';
        setTimeout(() => {
            errorIcon.innerHTML = '&#x1F61F;';
        }, 100);
    });

    errorIcon.addEventListener('mouseout', () => {
        errorIcon.style.transform = 'scale(1)';
        setTimeout(() => {
            errorIcon.innerHTML = '&#x1F615;';
        }, 100);
    });


    let clickCount = 0;
    let clickTimer = null;

    errorIcon.addEventListener('click', () => {
        clickCount++;
        if (clickCount === 1) {
            clickTimer = setTimeout(() => {
                clickCount = 0;
            }, 1000);
        }
        if (clickCount === 4) {
            clearTimeout(clickTimer);
            window.location.href = '/errorTestPage';
        }
    });
</script>
</body>
</html>
