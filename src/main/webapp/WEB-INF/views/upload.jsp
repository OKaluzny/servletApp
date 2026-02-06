<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>File Upload</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; }
        .content { padding: 20px 30px; max-width: 600px; }
        input[type="file"] { margin-top: 10px; }
        button { margin-top: 15px; padding: 10px 25px; background: #3498db; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 15px; }
        button:hover { background: #2980b9; }
        .result { background: #f8f8f8; border: 1px solid #ddd; border-radius: 5px; padding: 15px; margin-top: 20px; }
        .result p { margin: 5px 0; }
        .label { font-weight: bold; color: #555; }
    </style>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <div class="content">
        <h3>File Upload</h3>
        <form action="${pageContext.request.contextPath}/uploadServlet" method="post" enctype="multipart/form-data">
            <label>Select a file:</label>
            <input type="file" name="file" required/>
            <br/>
            <button type="submit">Upload</button>
        </form>

        <c:if test="${not empty fileName}">
        <div class="result">
            <h4>Upload Result</h4>
            <p><span class="label">File Name:</span> ${fileName}</p>
            <p><span class="label">Content Type:</span> ${fileContentType}</p>
            <p><span class="label">Size:</span> ${fileSize} bytes</p>
        </div>
        </c:if>
    </div>
    <jsp:include page="footer.jsp"/>
</body>
</html>
