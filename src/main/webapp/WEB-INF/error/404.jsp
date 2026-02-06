<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>404 - Not Found</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; padding-top: 50px; }
        h1 { font-size: 72px; color: #e74c3c; margin-bottom: 10px; }
        p { font-size: 18px; color: #555; }
        a { color: #3498db; text-decoration: none; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <h1>404</h1>
    <p>The page you are looking for does not exist.</p>
    <p>Requested URI: <code><%= request.getAttribute("jakarta.servlet.error.request_uri") %></code></p>
    <p><a href="${pageContext.request.contextPath}/viewServlet">Back to Employee List</a></p>
</body>
</html>
