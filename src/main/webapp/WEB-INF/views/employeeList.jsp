<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employee List</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; }
        .content { padding: 20px 30px; }
        table { border-collapse: collapse; width: 100%; margin-top: 15px; }
        th, td { border: 1px solid #ddd; padding: 10px 14px; text-align: left; }
        th { background: #3498db; color: white; }
        tr:nth-child(even) { background: #f2f2f2; }
        tr:hover { background: #e8f4fd; }
        a.btn { display: inline-block; padding: 5px 12px; border-radius: 3px; text-decoration: none; color: white; font-size: 13px; }
        .btn-view { background: #3498db; }
        .btn-edit { background: #f39c12; }
        .btn-delete { background: #e74c3c; }
    </style>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <div class="content">
        <h3>All Employees</h3>
        <c:if test="${empty employees}">
            <p>No employees found.</p>
        </c:if>
        <c:if test="${not empty employees}">
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Country</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="emp" items="${employees}">
            <tr>
                <td>${emp.id()}</td>
                <td>${emp.name()}</td>
                <td>${emp.email()}</td>
                <td>${emp.country()}</td>
                <td>
                    <a class="btn btn-view" href="${pageContext.request.contextPath}/viewByIDServlet?id=${emp.id()}">View</a>
                    <a class="btn btn-edit" href="${pageContext.request.contextPath}/editEmployee?id=${emp.id()}">Edit</a>
                    <form action="${pageContext.request.contextPath}/deleteServlet" method="post" style="display:inline;"
                          onsubmit="return confirm('Delete this employee?');">
                        <input type="hidden" name="id" value="${emp.id()}"/>
                        <button type="submit" class="btn btn-delete" style="border:none; cursor:pointer;">Delete</button>
                    </form>
                </td>
            </tr>
            </c:forEach>
        </table>
        </c:if>
    </div>
    <jsp:include page="footer.jsp"/>
</body>
</html>
