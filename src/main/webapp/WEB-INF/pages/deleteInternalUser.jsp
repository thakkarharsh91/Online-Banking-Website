<%@ page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!doctype html>
<html>
<head>
<title>Delete Internal User</title>
		<script type = "text/javascript" >
   			 history.pushState(null, null,window.location.href);
    		 window.addEventListener('popstate', function(event) 
    		 {history.pushState(null, null, window.location.href);});
    		 document.addEventListener("contextmenu", function(e)
    		 {e.preventDefault();}, false);
   		 </script>
</head>
<body>
	<form action="showInternal" method='POST'>
		<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
		<p style="text-align: right"><a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
		</p>
		<h2 align="justify">Show Internal Users:</h2>
		<tr>
			<td><button type="submit">Show Internal Users</button></td>
		</tr>
	</form>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	<form action="delInternalUser" method='POST'>
	<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
		<h2 align="justify">Delete Internal Users:</h2>
		<table>
			<tr>
				<th>Select</th>
				<th>Username</th>
				<th>First Name</th>
				<th>Last Name</th>
			</tr>
			<c:forEach var="details" items="${list}">
				<tr>
					<td><input type="checkbox" name="cblist" value="${details.username}"></td>
					<td><c:out value="${details.username}" /></td>
					<td><c:out value="${details.firstname}" /></td>
					<td><c:out value="${details.lastname}" /></td>
				</tr>
			</c:forEach>
		</table>		
			<tr>
				<td><button type="submit" id="deleteusers">Delete</button></td>
			</tr>
		</form>
		<p style="text-align:left"><a href="${pageContext.servletContext.contextPath}/adminhome">Admin Home</a></p>
	
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	//timeout/=60;
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/deleteInternalUser.jsp", "/login");
	response.setHeader("Refresh", timeout + "; URL =" + url);
%>