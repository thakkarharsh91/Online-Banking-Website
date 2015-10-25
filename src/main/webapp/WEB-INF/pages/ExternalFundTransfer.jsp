<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h2>External Funds Transfer</h2>

	<div>
		<c:if test="${not empty destinationError}">
			<div class="msg">${destinationError}</div>
		</c:if>
		<c:if test="${not empty mandatory}">
			<div class="msg">${mandatory}</div>
		</c:if>
		<c:if test="${not empty wrongOtp}">
			<div class="msg">${wrongOtp}</div>
		</c:if>
		<c:if test="${not empty amountError}">
			<div class="msg">${amountError}</div>
		</c:if>
		<c:if test="${not empty balanceInsuficientError}">
			<div class="msg">${balanceInsuficientError}</div>
		</c:if>
		<c:if test="${not empty success}">
			<div class="msg">${success}</div>
		</c:if>
		<c:if test="${not empty failure}">
			<div class="msg">${failure}</div>
		</c:if>
	</div>
	<form>
		<table width="500" border="0">
			<tbody>
				<tr>
					<th scope="col">&nbsp;</th>
					<th scope="col">&nbsp;</th>
				</tr>
				<tr>
					<td>Select the Recipient:</td>
					<td><select name="destinationuseraccount">
							<c:forEach var="item" items="${recepients}">
								<option>
									<c:out value="${item.accountnumber}" />
								</option>
							</c:forEach>
					</select></td>
					<td><a href="./addRecepient">Add a New Recipient</a></td>
				</tr>
				<tr>
					<td>Select the Account:</td>
					<td><select name="sourceuseraccounts">
							<c:forEach var="item" items="${useraccounts}">
								<option>
									<c:out value="${item.accounttype}" />
								</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>Enter the Amount:</td>
					<td><input name="amount" type="text"></td>
				</tr>
				
				<tr>
					<td><img id="otp_id" name="otpCaptcha123" src="captcha.jpg"
						hidden="true"> <a href="javascript:;"
						title="Send OTP in email" name="otpButton"
						onclick="document.getElementById('otp_id').src = '${pageContext.servletContext.contextPath}/externalTransfer?' + 'otpButton';  return false">
							Send Otp in Email </a></td>
					<td></td>
					<td>&nbsp;</td>
				</tr>
				</tr>
				<tr>
					<td>OTP:</td>
					<td><input name="otpCode" type="text"></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><button type="submit" id="submit" name="submit">
							Transfer</button></td>
				</tr>
			</tbody>
		</table>

	</form>
</body>
</html>