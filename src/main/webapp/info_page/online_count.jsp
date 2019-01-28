<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2018/12/30
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%! int onlineCnt; %>
<%! int onlineLoginCnt; %>
<% onlineCnt = (Integer) request.getServletContext().getAttribute("onlineCnt"); %>
<% onlineLoginCnt = (Integer) request.getServletContext().getAttribute("onlineLoginCnt"); %>
<div style='padding:10px'>
    <span style='padding:0.5% 3%'>当前在线总人数：<%= onlineCnt %></span>
    <span style='padding:0.5% 3%'>已登录人数：<%= onlineLoginCnt %></span>
    <span style='padding:0.5% 3%'>游客人数：<%= onlineCnt - onlineLoginCnt %></span>
</div>