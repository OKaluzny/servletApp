package com.example.demo.filter;

import jakarta.servlet.*;
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

class AuthenticationFilterTest {

    private AutoCloseable closeable;

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private FilterChain chain;
    @Mock private ServletContext servletContext;
    @Mock private HttpSession session;

    private AuthenticationFilter filter;

    @BeforeEach
    void setUp() throws ServletException {
        closeable = MockitoAnnotations.openMocks(this);
        filter = new AuthenticationFilter();

        FilterConfig filterConfig = mock(FilterConfig.class);
        when(filterConfig.getServletContext()).thenReturn(servletContext);
        filter.init(filterConfig);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void doFilter_errorDispatch_passesThrough() throws IOException, ServletException {
        when(request.getDispatcherType()).thenReturn(DispatcherType.ERROR);

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilter_publicPage_viewServlet_passesThrough() throws IOException, ServletException {
        when(request.getDispatcherType()).thenReturn(DispatcherType.REQUEST);
        when(request.getRequestURI()).thenReturn("/demo/viewServlet");
        when(request.getSession(false)).thenReturn(null);

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilter_publicPage_loginServlet_passesThrough() throws IOException, ServletException {
        when(request.getDispatcherType()).thenReturn(DispatcherType.REQUEST);
        when(request.getRequestURI()).thenReturn("/demo/loginServlet");
        when(request.getSession(false)).thenReturn(null);

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilter_publicPage_viewByIDServlet_passesThrough() throws IOException, ServletException {
        when(request.getDispatcherType()).thenReturn(DispatcherType.REQUEST);
        when(request.getRequestURI()).thenReturn("/demo/viewByIDServlet");
        when(request.getSession(false)).thenReturn(null);

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilter_loggedInUser_passesThrough() throws IOException, ServletException {
        when(request.getDispatcherType()).thenReturn(DispatcherType.REQUEST);
        when(request.getRequestURI()).thenReturn("/demo/editEmployee");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn("admin");

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilter_notLoggedIn_protectedPage_redirectsToLogin() throws IOException, ServletException {
        when(request.getDispatcherType()).thenReturn(DispatcherType.REQUEST);
        when(request.getRequestURI()).thenReturn("/demo/editEmployee");
        when(request.getSession(false)).thenReturn(null);
        when(request.getContextPath()).thenReturn("/demo");

        filter.doFilter(request, response, chain);

        verify(chain, never()).doFilter(request, response);
        verify(response).sendRedirect("/demo/loginServlet");
    }
}
