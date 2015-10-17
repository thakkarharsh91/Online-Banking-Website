<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form:form name='loginForm'
			action="${pageContext.servletContext.contextPath}/accessRequests/?${_csrf.parameterName}=${_csrf.token}" method='POST'>
<table border="3" align ="center">

<h2 align="center"><u>Requests</u></h2>
	<tr>
		<th>Request ID</th>
		<th>Request From</th>
		<th>Request Time</th>
		<th>Request Status</th>
	</tr>
<c:forEach var="view" items="${requestApprove}" >
	<tr>
		<td><c:out value="${view.requstID}"/></td>
		<td><c:out value="${view.rqstFrom}"/></td>
		<td><c:out value="${view.rqstTime}"/></td>
		<td><c:out value="${view.rqstStatus}"/></td>
	</tr>
</c:forEach>		

</table>
<select name="Type">
                   <option value="Approve">Approve</option>
                   <option value="Reject">Reject</option>
</select>
Enter the Request Id :
<input type='text' name = 'RequestID'/>
<input name="submit" type="submit"
						value="submit" />
</form:form>	
</body>
</html>
<%
int timeout = session.getMaxInactiveInterval();
//timeout/=60;
String url = request.getRequestURL().toString();
System.out.println(timeout);
url = url.replace("/WEB-INF/pages/accessRequests.jsp", "/login");
response.setHeader("Refresh", timeout + "; URL ="+url);
%>
</html>