<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>500 - Internal Server Error</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; padding-top: 50px; }
        h1 { font-size: 72px; color: #e74c3c; margin-bottom: 10px; }
        p { font-size: 18px; color: #555; }
        a { color: #3498db; text-decoration: none; }
        a:hover { text-decoration: underline; }
        .details { background: #f8f8f8; padding: 15px; margin: 20px auto; max-width: 600px; text-align: left; border-radius: 5px; }
    </style>
</head>
<body>
    <h1>500</h1>
    <p>An internal server error occurred.</p>
    <% if (request.getAttribute("jakarta.servlet.error.message") != null) { %>
    <div class="details">
        <strong>Error:</strong> <%= request.getAttribute("jakarta.servlet.error.message") %>
    </div>
    <% } %>
    <p><a href="${pageContext.request.contextPath}/viewServlet">Back to Employee List</a></p>
</body>
</html>
