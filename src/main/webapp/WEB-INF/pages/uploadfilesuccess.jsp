<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Application successfully submitted</title>
<style type="text/css">
</style>
</head>
<body oncopy="return false" oncut="return false" onpaste="return false">
    <noscript>
  <meta http-equiv="refresh" content="0; url=${pageContext.servletContext.contextPath}/logoutusers" />
  Javascript Disabled
</noscript>

		<c:if test="${not empty Successful}">
			<div class="msg">${Successful}</div>
		</c:if>
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