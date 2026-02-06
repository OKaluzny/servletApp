package com.example.demo.servlet;


import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/saveServlet")
public class SaveServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/employeeForm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        if (name == null || name.isBlank() || email == null || email.isBlank()) {
            request.setAttribute("error", "Name and email are required");
            request.getRequestDispatcher("/WEB-INF/views/employeeForm.jsp").forward(request, response);
            return;
        }

        Employee employee = new Employee(name, email, country);

        int status = EmployeeRepository.save(employee);

        if (status > 0) {
            response.sendRedirect(request.getContextPath() + "/viewServlet");
        } else {
            request.setAttribute("error", "Unable to save record");
            request.getRequestDispatcher("/WEB-INF/views/employeeForm.jsp").forward(request, response);
        }
    }
}
