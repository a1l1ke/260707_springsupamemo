<%-- WEB-INF/views/home.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link type="image/png" sizes="96x96" rel="icon" href="./assets/icons8-memo-96.png">
    <title>홈스윗홈</title>
    <style>
        h1 {
            text-align: center;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 12px;
            width: 480px;
            margin: 0 auto;
        }

        textarea {
            resize: none;
            height: 240px;
        }
    </style>
</head>
<body>
<h1>반가워요!</h1>
<section>
    <form method="post">
        <textarea
                name="memo" style="resize: none"
                placeholder="메모를 입력해주세요"
        ></textarea>
        <button>등록</button>
    </form>
</section>
</body>
</html>
