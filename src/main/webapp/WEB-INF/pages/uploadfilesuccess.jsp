<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Refresh" content="5;url=login">
<title>Application successfully submitted</title>
<style type="text/css">
</style>
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
    <br>
    <br>
    <div align="center">
 
        <h1>Application submitted successfully</h1>
        <p>Following files are uploaded successfully.</p>
        <ol>
            <c:forEach items="${files}" var="file">
           - ${file} <br>
            </c:forEach>
        </ol>
        
        <br />
    </div>
    <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
</body>
</html>
<%
    int timeout = session.getMaxInactiveInterval();
    String url = request.getRequestURL().toString();
    url = url.replace("/WEB-INF/pages/uploadfilesuccess.jsp",
            "/logoutusers");
    response.setHeader("Refresh", "300; URL =" + url);
%>