package com.example.demo.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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

class LogoutServletTest {

    private AutoCloseable closeable;

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;

    private final LogoutServlet servlet = new LogoutServlet();

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void doPost_withSession_invalidatesAndRedirects() throws ServletException, IOException {
        when(request.getCookies()).thenReturn(null);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn("admin");
        when(request.getContextPath()).thenReturn("/demo");

        servlet.doPost(request, response);

        verify(session).invalidate();
        verify(response).sendRedirect("/demo/loginServlet");
    }

    @Test
    void doPost_noSession_redirectsWithoutError() throws ServletException, IOException {
        when(request.getCookies()).thenReturn(null);
        when(request.getSession(false)).thenReturn(null);
        when(request.getContextPath()).thenReturn("/demo");

        servlet.doPost(request, response);

        verify(response).sendRedirect("/demo/loginServlet");
    }

    @Test
    void doPost_withCookies_logsSessionId() throws ServletException, IOException {
        Cookie jsessionCookie = new Cookie("JSESSIONID", "abc123");
        when(request.getCookies()).thenReturn(new Cookie[]{jsessionCookie});
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn("admin");
        when(request.getContextPath()).thenReturn("/demo");

        servlet.doPost(request, response);

        verify(session).invalidate();
        verify(response).sendRedirect("/demo/loginServlet");
    }

    @Test
    void doPost_withNonSessionCookie_doesNotLogSessionId() throws ServletException, IOException {
        Cookie otherCookie = new Cookie("user", "admin");
        when(request.getCookies()).thenReturn(new Cookie[]{otherCookie});
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn("admin");
        when(request.getContextPath()).thenReturn("/demo");

        servlet.doPost(request, response);

        verify(session).invalidate();
        verify(response).sendRedirect("/demo/loginServlet");
    }
}
