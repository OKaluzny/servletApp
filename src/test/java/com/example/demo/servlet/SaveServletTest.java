package com.example.demo.servlet;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SaveServletTest {

    private AutoCloseable closeable;

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private RequestDispatcher dispatcher;

    private final SaveServlet servlet = new SaveServlet();

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void doGet_forwardsToForm() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/views/employeeForm.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_missingName_forwardsWithError() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn(null);
        when(request.getParameter("email")).thenReturn("test@test.com");
        when(request.getParameter("country")).thenReturn("USA");
        when(request.getRequestDispatcher("/WEB-INF/views/employeeForm.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Name and email are required");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_blankEmail_forwardsWithError() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("John");
        when(request.getParameter("email")).thenReturn("  ");
        when(request.getParameter("country")).thenReturn("USA");
        when(request.getRequestDispatcher("/WEB-INF/views/employeeForm.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Name and email are required");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_validData_savesAndRedirects() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("John");
        when(request.getParameter("email")).thenReturn("john@test.com");
        when(request.getParameter("country")).thenReturn("USA");
        when(request.getContextPath()).thenReturn("/demo");

        try (MockedStatic<EmployeeRepository> repo = mockStatic(EmployeeRepository.class)) {
            repo.when(() -> EmployeeRepository.save(any(Employee.class))).thenReturn(1);

            servlet.doPost(request, response);

            repo.verify(() -> EmployeeRepository.save(any(Employee.class)));
            verify(response).sendRedirect("/demo/viewServlet");
        }
    }

    @Test
    void doPost_saveFails_forwardsWithError() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("John");
        when(request.getParameter("email")).thenReturn("john@test.com");
        when(request.getParameter("country")).thenReturn("USA");
        when(request.getRequestDispatcher("/WEB-INF/views/employeeForm.jsp")).thenReturn(dispatcher);

        try (MockedStatic<EmployeeRepository> repo = mockStatic(EmployeeRepository.class)) {
            repo.when(() -> EmployeeRepository.save(any(Employee.class))).thenReturn(0);

            servlet.doPost(request, response);

            verify(request).setAttribute("error", "Unable to save record");
            verify(dispatcher).forward(request, response);
        }
    }

    @Test
    void doPost_blankName_forwardsWithError() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("  ");
        when(request.getParameter("email")).thenReturn("test@test.com");
        when(request.getParameter("country")).thenReturn("USA");
        when(request.getRequestDispatcher("/WEB-INF/views/employeeForm.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Name and email are required");
        verify(dispatcher).forward(request, response);
    }
}
