<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page session="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type = "text/javascript" >
    history.pushState(null, null,window.location.href);
    window.addEventListener('popstate', function(event) {
    history.pushState(null, null, window.location.href);
    });
    document.addEventListener("contextmenu", function(e){
        e.preventDefault();
    }, false);
    </script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Confirm</title>
</head>
<body>
	<h2>Action complete:</h2>
	<p>${message}


		<br /> <br /> <br />
		<a href="${pageContext.servletContext.contextPath}/customerhome">Home</a> &nbsp;&nbsp;&nbsp;
		<a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/Individual.Customers/Confirm.Payment.Action.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
%>