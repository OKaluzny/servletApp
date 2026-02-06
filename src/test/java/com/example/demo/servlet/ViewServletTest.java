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
import java.util.List;

import static org.mockito.Mockito.*;

class ViewServletTest {

    private AutoCloseable closeable;

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private RequestDispatcher dispatcher;

    private final ViewServlet servlet = new ViewServlet();

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void doGet_setsEmployeesAttributeAndForwards() throws ServletException, IOException {
        List<Employee> employees = List.of(
                new Employee(1, "John", "john@test.com", "USA"),
                new Employee(2, "Jane", "jane@test.com", "UK")
        );

        try (MockedStatic<EmployeeRepository> repo = mockStatic(EmployeeRepository.class)) {
            repo.when(EmployeeRepository::getAllEmployees).thenReturn(employees);
            when(request.getRequestDispatcher("/WEB-INF/views/employeeList.jsp")).thenReturn(dispatcher);

            servlet.doGet(request, response);

            verify(request).setAttribute("employees", employees);
            verify(dispatcher).forward(request, response);
        }
    }

    @Test
    void doGet_emptyList() throws ServletException, IOException {
        try (MockedStatic<EmployeeRepository> repo = mockStatic(EmployeeRepository.class)) {
            repo.when(EmployeeRepository::getAllEmployees).thenReturn(List.of());
            when(request.getRequestDispatcher("/WEB-INF/views/employeeList.jsp")).thenReturn(dispatcher);

            servlet.doGet(request, response);

            verify(request).setAttribute("employees", List.of());
            verify(dispatcher).forward(request, response);
        }
    }
}
