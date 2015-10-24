<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div style="text-align: center">
<br></br>
		<a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
		<br></br>
		<br></br>
	</div>
	<form:form name='loginForm'
		action="${pageContext.servletContext.contextPath}/viewaccount/?${_csrf.parameterName}=${_csrf.token}"
		method='POST'>
		<table border="3" align="center">
		<tr>
				<th>Select</th>
				<th>User Requested</th>
			</tr>
			<c:forEach var="view" items="${accountView}">
				<tr>
					<td><INPUT TYPE="radio" name="radio" value="${view.userName}"/></td>
					<td><c:out value="${view.userName}" /></td>
				</tr>
			</c:forEach>
			
			</table>
			</br>
		</br>
		<div style="text-align: center">
			<select name="Type">
				<option value="Account">View Account Details</option>
				<option value="Personal">View Personal Details</option>
			</select> <br></br> <br></br> <input name="submit" type="submit"
				value="submit" />
		</div>
		</form:form>
		<br></br>
		<br></br>
		<br></br>
		<c:if test="${not empty AccountDetails}">
		<h2 align="center">
			<u>Account Details</u>
		</h2>
		<table border="3" align="center">
		<tr>
				<th>UserName</th>
				<th>Account Number</th>
				<th>Account Type</th>
				<th>Balance</th>
			</tr>
			<c:forEach var="view" items="${accountDetailsView}">
				<tr>
					<td><c:out value="${view.userNameAccount}" /></td>
					<td><c:out value="${view.accountNumber}" /></td>
					<td><c:out value="${view.accountType}" /></td>
					<td><c:out value="${view.balance}" /></td>
				</tr>
			</c:forEach>
			
			</table>
			</c:if>
		<c:if test="${not empty PersonalDetails}">
		<h2 align="center">
			<u>Personal Details</u>
		</h2>
		<table border="3" align="center">
		<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Gender</th>
				<th>Address</th>
				<th>State</th>
				<th>Zip</th>
				<th>Phone Number</th>
				<th>DOB</th>
				<th>E Mail</th>
			</tr>
			<c:forEach var="view" items="${personalDetailsView}">
				<tr>
					<td><c:out value="${view.firstName}" /></td>
					<td><c:out value="${view.lastName}" /></td>
					<td><c:out value="${view.gender}" /></td>
					<td><c:out value="${view.address}" /></td>
					<td><c:out value="${view.state}" /></td>
					<td><c:out value="${view.zip}" /></td>
					<td><c:out value="${view.phonenumber}" /></td>
					<td><c:out value="${view.dob}" /></td>
					<td><c:out value="${view.email}" /></td>
				</tr>
			</c:forEach>
			
			</table>
		</c:if>	
</body>
</html>