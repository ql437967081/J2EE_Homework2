<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2018/12/30
  Time: 18:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
<h1 align=center>客户登录</h1>
<form method="post" action="/login">
    <table border="5" align="center">
        <tr>
            <td style='padding:5px'>客户id: </td>
            <td style='padding:5px'>
                <input type='text' name='login' value='<%= request.getAttribute("login") %>'>
            </td>
        </tr>
        <tr>
            <td style='padding:5px'>密码: </td>
            <td style='padding:5px'>
                <input type='password' name='password' value=''>
            </td>
        </tr>
        <tr>
            <td style='padding:5px' colspan='2' align='center'>
                <input type='submit' name='Submit' value='登录'>
            </td>
        </tr>
    </table>
</form>
<%@include file="../info_page/online_count.jsp"%>
</body>
</html>
