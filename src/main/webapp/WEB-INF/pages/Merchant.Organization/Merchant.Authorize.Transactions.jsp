<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.sql.Date"%>
<%@page session="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Authorize Transaction</title>
<style>
body {
	text-align: center;
}

.buttons {
	padding-top: 20px;
}

label {
	display: inline-block;
	width: 140px;
	text-align: center;
}
</style>
<script type = "text/javascript" >
    history.pushState(null, null,window.location.href);
    window.addEventListener('popstate', function(event) {
    history.pushState(null, null, window.location.href);
    });
    document.addEventListener("contextmenu", function(e){
        e.preventDefault();
    }, false);
    </script>
</head>

<body>
	<c:choose>
		<c:when test="${payments != null && payments.size() != 0}">
			<c:set var="count" value="0" scope="page" />
			<h2>Payment Requests to you:</h2>
			<br>
			<br>

			<table>
				<th>Requested by</th>
				<th>amount</th>
				<th>date</th>
				<th>actions</th>
				<c:forEach items="${payments}" var="payment">
					<tr>
						<td><c:out value="${payment.username}" /></td>
						<td><c:out value="${payment.amount}" /></td>
						<td><c:out value="${payment.date.toString()}" /></td>
						<td>
							<form
								action="${pageContext.servletContext.contextPath}/merchantpaymentdecision/"
								method="POST">
								<input type="submit" name="accept" value="Accept Payment" /> <input
									type="submit" name="reject" value="Reject Payment" /> <input
									type="hidden" name="iteration" value="${count}" /> <input
									type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
							</form>
						</td>
					</tr>
					<c:set var="count" value="${count + 1}" scope="page" />
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<h2>There are no payments submitted to you.</h2>
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${paymentsRequests != null && paymentsRequests.size() != 0}">
			<c:set var="counter" value="0" scope="page" />
			<h2>Payment Requests from you:</h2>
			<br>
			<br>

			<table>
				<th>Requested by</th>
				<th>amount</th>
				<th>date</th>
				<th>actions</th>
				<c:forEach items="${paymentsRequests}" var="paymentRequest">
					<tr>
						<td><c:out value="${paymentRequest.username}" /></td>
						<td><c:out value="${paymentRequest.amount}" /></td>
						<td><c:out value="${paymentRequest.date.toString()}" /></td>
						<td>
							<form
								action="${pageContext.servletContext.contextPath}/merchantrequestpaymentdecision/"
								method="POST">
								<input type="submit" name="accept" value="Accept Payment" /> <input
									type="submit" name="reject" value="Reject Payment" /> <input
									type="hidden" name="iteration" value="${counter}" /> <input
									type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
							</form>
						</td>
					</tr>
					<c:set var="counter" value="${counter + 1}" scope="page" />
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<h2>There are no payments requests from you.</h2>
		</c:otherwise>
	</c:choose>
	
	
		<br /> <br /> <br /> 
		<a href="${pageContext.servletContext.contextPath}/merchanthome">Home</a>&nbsp;&nbsp;&nbsp;
		<a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/Merchant.Organization/Merchant.Authorize.Transactions.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
%>