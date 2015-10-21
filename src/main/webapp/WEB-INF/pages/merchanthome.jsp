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
					<td><a href="#">View Balance</a></td>
				</tr>
				<tr>
					<td><a href="#">Edit PII</a></td>
				</tr>
				<tr>
					<td><a href="#">Debit and Credit Funds</a></td>
				</tr>
				<tr>
					<td><a href="#">Add A Recipient</a></td>
				</tr>
				<tr>
					<td><a href="#">Internal Funds Transfer</a></td>
				</tr>
				<tr>
					<td><a href="#">External Funds Transfer</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/merchantstartpayment">Make a Payment Request</a></td>
				</tr>
				<tr>
					<td><a href="#">Request Payment from Merchant</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/merchantshowpayments">View Payment Lists</a></td>
				</tr>
				<tr>
					<td><a href="#">Submit Payment to Bank</a></td>
				</tr>
				<tr>
					<td><a href="#">Permission to Modify Personal Information</a></td>
				</tr>
				<tr>
					<td><a href="#">Reset Password</a></td>
				</tr>
				<tr>
					<td><a href="#">Allow View Access</a></td>
				</tr>
				<tr>
					<td><a href="#">Authorize Transaction for Approval</a></td>
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