<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>RandomFields</title>
    <meta content="text/html">
    <%@ page isELIgnored="false" %>
</head>
<body>
<form method="post" action="/random/add">
    <c:forEach begin="1" end="${random}" varStatus="loop">
        <p><input type="text" name="${loop.index}" value="${loop.index}"></p>
    </c:forEach>
    <input type="submit" value="Submit">
</form>

</body>

</html>

