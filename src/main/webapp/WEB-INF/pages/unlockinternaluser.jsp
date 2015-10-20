<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h1>${title}</h1>
	<h1>${message}</h1>
	<h2>Status: ${unlock_msg}</h2>
	<body>
	<c:if test="${request_results != null && request_results.size() != 0}">
	<table>
		<th>UserName</th>
        <th>RequestID</th>
		<c:forEach items="${request_results}" var="req">
			<tr>
				<td><c:out value="${req.username}" /></td>
				<td><c:out value="${req.requestid}"></c:out>
				<td><form method="POST">
				<input type="hidden" name="username" value="${req.username }"/>
				<input type="submit" value="Unlock" name="submit"/>
				<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
				</form></td>
			</tr>
		</c:forEach>
	</table>
	</c:if>
		<form method="POST">
			<input type="submit" value="Refresh" name="refresh"/>
			<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		</form>
	</body>
</html>