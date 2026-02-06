package com.example.demo.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;

@WebServlet("/uploadServlet")
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 5,      // 5 MB
        maxRequestSize = 1024 * 1024 * 10,   // 10 MB
        fileSizeThreshold = 1024 * 1024       // 1 MB
)
public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/upload.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Part filePart = request.getPart("file");

        if (filePart == null || filePart.getSize() == 0) {
            request.setAttribute("error", "No file selected");
            request.getRequestDispatcher("/WEB-INF/views/upload.jsp").forward(request, response);
            return;
        }

        request.setAttribute("fileName", filePart.getSubmittedFileName());
        request.setAttribute("fileContentType", filePart.getContentType());
        request.setAttribute("fileSize", filePart.getSize());

        request.getRequestDispatcher("/WEB-INF/views/upload.jsp").forward(request, response);
    }
}
