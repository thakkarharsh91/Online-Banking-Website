<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,authentication.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Review External Users</title>
</head>
<body>
	<c:if test="${users != null && users.size() != 0}">
	<h3>Review the below user:</h3>
	<table border ="1">
		<th>Items</th>
		<th>Details</th>
		

		<c:forEach items="${users}" var="user">
			<tr>
				<td>SSN: </td>
				<td><c:out value="${user.ssn}" />
				</td>
			</tr>
			<tr>
				<td>Account Type: </td>
				<td><c:out value="${user.accounttype}" />
				</td>
			</tr>
			<tr>
				<td>User Type: </td>
				<td><c:out value="${user.usertype}" />
				</td>
			</tr>
			<tr>
				<td>First Name: </td>
				<td><c:out value="${user.firstname}" />
				</td>
			</tr>
			<tr>
				<td>Last Name: </td>
				<td><c:out value="${user.lastname}" />
				</td>
			</tr>
			
			<tr>
				<td>Email: </td>
				<td><c:out value="${user.email1}" />
				</td>
			</tr>
			<tr>
				<td>Mobile Number: </td>
				<td><c:out value="${user.phonenumber}" />
				</td>
			</tr>
			<tr>
				<td>Document</td>
				<td><a href="DownloadDocuments">${file1}</a> </td>
			</tr>
		</c:forEach>
	</table>
	</c:if>
</body>
</html>