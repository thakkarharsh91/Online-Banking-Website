<%@ page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!doctype html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Add Government Agency</title>
		<script type = "text/javascript" >
   			 history.pushState(null, null,window.location.href);
    		 window.addEventListener('popstate', function(event) 
    		 {history.pushState(null, null, window.location.href);});
    		 document.addEventListener("contextmenu", function(e)
    		 {e.preventDefault();}, false);
   		 </script>
	</head>	
	<body>
		<form action="addgovernmentagency" method='POST'>
		<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
			<p style="text-align:right"><a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a></p>
			<h2 align="justify">Add Government Agency:</h2>
			<table>
				<tbody>
					<tr>
						<td>Agency Name: </td>
						<td><input type="text" name ="agencyname"></td>
					</tr>
					<tr>
						<td>Agency ID: </td>
						<td><input type="text" name ="agencyid"></td>
					</tr>					
					<tr>
						<td>Agency Email Address: </td>
						<td><input type="text" name ="agencyemail"></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
      					<td></td>
      					<td><button type="submit">Add Government Agency</button></td>
    				</tr>
										
				</tbody>
			</table>
			<p style="text-align:left"><a href="${pageContext.servletContext.contextPath}/adminhome">Admin Home</a></p>			
		</form>
	</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	//timeout/=60;
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/addGovernmentAgency.jsp", "/login");
	response.setHeader("Refresh", timeout + "; URL =" + url);
%>