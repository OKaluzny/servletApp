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
import java.util.Optional;

import static org.mockito.Mockito.*;

class EditEmployeeServletTest {

    private AutoCloseable closeable;

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private RequestDispatcher dispatcher;

    private final EditEmployeeServlet servlet = new EditEmployeeServlet();

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void doGet_missingId_sendsBadRequest() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn(null);

        servlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
    }

    @Test
    void doGet_invalidId_sendsBadRequest() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("xyz");

        servlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
    }

    @Test
    void doGet_employeeFound_setsAttributeAndForwards() throws ServletException, IOException {
        Employee employee = new Employee(1, "John", "john@test.com", "USA");

        when(request.getParameter("id")).thenReturn("1");
        when(request.getRequestDispatcher("/WEB-INF/views/employeeForm.jsp")).thenReturn(dispatcher);

        try (MockedStatic<EmployeeRepository> repo = mockStatic(EmployeeRepository.class)) {
            repo.when(() -> EmployeeRepository.getEmployeeById(1)).thenReturn(Optional.of(employee));

            servlet.doGet(request, response);

            verify(request).setAttribute("employee", employee);
            verify(dispatcher).forward(request, response);
        }
    }

    @Test
    void doGet_employeeNotFound_sends404() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("999");

        try (MockedStatic<EmployeeRepository> repo = mockStatic(EmployeeRepository.class)) {
            repo.when(() -> EmployeeRepository.getEmployeeById(999)).thenReturn(Optional.empty());

            servlet.doGet(request, response);

            verify(response).sendError(HttpServletResponse.SC_NOT_FOUND, "Employee not found");
        }
    }
}
