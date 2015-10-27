<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,authentication.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page trimDirectiveWhitespaces="true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manage Users</title>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"  
type="text/javascript" charset="utf-8"></script>  

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
<h1>
<c:if test="${not empty status}">
			<div class="msg"><c:out value="${status}" /></div>
</c:if>
</h1>

<div style="text-align: center">
		<a href="${pageContext.servletContext.contextPath}/Home">Home</a>
<a
href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
</div>
<form:form action="${pageContext.servletContext.contextPath}/changeaccount/?${_csrf.parameterName}=${_csrf.token}" method='POST'>
	<h2 align= "center">Search User</h2>
	<label>Enter Account Number</label>
	<input type=number name="accountnumber" required>
	<input type= submit name="search" value="search by account number">
 
	</form:form>
	
	<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" /> 

</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/searchaccounttochange.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
%>