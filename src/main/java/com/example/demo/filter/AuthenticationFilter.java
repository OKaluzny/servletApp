package com.example.demo.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log(">>> AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Let error dispatches pass through so custom error pages render
        if (req.getDispatcherType() == DispatcherType.ERROR) {
            chain.doFilter(request, response);
            return;
        }

        String uri = req.getRequestURI();

        this.context.log("Requested Resource::" + uri);

        HttpSession session = req.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("user") != null;

        boolean publicPage = uri.endsWith("demo/viewByIDServlet") ||
                uri.endsWith("demo/loginServlet") ||
                uri.endsWith("demo/viewServlet");

        if (loggedIn || publicPage) {
            chain.doFilter(request, response);
        } else {
            this.context.log("<<< Unauthorized access request");
            res.sendRedirect(req.getContextPath() + "/loginServlet");
        }
    }

    public void destroy() {
    }
}
