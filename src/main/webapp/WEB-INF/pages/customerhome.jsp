<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form>
		<table width="500" border="0">
			<tbody>
				<tr>
					<th scope="col">&nbsp;</th>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/viewBal">View Balance</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/editPersonalInfo">Edit PII</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/debitAndCredit">Debit and Credit Funds</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/changeaccount">Change Account Type</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/addRecepient">Add A Recipient</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/internalTransfer">Internal Funds Transfer</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/externalTransfer">External Funds Transfer</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/usermakepayment">Make a Payment</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/downloadStatement" target="_blank">View Statement</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/usershowpayments">Approval of Payment Request</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/reqModify">Permission to Modify Personal Information</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/authRequest?${_csrf.parameterName}=${_csrf.token}">Allow View Access</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/reset">Change
							Password</a></td>
				</tr>
				<tr>
					<td><a
						href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a></td>
				</tr>
			</tbody>
		</table>

	</form>
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/customerhome.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
%>