<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!doctype html>
<html lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>Sun Devils Bank Home Page</title>
<link rel="shortcut icon"
	href="http://teamtreehouse.com/assets/favicon.ico">
<link rel="icon" href="http://teamtreehouse.com/assets/favicon.ico">
<link rel="stylesheet" type="text/css" media="all"
	href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" media="all"
	href="css/bootstrap-responsive.min.css">
<link rel="stylesheet" type="text/css" media="all" href="css/global.css">
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" language="javascript" charset="utf-8"
	src="js/bootstrap.min.js"></script>
</head>

<body>
	<form name="form"
		action="${pageContext.servletContext.contextPath}/customerquery?${_csrf.parameterName}=${_csrf.token}"
		method='POST'>
		<nav id="navigation">
			<div class="container">
				<ul class="navlinks">
					<li><a href="./">Home</a></li>
					<li><a href="./aboutus">About Us</a></li>
					<li><a href="./projects">Projects</a></li>
					<li><a href="./team">The Team</a></li>
					<li><a href="./contact">Contact Us</a></li>
				</ul>
			</div>
		</nav>

		<header id="heading">
			<div class="container text-center">
				<h1>Sun Devils Bank</h1>
				<h4>Secure Banking Website by Group#1</h4>


			</div>
		</header>

		<div id="main-content">
			<div class="container">
				<div class="row">
					<div class="span10">
						<h2>Contact Us</h2>
						<p>
							<strong>Please enter your query and your email address
								in the below area and we will get back to you soon.</strong>
						</p>
						<p>
						<strong><c:if test="${not empty mandatory}">
							<div>${mandatory}</div>
						</c:if></strong>
						<strong><c:if test="${not empty success}">
							<div>${success}</div>
						</c:if></strong>
						</p>
						<p>
							Enter query <input name="query" type="text" />
						</p>
						<p>
							Enter email address <input name="emailaddress" type="email" />
						</p>
						<p>
							<input class="btn btn-primary" name="submit" type="submit"
								value="Submit" />
						</p>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/contact.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
%>