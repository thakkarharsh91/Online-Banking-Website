<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h1>${title}</h1>
	<h1>${message}</h1>
	<h2>Status: ${unlock_msg}</h2>
	<body>
		<form method="POST">
			<h2>Unlock Internal User Account</h2><br><br>
			<label>Enter username:</label>
			<input type="text" name="username">
			<input type="submit" value="submit" name="submit"/>
			<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		</form>
	</body>
</html>