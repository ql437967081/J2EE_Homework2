package edu.nju.wsql.controller;

import edu.nju.wsql.beans.InfoBean;
import edu.nju.wsql.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @GetMapping
    public String getLogin(HttpServletRequest request) {
        String login = "";
        HttpSession session = request.getSession(true);
        Cookie cookie;
        Cookie[] cookies = request.getCookies();

        if (null != cookies) {
            // Look through all the cookies and see if the
            // cookie with the login info is there.
            for (Cookie c : cookies) {
                cookie = c;
                if (cookie.getName().equals("LoginCookie")) {
                    login = cookie.getValue();
                    break;
                }
            }
        }
        request.setAttribute("login", login);

        // Logout action removes session, but the cookie remains
        if (null != request.getParameter("Logout")) {
            if (null != session) {
                session.invalidate();
                System.out.println("After invalidate: " + request.getSession(false));
                request.getSession(true);
            }
        }
        return "/login/login.jsp";
    }

    @PostMapping
    public String login(HttpServletRequest request, HttpServletResponse response) {
        boolean cookieFound = false;
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            // Look through all the cookies and see if the
            // cookie with the login info is there.
            for (Cookie c : cookies) {
                cookie = c;
                if (cookie.getName().equals("LoginCookie")) {
                    cookieFound = true;
                    break;
                }
            }
        }
        String loginValue = request.getParameter("login");
        if (loginValue != null){
            //修改cookie
            if (cookieFound) { // If the cookie exists update the value only
                // if changed
                if (!loginValue.equals(cookie.getValue())) {
                    cookie.setValue(loginValue);
                    response.addCookie(cookie);
                }
            }
            else {
                // If the cookie does not exist, create it and set value
                cookie = new Cookie("LoginCookie", loginValue);
                cookie.setMaxAge(Integer.MAX_VALUE);
                response.addCookie(cookie);
            }

            if (checkIdAndPassword(loginValue, request.getParameter("password"))){
                System.out.println("Before login: " + request.getSession(false));
                HttpSession session = request.getSession(true);
                session.setAttribute("login", loginValue);
                return "redirect:/goods_list";
            }
            else {
                request.setAttribute("info", new InfoBean(
                        "登录失败",
                        "登录失败",
                        "无效的客户ID或者密码错误！",
                        "/login"));
                return "/info_page/info.jsp";
            }
        }
        else
            return "redirect:/login";
    }

    private boolean checkIdAndPassword(String id, String password) {
        return loginService.checkIdAndPassword(Integer.parseInt(id), password);
    }
}
