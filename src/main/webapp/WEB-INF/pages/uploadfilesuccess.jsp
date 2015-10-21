<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Application successfully submitted</title>
<style type="text/css">
</style>
</head>
<body>
    <br>
    <br>
    <div align="center">
 
        <h1>Application submitted successfully</h1>
        <p>Following files are uploaded successfully.</p>
        <ol>
            <c:forEach items="${files}" var="file">
           - ${file} <br>
            </c:forEach>
        </ol>
        
        <br />
    </div>
    <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
</body>
</html>