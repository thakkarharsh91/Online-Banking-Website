<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
				<div class="span4">
					<h2>Explore our products</h2>
					<p>
						<strong>Find the products that fits your individual and
							family goals </strong>
					</p>
					<p class="text-center">
						<a class="btn btn-primary" href="./findallproducts">Find all products</a>
					</p>
				</div>

				<div class="span4">
					<h2>Start banking with Us</h2>
					<p>
						<strong>You could apply for checking, savings accounts
							and credit cards</strong>
					</p>
					<p class="text-center">
						<a class="btn btn-primary" href="./startbanking">Apply Online</a>
					</p>
				</div>

				<div class="span4">
					<h2>Existing Users</h2>
					<p>
						<strong>Login to access your accounts and access the
							transactions and requests</strong>
					</p>
					<p class="text-center">
						<a class="btn btn-primary" href="./login">Sign in</a>
					</p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<%
	int timeout = session.getMaxInactiveInterval();
	String url = request.getRequestURL().toString();
	url = url.replace("/WEB-INF/pages/index.jsp",
			"/logoutusers");
	response.setHeader("Refresh", "300; URL =" + url);
%>