<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Unlock Account</title>
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
	<h1 align="center">Unlock account</h1>
	<h3 align="center">Your account is being locked. Please fill below the details to put in request in the system to unlock it.</h3>
	<div id="login-box" align="center">
		<h3>Enter details</h3>
		<c:if test="${not empty emptyFields}">
			<div class="msg">${emptyFields}</div>
		</c:if>
		<c:if test="${not empty wrongOtp}">
			<div class="msg">${wrongOtp}</div>
		</c:if>
		<c:if test="${not empty incorrectFields}">
			<div class="msg">${incorrectFields}</div>
		</c:if>
		<form name="form"
			action="${pageContext.servletContext.contextPath}/unlockaccount?${_csrf.parameterName}=${_csrf.token}"
			method='POST'>
			<table border="0">
				<tbody>
					<tr>
						<td>Enter the User name:</td>
						<td><input type="text" name="username"></td>
					</tr>
					<tr>
						<td>Enter Account Number:</td>
						<td><input type="text" name="account"></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td></td>
						<td><img id="otp_id" name="otpCaptcha123" src="captcha.jpg"
							hidden="true"> <a href="javascript:;"
							title="Send OTP in email" name="otpButton"
							onclick="document.getElementById('otp_id').src = '${pageContext.servletContext.contextPath}/unlockaccount?' + 'otpButton';  return false">
								Send Otp in Email </a></td>
					</tr>

					<tr>
						<td>OTP :</td>
						<td><input type='text' name='otpCode' class='keyboardInput'></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td><input name="submit" type="submit" value="submit" /></td>
					</tr>


				</tbody>
			</table>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />

		</form>
	</div>
</body>
</html>