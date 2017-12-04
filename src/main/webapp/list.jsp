<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>RandomFields</title>
    <meta content="text/html">
    <%@ page isELIgnored="false" %>
</head>
<body>
<p>Data from MongoDB</p>
<table border="1">
    <c:forEach items="${list}" var="item">
        <tr>
            <c:forEach items="${item}" var="entry">
                <td><c:out value = "${entry.value}"/></td>
            </c:forEach>
        </tr>
    </c:forEach>
</table>

</body>

</html>

