<%--
  Created by IntelliJ IDEA.
  User: 高伟冬
  Date: 2017/6/8
  Time: 9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    if (session.getAttribute("nick") == null) {
        response.sendRedirect("index.jsp");
    }
%>
<h1>主页</h1>
<%=session.getAttribute("nick")%><br>
<%
    pageContext.setAttribute("key", "value");
    application.setAttribute("app-key", "app-value");
%>

<p><a href="user?action=logout">注销</a></p>
<hr>
<form action="student" method="post">
    <input type="hidden" name="action" value="add">
    <input type="text" name="name" placeholder="姓名">
    <input type="text" name="gender" placeholder="性别">
    <input type="date" name="dob" placeholder="出生日期">
    <input type="submit" value="添加">
</form>
</body>
</html>
