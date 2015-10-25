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
		<h2 align="justify">Hello ABC (Manager)</h2>
		<table width="500" border="0">
			<tbody>
				<tr>
					<td><a href="${pageContext.request.contextPath}/VerifyExternalUser">View Requests</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/modifyUs">Manage User</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/updateAllow?${_csrf.parameterName}=${_csrf.token}">Access Transactions Requests</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/criticaltransaction">Critical Transactions</a></td>
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
	url = url.replace("/WEB-INF/pages/managerhome.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
%>