<%@page import="gunpreetPackage.Model.Authentication"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reset Password</title>
<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

#login-box {
	width: 300px;
	padding: 20px;
	margin: 100px auto;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}
</style>
</head>
<body>
	<h1 align="center">Reset Password</h1>
	<div id="login-box" align="center">
		<h3>Change Password</h3>
		<c:if test="${not empty errormessage}">
			<div class="msg">${errormessage}</div>
		</c:if>
		<c:if test="${not empty emptyFields}">
			<div class="msg">${emptyFields}</div>
		</c:if>
		<form name="form"
			action="${pageContext.servletContext.contextPath}/resetButton?${_csrf.parameterName}=${_csrf.token}"
			method='POST'>
			<table border="0">
				<tbody>
					<tr>
						<th scope="col">&nbsp;</th>
						<th scope="col">&nbsp;</th>
					</tr>
					<tr>
						<td>Username:</td>
						<td><c:out value="${user}" /></td>
					</tr>
					<tr>
						<td>Current Password:</td>
						<td><input type="password" name="current"></td>
					</tr>
					<tr>
						<td>New Password:</td>
						<td><input type="password" name="new"></td>
					</tr>
					<tr>
						<td>Confirm New Password:</td>
						<td><input type="password" name="confirm"></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td><button type="submit">Reset Password</button></td>
					</tr>
				</tbody>
			</table>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			
		</form>
	</div>
</body>
</html>