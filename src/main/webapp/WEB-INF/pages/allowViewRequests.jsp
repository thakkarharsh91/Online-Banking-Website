<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript" src="<c:url value="/js/keyboard.js " />"></script>
<link href="<c:url value="/css/keyboard.css" />" rel="stylesheet"
	type="text/css">
<sec:csrfMetaTags />
<title>Request Page</title>
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
<body onload='document.loginForm.username.focus();'>

	<h1 align="center">Sun Devils Bank</h1>

	<div id="login-box" align="center">

		<h3>Access Request</h3>
		<c:if test="${not empty Validity}">
			<div class="msg">${Validity}</div>
		</c:if>
		<form:form name='loginForm'
			action="${pageContext.servletContext.contextPath}/updateAllow/?${_csrf.parameterName}=${_csrf.token}"
			method='POST'>

			<table>
				<tr>
					<td>Username:</td>
					<td><input type='text' name='username'></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>User Type:</td>
					<td><select name="userType">
							<option value="ADMIN">ADMIN</option>
							<option value="USER">USER</option>
					</select></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td></td>
					<td><input name="submit" type="submit" value="submit" /></td>
				</tr>
			</table>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />

		</form:form>
	</div>
	<table border="3" align="center">
		<h2 align="center">
			<u>Requests</u>
		</h2>
		<tr>
			<th>Request ID</th>
			<th>Request To</th>
			<th>Request From</th>
			<th>Request Type</th>
			<th>Request Time</th>
			<th>Request Status</th>
		</tr>
		<c:forEach var="view" items="${requestDetails}">
			<tr>
				<td><c:out value="${view.requstID}" /></td>
				<td><c:out value="${view.rqstTo}" /></td>
				<td><c:out value="${view.rqstFrom}" /></td>
				<td><c:out value="${view.rqstType}" /></td>
				<td><c:out value="${view.rqstTime}" /></td>
				<td><c:out value="${view.rqstStatus}" /></td>
			</tr>
		</c:forEach>

	</table>
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	//timeout/=60;
	String url = request.getRequestURL().toString();
	System.out.println(timeout);
	url = url.replace("/WEB-INF/pages/allowViewRequests.jsp", "/login");
	response.setHeader("Refresh", timeout + "; URL =" + url);
%>