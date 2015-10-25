<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error</title>
<script type = "text/javascript" >
    history.pushState(null, null,window.location.href);
    window.addEventListener('popstate', function(event) {
    history.pushState(null, null, window.location.href);
    });
    document.addEventListener("contextmenu", function(e){
        e.preventDefault();
    }, false);
    </script>
</head>
<body>
<h2>The operation was interrupted by errors - please start over: </h2>
		<c:forEach items="${errors}" var="error">
			<p> ${error}
		</c:forEach>
		
		
		<br /> <br /> <br /> 
		<a href="${pageContext.servletContext.contextPath}/merchanthome">Home</a>&nbsp;&nbsp;&nbsp;
		<a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
</body>
</html>