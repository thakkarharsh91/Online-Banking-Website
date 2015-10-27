<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
	<form>
		<h2 align="justify">Hello <c:out value="${sessionScope.Admin}"/></h2>
		<table width="500" border="0">
			<tbody>
				<tr>
					<td><a href="${pageContext.request.contextPath}/authRequest?${_csrf.parameterName}=${_csrf.token}">View Requests</a></td>
				</tr>
				<tr>
					<td><a href="http://www.asu.edu/">View Logs</a></td>
				</tr>
				<tr>
					<td><a href="http://www.asu.edu/">Modify/Delete Accounts</a></td>
				</tr>
				<tr>
					<td><a href="http://www.asu.edu/">PII Access</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/unlockinternal">unlock
							internal </a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/reset">Change
							Password</a></td>
				</tr>
				<tr>
					<td><a
						href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a></td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/admin.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
%>