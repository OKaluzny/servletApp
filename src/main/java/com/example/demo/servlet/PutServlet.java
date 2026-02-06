package com.example.demo.servlet;


import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/putServlet")
public class PutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String sid = request.getParameter("id");
        if (sid == null || sid.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(sid);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
            return;
        }

        String name = request.getParameter("name");
        String email = request.getParameter("email");

        Employee employee = new Employee(id, name, email, request.getParameter("country"));

        int status = EmployeeRepository.update(employee);
        if (status > 0) {
            response.sendRedirect(request.getContextPath() + "/viewByIDServlet?id=" + id);
        } else {
            request.setAttribute("employee", employee);
            request.setAttribute("error", "Unable to update record");
            request.getRequestDispatcher("/WEB-INF/views/employeeForm.jsp").forward(request, response);
        }
    }
}
