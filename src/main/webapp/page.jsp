<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.js"></script>
    <script>
        $(document).ready(window.setInterval(getUsers, <c:out value="${time}"/>));
        function getUsers() {

            $.ajax({
                url: "/users/get",
                headers: { 'Authorization': '<c:out value="${token}"/>' }
            }).then(function(data) {
                $('table tr').remove();
                var tr;
                for (var i = 0; i < data.length; i++) {
                    tr = $('<tr/>');
                    tr.append("<td>" + data[i].name + "</td>");
                    tr.append("<td>" + data[i].password + "</td>");
                    tr.append("<td>" + data[i].roles + "</td>");
                    $('table').append(tr);
                }
            });
        }
    </script>
</head>
<body>
<table></table>
</body>
</html>