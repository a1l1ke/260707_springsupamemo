<%-- WEB-INF/views/home.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <link type="image/png" sizes="96x96" rel="icon" href="./assets/icons8-memo-96.png">
    <title>홈스윗홈</title>
    <link rel="stylesheet" href="./assets/style.css">
</head>
<body>
<h1>반가워요!</h1>
<section>
    <form method="post">
        <%--        <input name="author" placeholder="작성자를 입력해주세요">--%>
        <textarea
                name="memo" style="resize: none"
                placeholder="메모를 입력해주세요"
        ></textarea>
        <button>등록</button>
    </form>
</section>
<section id="memoList">
    <c:forEach items="${memoList}" var="memo">
        <div class="memo">
            <p>${memo.memo}</p>
        </div>
    </c:forEach>
</section>
</body>
</html>
