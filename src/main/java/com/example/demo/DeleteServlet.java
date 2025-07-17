package com.example.demo;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/deleteServlet")
public class DeleteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String sid = request.getParameter("id");
        int id = Integer.parseInt(sid);
        int status = EmployeeRepository.delete(id);

        if (status > 0) {
            response.sendRedirect("viewServlet");
        } else {
            response.sendRedirect("viewServlet");
        }
    }
}
