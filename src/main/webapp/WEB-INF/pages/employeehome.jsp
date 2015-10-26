<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
	<form>
		<h2 align="justify">Hello <c:out value="${sessionScope.Employee}"/></h2>
		<table width="500" border="0">
			<tbody>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/transact">Create Transaction</a></td>
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
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/employeehome.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
%>