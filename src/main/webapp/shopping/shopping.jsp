<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2018/12/31
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"
         import="edu.nju.wsql.model.Goods, java.util.Map" %>
<html>
<head>
    <title>购物</title>
</head>
<body>
<h1 align=center>欢迎选购商品</h1>
<%!
    private double round2(double d){
        return Math.round(d * 100) / 100.0;
    }
%>
<form method="get" action="/login">
    <div align="center">
        <span style="padding:0.5% 3%">
            客户id：<%= session.getAttribute("login") %>
        </span>
        <span style="padding:0.5% 3%">
            账户余额：<%= round2((double) request.getAttribute("balance")) %>
        </span>
        <span style="padding:0.5% 3%">
            <input type="submit" name="Logout" value="注销" style="width:80px;height:40px;padding:0.5% 1%;outline:none">
        </span>
    </div>
</form>
<jsp:useBean id="cart" type="edu.nju.wsql.beans.CartBean" scope="session" />
<jsp:useBean id="goods" class="edu.nju.wsql.model.Goods" />
<form method="post" action="/goods_list">
    <table align="center" style="padding:2% 5%">
        <tr>
            <th style="padding:7px 20px">商品名</th>
            <th style="padding:7px 20px">价格</th>
            <th style="padding:7px 20px">数量</th>
        </tr>
        <%
            Map<Integer, Integer> order = cart.getOrder();
            for (Goods g: cart.getList()) {
                goods = g;
                int num = 0;
                int gid = (int) goods.getId();
                if (order.containsKey(gid))
                    num = order.get(gid);
        %>
        <tr>
            <td style="padding:7px 20px"><%= goods.getName() %></td>
            <td style="padding:7px 20px"><%= round2(goods.getPrice()) %></td>
            <td style="padding:7px 20px">
                <input type="number" min="0" name="<%= goods.getId() %>" value="<%= num %>" style="width:100px">
            </td>
        </tr>
        <%
            }
        %>
        <tr>
            <td style="padding:20px" colspan="3" align="center">
                <input type="submit" name="Pay" value="支付"
                       style="width:100px;height:40px;border-width:0;border-radius:3px;background:blue;cursor:pointer;outline:none;color:white;font-size:17px">
            </td>
        </tr>
    </table>
</form>
<%@include file="../info_page/online_count.jsp"%>
</body>
</html>
