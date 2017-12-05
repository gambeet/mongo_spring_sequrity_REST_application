<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.js"></script>
    <script type="text/javascript">

    </script>
    <script></script>
</head>
<body>
    <form method="post" id="loginForm">
        <input type="text" placeholder="login" name="username">
        <br/>
    <input type="password" placeholder="password" name="password">
        <br/>
    </form>
    <button type="submit" class="a_submit">Submit</button>
    <div class="wrapper"></div>
    <script>
        $('.a_submit').on('click', function (form) {
            form.preventDefault();
            var arr = $("form input"), obj = {};
            $.each(arr, function(indx, el){
                obj[el.name] ? obj[el.name].push(el.value) : (obj[el.name] = el.value);
            });
            var xhr = new XMLHttpRequest();
            var split;
            xhr.open('POST', '/login', false);
            xhr.send(JSON.stringify(obj));

            if (xhr.status != 200) {
                alert( xhr.status + ': ' + xhr.statusText );
            } else {
                split = xhr.getResponseHeader("Authorization").split(" ")[1];
            }
            sessionStorage['token'] = split;
            redirectPost('/page', {'token': split});
        });
        function redirectPost(url, data) {
            var form = document.createElement('form');
            document.body.appendChild(form);
            form.method = 'post';
            form.action = url;
            for (var name in data) {
                var input = document.createElement('input');
                input.type = 'hidden';
                input.name = name;
                input.value = data[name];
                form.appendChild(input);
            }
            form.submit();
        }
    </script>
</body>
</html>