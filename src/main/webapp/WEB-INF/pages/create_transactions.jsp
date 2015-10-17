<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h1>${title}</h1>
	<h1>${message}</h1>
	<h2>Status: ${success_msg}</h2>
	<body>
		<form method="POST">
			<h2>Create Transactions</h2><br><br>
			<label>UserName</label>
			<input type="text" name="username">
    		<label>Transaction Amount</label>
    		<input type="text" name="transamount" />
			
			
    		<label>Source Account</label>
    		<input type="text" name="sourceacc"/>
			
			
    		<label>Destination Account</label>
    		<input type="text" name="destacc"/>
			
			<label>Type</label>
    		<input type="text" name="type"/>
			<br><br>
			<input type="submit" value="submit" name="submit"/>
			<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		</form>
	</body>
</html>