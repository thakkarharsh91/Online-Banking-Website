<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,authentication.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page trimDirectiveWhitespaces="true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manage Users</title>
<style>
table { 
color: #333;
font-family: Helvetica, Arial, sans-serif;
width: 640px; 
border-collapse: 
collapse; border-spacing: 0; 
}

td, th { 
border: 1px solid transparent; /* No more visible border */
height: 30px; 
transition: all 0.3s;  /* Simple transition for hover effect */
}

th {
background: #DFDFDF;  /* Darken header a bit */
font-weight: bold;
}

td {
background: #FAFAFA;
text-align: center;
}

/* Cells in even rows (2,4,6...) are one color */ 
tr:nth-child(even) td { background: #F1F1F1; }   

/* Cells in odd rows (1,3,5...) are another (excludes header cells)  */ 
tr:nth-child(odd) td { background: #FEFEFE; }  

tr td:hover { background: #666; color: #FFF; } /* Hover cell effect! */

</style>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"  
type="text/javascript" charset="utf-8"></script>  
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
<body oncontextmenu="return false;">
<div>
<a href="">Home</a>
<a
href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
</div>
<form:form name='SearchForm'
			action="${pageContext.servletContext.contextPath}/modifyUs/?${_csrf.parameterName}=${_csrf.token}" method='POST'>
	<select name=searchcat>
	<option value=Name> Name </option>
	<option value=UserName> User Name </option>
	<option value=AccountNumber> Account Number </option>
	
	</select>
	<input type= text name = username>
    <input type= submit name=submit value=search> 
    <br>
    <br> 
	<c:if test="${users != null && users.size() != 0}">:
	<table>
		<th>User Name</th>
		<th>Email</th>
		<th>First Name</th>
		<th>Last Name</th>
		<th>Address</th>
		<th> Phone Number</th>
		<th>Passport Number</th>
		<th>State </th>
		<th>Zip</th>
		<th>Business License</th>

		<c:forEach items="${users}" var="user">
		<form:form name='SearchForm'
			action="${pageContext.servletContext.contextPath}/modifyUs/?${_csrf.parameterName}=${_csrf.token}" method='POST'>
			<tr id="${user.username}">
				<td><input type=text value="${user.username}" disabled/>
				</td>
				<td><input type=text value="${user.email}" disabled />
				</td>
				<td><input type=text value="${user.firstName}" disabled />
				</td>
				<td><input type=text  value="${user.lastName}" disabled /></td>
				<td><input type=text  value="${user.address}" disabled /></td>
				<td><input type=text  value="${user.phonenumber}" disabled /></td>
				<td><input type=text  value="${user.passport}" disabled /></td>
				<td><input type=text  value="${user.state}" disabled /></td>
				<td><input type=text  value="${user.zip}" disabled /></td>
				<td><input type=text  value="${user.businessLicense}" disabled /></td>
				
			</tr>
			<tr>
			
				<td><input type= hidden name= hiddenUser id='hiddenUser' value="${user.username}" /></td>
				<td><input type= hidden name= hiddenUserNumber id='hiddenUserNumber' value="${user.username}" /></td>
			</tr>
			</form:form>
		</c:forEach>
	</table>
	</c:if>
	<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" /> 
</form:form>
</body>
</html>