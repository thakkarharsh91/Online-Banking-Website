<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
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
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/create_transactions.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
%>