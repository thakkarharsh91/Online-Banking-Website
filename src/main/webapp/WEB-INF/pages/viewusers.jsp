<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,authentication.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${users != null && users.size() != 0}">
	Here are all system users:
	<table>
		<th>username</th>
		<th>password</th>
		<th>email</th>
		<th>firstName</th>
		<th>lastName</th>

		<c:forEach items="${users}" var="user">
			<tr>
				<td><c:out value="${user.username}" />
				<td>
				<td><c:out value="${user.password}" />
				<td>
				<td><c:out value="${user.email}" />
				<td>
				<td><c:out value="${user.firstName}" />
				<td>
				<td><c:out value="${user.lastName}" />
				<td>
			</tr>
		</c:forEach>
	</table>
	</c:if>
</body>
</html>