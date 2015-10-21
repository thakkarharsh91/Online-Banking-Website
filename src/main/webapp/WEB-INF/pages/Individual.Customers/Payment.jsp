<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value="/css/keyboard.css" />" rel="stylesheet"
	type="text/css">
<title>Insert title here</title>
</head>
<body>

	<form:form
		action="${pageContext.servletContext.contextPath}/readuserpaymentform/?${_csrf.parameterName}=${_csrf.token}"
		method="post">

		<table>
			<tr>
				<td>Select the merchant:</td>
				<td><select name="merchantName">
						<c:forEach items="${merchants}" var="merchant">
							<option value="${merchant}">${merchant}</option>
						</c:forEach>
				</select></td>
			</tr>
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
			<tr>
				<td>Enter amount:</td>
				<td><input type="text" name="amount" /></td>
			</tr>
			<tr>
				<td>OTP:</td>
				<td><input type="text" name="otpText" class="keyboardInput"/></td>
				<td><img id="otp_id" name="otpCaptcha123" src="refresh.jpg"
					hidden="true"> <a href="javascript:;"
					title="Send OTP in email" name="otpButton"
					onclick="document.getElementById('otp_id').src = '${pageContext.servletContext.contextPath}' + '/otpforpayment';  return false">
						Send OTP in Email </a></td>
			</tr>
			<tr>
				<td>Signature:</td>
				<td><input type="text" name="signature" /></td>
			</tr>
		</table>


		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		<input type="submit" name="makepayment" value="Make Payment" />
	</form:form>

	<br/>
	<br/>
	<br/>
	<a href="${pageContext.servletContext.contextPath}/customerhome">Home</a>&nbsp;&nbsp;&nbsp;
		<a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
</body>
</html>