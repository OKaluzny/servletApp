package com.example.demo.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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

        String uri = req.getRequestURI();

        Map<String, Object> uMap = new HashMap<>();
        uMap.put("uri1", uri.endsWith("demo/saveServlet"));
        uMap.put("uri2", uri.endsWith("demo/loginServlet"));
        uMap.put("uri3", uri.endsWith("demo/viewServlet"));
        uMap.put("uri4", uri.endsWith("demo/viewByIDServlet"));
        uMap.put("uri5", uri.endsWith("demo/putServlet"));

        this.context.log("Requested Resource::http://localhost:8080" + uri);

        HttpSession session = req.getSession(false);

        if (session == null && !(uMap.containsKey("uri1")
                || uMap.containsKey("uri2")
                || uMap.containsKey("uri3")
                || uMap.containsKey("uri4")
                || uMap.containsKey("uri5"))) {
            this.context.log("<<< Unauthorized access request");
            PrintWriter out = res.getWriter();
            out.println("No access!!!");
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
          //close any resources here
    }
}