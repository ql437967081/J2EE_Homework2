package edu.nju.wsql.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;

@WebListener()
public class OnlineCounterListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    private static final String ONLINE_CNT = "onlineCnt";
    private static final String ONLINE_LOGIN_CNT = "onlineLoginCnt";

    // Public constructor is required by servlet spec
    public OnlineCounterListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        sce.getServletContext().setAttribute(ONLINE_CNT, 0);
        sce.getServletContext().setAttribute(ONLINE_LOGIN_CNT, 0);
    }

    private synchronized void changeOnlineCnt(ServletContext sc, int d){
        int onlineCnt = (Integer)sc.getAttribute(ONLINE_CNT) + d;
        sc.setAttribute(ONLINE_CNT, onlineCnt);
    }

    private synchronized void changeOnlineLoginCnt(ServletContext sc, int d){
        int onlineCnt = (Integer)sc.getAttribute(ONLINE_LOGIN_CNT) + d;
        sc.setAttribute(ONLINE_LOGIN_CNT, onlineCnt);
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
        changeOnlineCnt(se.getSession().getServletContext(), 1);
        System.out.println("session created");
        System.out.println();
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
        HttpSession session = se.getSession();
        ServletContext sc = session.getServletContext();/*
        if (session.getAttribute("login") != null){
            changeOnlineLoginCnt(sc, -1);
            System.out.println("login session destroyed");
        }*/
        changeOnlineCnt(sc, -1);
        System.out.println("session destroyed");
        System.out.println();
    }

    private boolean isLoginAttribute(HttpSessionBindingEvent sbe) {
        return sbe.getName().equals("login");
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
      if (isLoginAttribute(sbe)){
          changeOnlineLoginCnt(sbe.getSession().getServletContext(), 1);
          System.out.println("login attribute added");
          System.out.println();
      }
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
        if (isLoginAttribute(sbe)){
            changeOnlineLoginCnt(sbe.getSession().getServletContext(), -1);
            System.out.println("login attribute removed");
            System.out.println();
        }
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attibute
         is replaced in a session.
      */
    }
}
