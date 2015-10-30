<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
	<noscript>
  <meta http-equiv="refresh" content="0; url=${pageContext.servletContext.contextPath}/logoutusers" />
  Javascript Disabled
</noscript>
	<div style="text-align: center">
		<a href="${pageContext.servletContext.contextPath}/Home">Home</a>
		<a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
	</div>
	<form:form name='loginForm'
		action="${pageContext.servletContext.contextPath}/moddeltransaction/?${_csrf.parameterName}=${_csrf.token}"
		method='POST'>
		<table border="3" align="center">

			<h2 align="center">
				<u>Transactions</u>
			</h2>
			<c:if test="${not empty select}">
				<div class="msg" align="center">${select}</div>
			</c:if>
			<c:if test="${not empty delete}">
				<div class="msg" align="center">${delete}</div>
			</c:if>
			<c:if test="${not empty modify}">
				<div class="msg" align="center">${modify}</div>
			</c:if>
			<c:if test="${not empty zeroerror}">
				<div class="msg" align="center">${zeroerror}</div>
			</c:if>
			<c:if test="${not empty multiplemodify}">
				<div class="msg" align="center">${multiplemodify}</div>
			</c:if>
			<c:if test="${not empty destinationerror}">
				<div class="msg" align="center">${destinationerror}</div>
			</c:if>
			<c:if test="${not empty greatervalue}">
				<div class="msg" align="center">${greatervalue}</div>
			</c:if>
			<c:if test="${not empty success}">
				<div class="msg" align="center">${success}</div>
			</c:if>
			<tr>
				<th>Select</th>
				<th>Username</th>
				<th>Transaction ID</th>
				<th>Transaction amount</th>
				<th>Modified Amount</th>
				<th>Source account number</th>
				<th>Destination account number</th>
				<th>date and time</th>
				<th>transfer type</th>
				<th>status</th>
			</tr>
			<c:forEach var="view" items="${transactionApprove}">
				<tr>
					<td><input type="checkbox" value="${view.transactionId}"
						name="check"></td>
					<td><c:out value="${view.userName}" /></td>
					<td><c:out value="${view.transactionId}" /></td>
					<td><c:out value="${view.transactionAmount}" /></td>
					<td><input type="text" name="${view.transactionId}" value="${view.newAmount}" /></td>
					<td><c:out value="${view.sourceAccount}" /></td>
					<td><c:out value="${view.destAccount}" /></td>
					<td><c:out value="${view.dateandTime}" /></td>
					<td><c:out value="${view.transferType}" /></td>
					<td><c:out value="${view.status}" /></td>
				</tr>
			</c:forEach>

		</table>
		</br>
		</br>
		<div style="text-align: center">
			<select name="Type">
				<option value="Modify">Modify</option>
				<option value="Delete">Delete</option>
			</select> <br></br> <br></br> <input name="submit" type="submit"
				value="submit" />
		</div>
	</form:form>
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	//timeout/=60;
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/moddeltransaction.jsp", "/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1);
%>