package com.example.demo.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;

class FileUploadServletTest {

    private AutoCloseable closeable;

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private RequestDispatcher dispatcher;
    @Mock private Part filePart;

    private final FileUploadServlet servlet = new FileUploadServlet();

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void doGet_forwardsToUploadPage() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/views/upload.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_noFile_forwardsWithError() throws ServletException, IOException {
        when(request.getPart("file")).thenReturn(null);
        when(request.getRequestDispatcher("/WEB-INF/views/upload.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute("error", "No file selected");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_emptyFile_forwardsWithError() throws ServletException, IOException {
        when(request.getPart("file")).thenReturn(filePart);
        when(filePart.getSize()).thenReturn(0L);
        when(request.getRequestDispatcher("/WEB-INF/views/upload.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute("error", "No file selected");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_validFile_setsAttributesAndForwards() throws ServletException, IOException {
        when(request.getPart("file")).thenReturn(filePart);
        when(filePart.getSize()).thenReturn(1024L);
        when(filePart.getSubmittedFileName()).thenReturn("test.txt");
        when(filePart.getContentType()).thenReturn("text/plain");
        when(request.getRequestDispatcher("/WEB-INF/views/upload.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute("fileName", "test.txt");
        verify(request).setAttribute("fileContentType", "text/plain");
        verify(request).setAttribute("fileSize", 1024L);
        verify(dispatcher).forward(request, response);
    }
}
