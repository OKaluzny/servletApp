<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; }
        .content { padding: 20px 30px; max-width: 400px; }
        label { display: block; margin-top: 12px; font-weight: bold; }
        input[type="text"], input[type="password"] { width: 100%; padding: 8px; margin-top: 4px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        button { margin-top: 18px; padding: 10px 25px; background: #27ae60; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 15px; }
        button:hover { background: #219a52; }
        .error { color: #e74c3c; margin-top: 10px; }
    </style>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <div class="content">
        <h3>Login</h3>
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
        <form action="${pageContext.request.contextPath}/loginServlet" method="post">
            <label>Username:
                <input type="text" name="user" required/>
            </label>
            <label>Password:
                <input type="password" name="pwd" required/>
            </label>
            <button type="submit">Login</button>
        </form>
    </div>
    <jsp:include page="footer.jsp"/>
</body>
</html>
