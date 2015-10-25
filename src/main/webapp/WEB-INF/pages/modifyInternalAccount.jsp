<%@ page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
		<script type = "text/javascript" >
   			 history.pushState(null, null,window.location.href);
    		 window.addEventListener('popstate', function(event) 
    		 {history.pushState(null, null, window.location.href);});
    		 document.addEventListener("contextmenu", function(e)
    		 {e.preventDefault();}, false);
   		 </script>
</head>
<body>
		<form action="modifyinternaluser" method='POST'>
		<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
		<p style="text-align: right"><a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
		</p>
			<h2 align="justify">Modify Internal User Information:</h2>
			<table >
				<tbody>
					<tr>
						<td>Enter Username: </td>
						<td><input type="text" name ="username"></td>
					</tr>
					<tr>
						<!-- <td>Designation: </td>
						<td>
							<select name="designation">
								<option value="employee">Employee</option>
								<option value="manager">Manager</option>
							</select>
						</td> -->
					</tr>
					<tr>
						<td>Select the field for update: </td>
						<td>
							<select name="column">
								<option value="address">Address</option>
								<option value="email">Email Id</option>
								<option value="phonenumber">Phone Number</option>
							</select>
						</td>
					</tr>
					<!-- <tr>
						<td>Current Information: </td>
						<td><input type="text" name = "currentinfo"></td>
					</tr> -->
					<tr>
						<td>New Information:</td>
						<td><input type="text" name = "newinfo"></td>
					</tr>
					<!-- <tr>
						<td>Confirm New Information: </td>
						<td><input type="text"></td>
					</tr> -->
					
					<tr>
					<td></td>
					<td><button type="submit">Modify Internal Accounts</button></td>
		  
					</tr>
				</tbody>
			</table>
		</form>
		<p style="text-align:left"><a href="${pageContext.servletContext.contextPath}/adminhome">Admin Home</a></p>
	
	</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	//timeout/=60;
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/modifyInternalAccount.jsp", "/login");
	response.setHeader("Refresh", timeout + "; URL =" + url);
%>