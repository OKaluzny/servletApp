package com.example.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.Mockito.*;

class RequestLoggingFilterTest {

    private AutoCloseable closeable;

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private FilterChain chain;
    @Mock private ServletContext servletContext;

    private RequestLoggingFilter filter;

    @BeforeEach
    void setUp() throws ServletException {
        closeable = MockitoAnnotations.openMocks(this);
        filter = new RequestLoggingFilter();

        FilterConfig filterConfig = mock(FilterConfig.class);
        when(filterConfig.getServletContext()).thenReturn(servletContext);
        filter.init(filterConfig);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void doFilter_logsParameters_andContinuesChain() throws IOException, ServletException {
        when(request.getParameterNames()).thenReturn(Collections.enumeration(java.util.List.of("name")));
        when(request.getParameter("name")).thenReturn("John");
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        when(request.getCookies()).thenReturn(null);

        filter.doFilter(request, response, chain);

        verify(servletContext).log("127.0.0.1::Request Params::{name=John}");
        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilter_masksPassword() throws IOException, ServletException {
        when(request.getParameterNames()).thenReturn(Collections.enumeration(java.util.List.of("pwd")));
        when(request.getParameter("pwd")).thenReturn("secret");
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        when(request.getCookies()).thenReturn(null);

        filter.doFilter(request, response, chain);

        verify(servletContext).log("127.0.0.1::Request Params::{pwd=******}");
        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilter_masksPasswordField() throws IOException, ServletException {
        when(request.getParameterNames()).thenReturn(Collections.enumeration(java.util.List.of("password")));
        when(request.getParameter("password")).thenReturn("secret123");
        when(request.getRemoteAddr()).thenReturn("192.168.1.1");
        when(request.getCookies()).thenReturn(null);

        filter.doFilter(request, response, chain);

        verify(servletContext).log("192.168.1.1::Request Params::{password=******}");
        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilter_logsCookies() throws IOException, ServletException {
        when(request.getParameterNames()).thenReturn(Collections.emptyEnumeration());
        Cookie cookie = new Cookie("user", "admin");
        when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        filter.doFilter(request, response, chain);

        verify(servletContext).log("127.0.0.1::Cookie::{user,admin}");
        verify(chain).doFilter(request, response);
    }
}
