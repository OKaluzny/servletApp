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

class PutServletTest {

    private AutoCloseable closeable;

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private RequestDispatcher dispatcher;

    private final PutServlet servlet = new PutServlet();

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void doPost_missingId_sendsBadRequest() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn(null);

        servlet.doPost(request, response);

        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
    }

    @Test
    void doPost_invalidId_sendsBadRequest() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("abc");

        servlet.doPost(request, response);

        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
    }

    @Test
    void doPost_updateSuccess_redirects() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("John");
        when(request.getParameter("email")).thenReturn("john@test.com");
        when(request.getParameter("country")).thenReturn("USA");
        when(request.getContextPath()).thenReturn("/demo");

        try (MockedStatic<EmployeeRepository> repo = mockStatic(EmployeeRepository.class)) {
            repo.when(() -> EmployeeRepository.update(any(Employee.class))).thenReturn(1);

            servlet.doPost(request, response);

            repo.verify(() -> EmployeeRepository.update(any(Employee.class)));
            verify(response).sendRedirect("/demo/viewByIDServlet?id=1");
        }
    }

    @Test
    void doPost_updateFails_forwardsWithError() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("John");
        when(request.getParameter("email")).thenReturn("john@test.com");
        when(request.getParameter("country")).thenReturn("USA");
        when(request.getRequestDispatcher("/WEB-INF/views/employeeForm.jsp")).thenReturn(dispatcher);

        try (MockedStatic<EmployeeRepository> repo = mockStatic(EmployeeRepository.class)) {
            repo.when(() -> EmployeeRepository.update(any(Employee.class))).thenReturn(0);

            servlet.doPost(request, response);

            verify(request).setAttribute("error", "Unable to update record");
            verify(request).setAttribute(eq("employee"), any(Employee.class));
            verify(dispatcher).forward(request, response);
        }
    }
}
