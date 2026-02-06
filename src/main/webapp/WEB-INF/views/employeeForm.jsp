<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${not empty employee}">Edit Employee</c:when>
            <c:otherwise>Add Employee</c:otherwise>
        </c:choose>
    </title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; }
        .content { padding: 20px 30px; max-width: 500px; }
        label { display: block; margin-top: 12px; font-weight: bold; }
        input[type="text"], input[type="email"] { width: 100%; padding: 8px; margin-top: 4px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        button { margin-top: 18px; padding: 10px 25px; background: #3498db; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 15px; }
        button:hover { background: #2980b9; }
        .error { color: #e74c3c; margin-top: 10px; }
    </style>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <div class="content">
        <h3>
            <c:choose>
                <c:when test="${not empty employee}">Edit Employee</c:when>
                <c:otherwise>Add Employee</c:otherwise>
            </c:choose>
        </h3>

        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>

        <c:choose>
            <c:when test="${not empty employee}">
                <form action="${pageContext.request.contextPath}/putServlet" method="post">
                    <input type="hidden" name="id" value="${employee.id()}"/>
                    <label>Name:
                        <input type="text" name="name" value="${employee.name()}" required/>
                    </label>
                    <label>Email:
                        <input type="email" name="email" value="${employee.email()}" required/>
                    </label>
                    <label>Country:
                        <input type="text" name="country" value="${employee.country()}"/>
                    </label>
                    <button type="submit">Update</button>
                </form>
            </c:when>
            <c:otherwise>
                <form action="${pageContext.request.contextPath}/saveServlet" method="post">
                    <label>Name:
                        <input type="text" name="name" required/>
                    </label>
                    <label>Email:
                        <input type="email" name="email" required/>
                    </label>
                    <label>Country:
                        <input type="text" name="country"/>
                    </label>
                    <button type="submit">Save</button>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
    <jsp:include page="footer.jsp"/>
</body>
</html>
