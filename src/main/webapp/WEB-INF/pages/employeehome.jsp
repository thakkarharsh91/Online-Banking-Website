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
		<h2 align="justify">Hello XYZ</h2>
		<table width="500" border="0">
			<tbody>
				<tr>
					<td><a href=${pageContext.servletContext.contextPath}/transact">Create Transaction</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/approvetransaction">Approve Transaction</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/moddeltransaction">Modify/Delete
							Transaction</a></td>
				</tr>
				<tr>
					<td><a href="./modifyUs">Search User</a></td>
				</tr>
					<tr>
					<td><a href="${pageContext.servletContext.contextPath}/viewaccount">View External user</a></td>
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
	//timeout/=60;
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/customerhome.jsp", "/login");
	response.setHeader("Refresh", timeout + "; URL =" + url);
%>