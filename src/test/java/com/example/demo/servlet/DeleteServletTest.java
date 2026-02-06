package com.example.demo.servlet;

import com.example.demo.repository.EmployeeRepository;
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

import static org.mockito.Mockito.*;

class DeleteServletTest {

    private AutoCloseable closeable;

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;

    private final DeleteServlet servlet = new DeleteServlet();

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
    void doPost_deleteSuccess_redirects() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getContextPath()).thenReturn("/demo");

        try (MockedStatic<EmployeeRepository> repo = mockStatic(EmployeeRepository.class)) {
            repo.when(() -> EmployeeRepository.delete(1)).thenReturn(1);

            servlet.doPost(request, response);

            repo.verify(() -> EmployeeRepository.delete(1));
            verify(response).sendRedirect("/demo/viewServlet");
        }
    }

    @Test
    void doPost_deleteFails_sends404() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("999");

        try (MockedStatic<EmployeeRepository> repo = mockStatic(EmployeeRepository.class)) {
            repo.when(() -> EmployeeRepository.delete(999)).thenReturn(0);

            servlet.doPost(request, response);

            verify(response).sendError(HttpServletResponse.SC_NOT_FOUND, "Employee not found");
        }
    }
}
