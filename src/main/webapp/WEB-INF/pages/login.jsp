<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page session="true"%>
<html>
<head>
<script type="text/javascript" src="<c:url value="/js/keyboard.js " />"></script>
<link href="<c:url value="/css/keyboard.css" />" rel="stylesheet"  type="text/css" >
<sec:csrfMetaTags/>
<title>Login Page</title>
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

		<h3>Login with Username and Password</h3>

		<c:if test="${not empty emptyFields}">
			<div class="msg">${emptyFields}</div>
		</c:if>
		<c:if test="${not empty wrongOtp}">
			<div class="msg">${wrongOtp}</div>
		</c:if>
		<c:if test="${not empty wrongCaptcha}">
			<div class="msg">${wrongCaptcha}</div>
		</c:if>
		<c:if test="${not empty wrongCredentials}">
			<div class="msg">${wrongCredentials}</div>
		</c:if>
		<form:form name='loginForm'
			action="${pageContext.servletContext.contextPath}/login/?${_csrf.parameterName}=${_csrf.token}" method='POST'>

			<table>
				<tr>
					<td>Username:</td>
					<td><input type='text' name='username'></td>
				</tr>
				<tr>
					<td>Password :</td>
					<td><input type='password' name='password' /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				 <tr>
					<td>Image :</td>
					<td>
						<div>
							<img id="captcha_id" name="imgCaptcha123" src="captcha.jpg">
						</div>
					</td>


					<td align="left"><a href="javascript:;"
						title="change captcha text" name="imgCaptcha"
						onclick="document.getElementById('captcha_id').src = '<%=request.getContextPath()%>/login?' + 'imgCaptcha';  return false">
							<img src="<%=request.getContextPath()%>/images/refresh.png">
					</a>
					</td>
				</tr>
				<tr>
					<td>Enter Captcha:</td>
					<td><input type="text" name = 'captcha'/></td>
				</tr>  
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
				<td></td>
					<td>
					<img id="otp_id" name="otpCaptcha123" src="captcha.jpg" hidden="true">
					<a href="javascript:;"
						title="Send OTP in email" name="otpButton"
						onclick="document.getElementById('otp_id').src = '${pageContext.servletContext.contextPath}/login?' + 'otpButton';  return false">
							Send Otp in Email
					</a></td>
				</tr>
		
				<tr>
					<td>OTP :</td>
					<td><input type='text' name = 'otpCode' class='keyboardInput'></td>
				</tr>
				<tr>
					<td></td>
					<td><input name="submit" type="submit"
						value="submit" /></td>
				</tr>
			</table>

			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" /> 

		</form:form>
	</div>

</body>
</html>