package com.example.demo.auth;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;

class LoginServletTest {

    private AutoCloseable closeable;

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private RequestDispatcher dispatcher;
    @Mock private ServletContext servletContext;
    @Mock private HttpSession session;

    private LoginServlet servlet;

    @BeforeEach
    void setUp() throws ServletException {
        closeable = MockitoAnnotations.openMocks(this);
        servlet = new LoginServlet();

        // Inject mock ServletContext via a mock ServletConfig
        jakarta.servlet.ServletConfig config = mock(jakarta.servlet.ServletConfig.class);
        when(config.getServletContext()).thenReturn(servletContext);
        when(config.getServletName()).thenReturn("LoginServlet");
        servlet.init(config);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void doGet_forwardsToLoginPage() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/views/login.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_validCredentials_createsSessionAndRedirects() throws ServletException, IOException {
        when(servletContext.getInitParameter("app.auth.user")).thenReturn("admin");
        when(servletContext.getInitParameter("app.auth.password")).thenReturn("password");
        when(request.getParameter("user")).thenReturn("admin");
        when(request.getParameter("pwd")).thenReturn("password");
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("/demo");

        servlet.doPost(request, response);

        verify(session).setAttribute("user", "admin");
        verify(session).setMaxInactiveInterval(30 * 60);
        verify(response).addCookie(argThat(cookie ->
                "user".equals(cookie.getName()) && "admin".equals(cookie.getValue())
        ));
        verify(response).sendRedirect("/demo/viewServlet");
    }

    @Test
    void doPost_invalidPassword_forwardsWithError() throws ServletException, IOException {
        when(servletContext.getInitParameter("app.auth.user")).thenReturn("admin");
        when(servletContext.getInitParameter("app.auth.password")).thenReturn("password");
        when(request.getParameter("user")).thenReturn("admin");
        when(request.getParameter("pwd")).thenReturn("wrong");
        when(request.getRequestDispatcher("/WEB-INF/views/login.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Either user name or password is wrong!");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_invalidUsername_forwardsWithError() throws ServletException, IOException {
        when(servletContext.getInitParameter("app.auth.user")).thenReturn("admin");
        when(servletContext.getInitParameter("app.auth.password")).thenReturn("password");
        when(request.getParameter("user")).thenReturn("unknown");
        when(request.getParameter("pwd")).thenReturn("password");
        when(request.getRequestDispatcher("/WEB-INF/views/login.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Either user name or password is wrong!");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_validCredentials_cookieIsHttpOnly() throws ServletException, IOException {
        when(servletContext.getInitParameter("app.auth.user")).thenReturn("admin");
        when(servletContext.getInitParameter("app.auth.password")).thenReturn("password");
        when(request.getParameter("user")).thenReturn("admin");
        when(request.getParameter("pwd")).thenReturn("password");
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("/demo");

        servlet.doPost(request, response);

        verify(response).addCookie(argThat(cookie ->
                cookie.isHttpOnly() && cookie.getMaxAge() == 30 * 60
        ));
    }
}
