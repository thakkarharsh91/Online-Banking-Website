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
	<div style="text-align: center">
		<a href="${pageContext.servletContext.contextPath}/Home">Home</a>
	</div>
	<div style="text-align: center">
		<a href="${pageContext.servletContext.contextPath}/logoutusers">Logout</a>
	</div>
	
		<form method="POST">
		<h2 align="center">
				<u>Transactions</u>
			</h2>
			<c:if test="${not empty success_msg}">
				<div class="msg" align="center">${success_msg}</div>
			</c:if><br/>
		<table align="center">
			<tr><td><label>UserName</label></td>
			<td><input type="text" name="username"></td>
			</tr>
    		<tr><td><label>Transaction Amount</label></td>
    		<td><input type="text" name="transamount" />
			</td></tr>		
    		<tr><td><label>Source Account</label></td>
    		<td><input type="text" name="sourceacc"/>
			</td></tr>	
    		<tr><td><label>Destination Account</label></td>
    		<td><input type="text" name="destacc"/>
			</td></tr>
			<tr><td></td><td><input type="submit" value="Create" name="submit"/>
			<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /></td></tr>
		</table>
				
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