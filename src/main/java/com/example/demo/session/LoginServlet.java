package com.example.demo.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ConnectException {

        // Это название 2-х параметров, которые мы передаем
        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");
        // Это значение наших параметров
        LoginData.logMap();



        try {
            if (LoginData.logMap.containsKey(user) && LoginData.logMap.get(user).equals(pwd)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", "user");
                //setting session to expiry in 30 mins
                session.setMaxInactiveInterval(30 * 60);
                Cookie userName = new Cookie("user", user);
                userName.setMaxAge(30 * 60);
                response.addCookie(userName);
                PrintWriter out = response.getWriter();
                out.println("Welcome back to the team, " + user + "!");
            } else {
                PrintWriter out = response.getWriter();
                out.println("Either user name or password is wrong!");
            }
        }catch (ConnectException e){
            e.printStackTrace();
        }


        /*if (LoginData.checkLogList(user, pwd)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", "user");
            //setting session to expiry in 30 mins
            session.setMaxInactiveInterval(30 * 60);
            Cookie userName = new Cookie("user", user);
            userName.setMaxAge(30 * 60);
            response.addCookie(userName);
            PrintWriter out = response.getWriter();
            out.println("Welcome back to the team, " + user + "!");
        } else {
            PrintWriter out = response.getWriter();
            out.println("Either user name or password is wrong!");
        }*/

    }
}
