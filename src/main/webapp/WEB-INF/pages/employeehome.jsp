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
					<td><a href="http://www.asu.edu/">Create Transaction</a></td>
				</tr>
				<tr>
					<td><a href="http://www.asu.edu/">View Transaction</a></td>
				</tr>
				<tr>
					<td><a href="http://www.asu.edu/">Modify/Delete
							Transaction</a></td>
				</tr>
				<tr>
					<td><a href="http://www.asu.edu/">Manage External user</a></td>
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