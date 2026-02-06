<%@ page contentType="text/html;charset=UTF-8" %>
<div style="background: #2c3e50; color: white; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center;">
    <h2 style="margin: 0;">Employee Management</h2>
    <nav>
        <a href="${pageContext.request.contextPath}/viewServlet" style="color: #ecf0f1; margin-right: 15px; text-decoration: none;">Employees</a>
        <a href="${pageContext.request.contextPath}/saveServlet" style="color: #ecf0f1; margin-right: 15px; text-decoration: none;">Add Employee</a>
        <a href="${pageContext.request.contextPath}/uploadServlet" style="color: #ecf0f1; margin-right: 15px; text-decoration: none;">File Upload</a>
        <% if (session.getAttribute("user") != null) { %>
            <span style="color: #2ecc71; margin-right: 10px;">(<%= session.getAttribute("user") %>)</span>
            <a href="${pageContext.request.contextPath}/logoutServlet" style="color: #e74c3c; text-decoration: none;"
               onclick="event.preventDefault(); document.getElementById('logoutForm').submit();">Logout</a>
            <form id="logoutForm" action="${pageContext.request.contextPath}/logoutServlet" method="post" style="display:none;"></form>
        <% } else { %>
            <a href="${pageContext.request.contextPath}/loginServlet" style="color: #2ecc71; text-decoration: none;">Login</a>
        <% } %>
    </nav>
</div>
