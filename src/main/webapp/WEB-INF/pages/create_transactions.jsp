<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<body>
	<h1>${title}</h1>
	
	<h2>Status: ${success_msg}</h2>
	<a style="float:right" href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
	<body>
		<form method="POST">
			
			<label>UserName</label>
			<input type="text" name="username">
			<br/>
    		<label>Transaction Amount</label>
    		<input type="text" name="transamount" />
			<br/>			
    		<label>Source Account</label>
    		<input type="text" name="sourceacc"/>
			<br/>			
    		<label>Destination Account</label>
    		<input type="text" name="destacc"/>
			<br/><br/>
			<input type="submit" value="Create" name="submit"/>
			<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
				
				
		</form>
	</body>
	
</html>