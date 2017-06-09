<%--
  Created by IntelliJ IDEA.
  User: 高伟冬
  Date: 2017/6/7
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>sign up page</title>
</head>
<body>
<h1>注册<%=session.getId()%></h1>
<form action="register.jsp" method="post">
    <input type="text" name="nick" placeholder="昵称"><br>
    <input type="text" name="mobile" placeholder="手机号"><br>
    <input type="password" name="password" placeholder="密码"><br>
    hobbies：
    <input type="checkbox" name="hobbies" value="TV">TV
    <input type="checkbox" name="hobbies" value="Movie">Movie
    <input type="checkbox" name="hobbies" value="Game">Game<br>
    cities：
    <select name="cities" multiple>
        <option value="Beijing">Beijing</option>
        <option value="Shanghai">Shanghai</option>
        <option value="Shenzhen">Shenzhen</option>
    </select><br>
    <input type="submit" value="注册">
</form>
<%
    String message = (String) request.getAttribute("message");
    if (message != null) {
        out.print(message);
    }
%>
</body>
</html>