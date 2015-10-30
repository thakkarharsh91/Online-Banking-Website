<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Pragma" content="no-cache">
 <meta http-equiv="Cache-Control" content="no-cache">
 <meta http-equiv="Expires" content="-1">
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
<body oncopy="return false" oncut="return false" onpaste="return false">
	
	<form>
		<h2 align="justify">Hello <c:out value="${sessionScope.Employee}"/></h2>
		<table width="500" border="0">
			<tbody>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/transact?${_csrf.parameterName}=${_csrf.token}">Create Transaction</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/approvetransaction?${_csrf.parameterName}=${_csrf.token}">Approve/Reject Transaction</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/moddeltransaction?${_csrf.parameterName}=${_csrf.token}">Modify/Delete
							Transaction</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.servletContext.contextPath}/modifyUs?${_csrf.parameterName}=${_csrf.token}">Search User</a></td>
				</tr>
					<tr>
					<td><a href="${pageContext.servletContext.contextPath}/AccountView?${_csrf.parameterName}=${_csrf.token}">View External user</a></td>
				</tr>
				<tr>
					<td><a
						href="${pageContext.servletContext.contextPath}/logoutusers?${_csrf.parameterName}=${_csrf.token}">Logout</a></td>
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
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>