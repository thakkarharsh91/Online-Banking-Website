<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ page isELIgnored='true' %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

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
<div id=creditAndDebit align="center">
	<h3>Credit And Debit Transactions</h3>
	<c:if test="${not empty emptyFields}">
			<div class="msg">${emptyFields}</div>
	</c:if>
	<c:if test="${not empty insuffFunds}">
			<div class="msg">${insuffFunds}</div>
	</c:if>
	<form:form name='creditAndDebitForm'
			action="${pageContext.servletContext.contextPath}/creditAndDebitFull/?${_csrf.parameterName}=${_csrf.token}" method='POST'>
			<table>
				<tr>
					<td><input type="radio" name="transaction" value="credit" checked>Credit</td>
  					<td><input type="radio" name="transaction" value="debit">Debit</td>
  				</tr>
  				<tr>
  					<td>Select The account:</td>
  					<td>
  						<select name="transactions">
  							<c:forEach var="account" items="${accountNumbers}" >
  								<option><c:out value="${account}"/></option>
  							</c:forEach>
  						</select>
  					</td>
  				</tr>
  				<tr>
  					<td>Enter the Amount:</td>
					<td><input type='text' name='amount'></td>
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