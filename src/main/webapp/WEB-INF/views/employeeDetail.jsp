<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employee Detail</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; }
        .content { padding: 20px 30px; }
        .card { border: 1px solid #ddd; border-radius: 6px; padding: 20px; max-width: 400px; margin-top: 15px; }
        .card p { margin: 8px 0; }
        .card .label { font-weight: bold; color: #555; }
        a.btn { display: inline-block; padding: 7px 16px; border-radius: 3px; text-decoration: none; color: white; margin-top: 15px; margin-right: 8px; }
        .btn-edit { background: #f39c12; }
        .btn-back { background: #3498db; }
    </style>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <div class="content">
        <h3>Employee Detail</h3>
        <c:if test="${not empty employee}">
        <div class="card">
            <p><span class="label">ID:</span> ${employee.id()}</p>
            <p><span class="label">Name:</span> ${employee.name()}</p>
            <p><span class="label">Email:</span> ${employee.email()}</p>
            <p><span class="label">Country:</span> ${employee.country()}</p>
        </div>
        <a class="btn btn-edit" href="${pageContext.request.contextPath}/editEmployee?id=${employee.id()}">Edit</a>
        <a class="btn btn-back" href="${pageContext.request.contextPath}/viewServlet">Back to List</a>
        </c:if>
    </div>
    <jsp:include page="footer.jsp"/>
</body>
</html>
