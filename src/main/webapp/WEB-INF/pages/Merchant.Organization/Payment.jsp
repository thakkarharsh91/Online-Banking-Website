<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form:form
		action="${pageContext.servletContext.contextPath}/readmerchantpaymentform/?${_csrf.parameterName}=${_csrf.token}"
		method="post">

		<table>
			<tr>
				<td>Request payment from:</td>
				<td><select name="payer">
						<c:forEach items="${merchants}" var="merchant">
							<option value="${merchant}">${merchant}${" (merchant)"}</option>
						</c:forEach>
						<c:forEach items="${users}" var="user">
							<option value="${user}">${user}${" (user)"}</option>
						</c:forEach>
				</select></td>
			</tr>

			<tr>
				<td>Enter amount:</td>
				<td><input type="text" name="amount" /></td>
			</tr>

			<tr>
				<td>Signature:</td>
				<td><input type="text" name="signature" /></td>
			</tr>
		</table>

		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		<input type="submit" name="requestpayment" value="Request Payment" />

	</form:form>


	<br />
	<br />
	<br />
	<a href="${pageContext.servletContext.contextPath}/merchanthome">Home</a>&nbsp;&nbsp;&nbsp;
		<a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
</body>
</html>