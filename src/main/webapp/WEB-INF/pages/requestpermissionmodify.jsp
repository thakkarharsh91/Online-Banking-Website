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
<script type="text/javascript">
   function a() {
	   document.getElementById("hiddenUser").value = $(this).closest('tr').attr('id'); // table row ID 
       
   }
</script>

</head>
<body>
<form:form action="${pageContext.servletContext.contextPath}/reqModify/?${_csrf.parameterName}=${_csrf.token}" method='POST'>
	<select name=searchcat>
	<option value=firstname> First Name </option>
	<option value=lastname> User Name </option>
	<option value=phonenumber> Phone Number </option>
	<option value=email> Email </option>
	<option value=address> Address</option>
	<option value=state> State</option>
	<option value=zip>Zip</option>
	<option value=businesslicense>Business License</option>
	</select>
	<label> User Name </label>
	<input type= text name = username>
	<label> New Value </label>
	<input type= text name = newvalue>
	<select name=usertype>
	<option value=EMPLOYEE> Employee </option>
	<option value="System Admin"> System Admin </option>
	<option value="System Manager">System Manager </option>
    <input type= submit name=submit value="Request Permission"> 
    <br>
 
	</form:form>
	
	<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" /> 

</body>
</html>