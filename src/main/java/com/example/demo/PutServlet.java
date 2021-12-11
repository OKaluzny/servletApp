package com.example.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/putServlet")
public class PutServlet extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String sid = request.getParameter("id");
        int id = Integer.parseInt(sid);

        String name = request.getParameter("name");
        String email = request.getParameter("email");

        Employee employee = new Employee();
        employee.setId(id);
        employee.setName(name);
        employee.setEmail(email);
        employee.setCountry(request.getParameter("country"));

        int status = 0;
        try {
            status = EmployeeRepository.update(employee);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (status > 0) {
            response.sendRedirect("viewServlet");
        } else {
            out.println("Sorry! unable to update record");
        }
        out.close();
    }
}
