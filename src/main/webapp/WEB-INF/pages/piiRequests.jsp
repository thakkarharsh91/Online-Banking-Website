<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PII Requests</title>
		<script type = "text/javascript" >
   			 history.pushState(null, null,window.location.href);
    		 window.addEventListener('popstate', function(event) 
    		 {history.pushState(null, null, window.location.href);});
    		 document.addEventListener("contextmenu", function(e)
    		 {e.preventDefault();}, false);
   		 </script>
</head>
<body>


	<form action="updatepiistatus" method='POST'>
	<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
	<p style="text-align: right"><a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a></p>
	<h2 align="justify">PII Requests:</h2>
	<table>		
	<tr>
				<th>Select</th>
				<th>Government Request ID</th><th></th>				
				<th>Username</th><th></th>
				<th>First Name</th><th></th>
				<th>Last Name</th><th></th>
				<th>Account Number</th><th></th>
				<th>Email</th><th></th>
				<th>Phone Number</th><th></th>
				<th>Address</th><th></th>
				
								
	</tr>			
	<c:forEach var="showList" items="${showList}">
				<tr>
					<td><input type="checkbox" name="checkList" value="${showList.pid}"></td>
					<td><c:out value="${showList.pid}" /></td><td></td>
					<td><c:out value="${showList.username}" /></td><td></td>
					<td><c:out value="${showList.firstname}" /></td><td></td>
					<td><c:out value="${showList.lastname}" /></td><td></td>
					<td><c:out value="${showList.accountnumber}" /></td><td></td>
					<td><c:out value="${showList.email}" /></td><td></td>	
					<td><c:out value="${showList.phonenumber}" /></td><td></td>
					<td><c:out value="${showList.address}" /></td><td></td>			
				</tr>
	</c:forEach>			
	</table>
		<div style="text-align: center">
			<select name="Type"><option value="Approve">Approve</option><option value="Reject">Reject</option></select> 
			<br></br> <br></br> <input name="submit" type="submit" value="submit" />
		</div>		
	</form>
	<p style="text-align:left"><a href="${pageContext.servletContext.contextPath}/adminhome">Admin Home</a></p>
	
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	//timeout/=60;
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/modifyUsers.jsp", "/login");
	response.setHeader("Refresh", timeout + "; URL =" + url);
%>