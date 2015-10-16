<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form>
		<h2 align="justify">Hello Admin</h2>
		<table width="500" border="0">
			<tbody>
				<tr>
					<td><a href="http://www.asu.edu/">View Requests</a></td>
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
					<td><a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a></td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>