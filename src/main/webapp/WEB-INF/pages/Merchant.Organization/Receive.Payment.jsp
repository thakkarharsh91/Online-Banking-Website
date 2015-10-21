<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>
<%@ page import="java.util.*,soroosh.AccountInfo"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form:form
		action="${pageContext.servletContext.contextPath}/merchantreceivepayment/?${_csrf.parameterName}=${_csrf.token}"
		method="post">

		<table>
			<tr>
				<td>Payment Payer:</td>
				<td>${payment_payer}</td>
			</tr>
			<tr>
				<td>Amount:</td>
				<td>${payment_amount}</td>
			</tr>
			<tr></tr>
			<tr>
				<td>Select the bank account:</td>
				<td><select name="accountNumber">
						<c:forEach items="${bankaccounts}" var="bankaccount">
							<option value="${bankaccount.getAccountNumber()}">
								${bankaccount.getAccountNumber()}
								(${bankaccount.getAccountType()}) - B:
								${bankaccount.getBalance()}</option>
						</c:forEach>
				</select></td>
			</tr>
		</table>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		<input type="submit" name="receivepayment" value="Receive Payment" />
	</form:form>


		<br /> <br /> <br /> 
		<a href="${pageContext.servletContext.contextPath}/merchanthome">Home</a>&nbsp;&nbsp;&nbsp;
		<a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
</body>
</html>