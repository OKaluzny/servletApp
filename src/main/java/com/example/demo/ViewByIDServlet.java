package com.example.demo;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;


@WebServlet("/viewByIDServlet")
public class ViewByIDServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String sid = request.getParameter("id");
        int id = Integer.parseInt(sid);

        //List<Employee> employee = EmployeeRepository.getEmployeeById(id);
        //Optional<Employee> employee = EmployeeRepository.getEmployeeById(id);
        Optional<Object> employee = EmployeeRepository.getEmployeeById(id);

        out.print(employee);
        out.close();
    }
}
