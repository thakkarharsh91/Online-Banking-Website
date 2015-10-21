<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
 <div>
 <c:if test="${not empty norequests}">
			<div class="msg">${norequests}</div>
		</c:if>
</div>
<div>
<c:if test="${requests != null && requests.size() != 0}">
	<form name="form"
		action="${pageContext.servletContext.contextPath}/VerifyExternalUser"
		method='POST'>
		<div>
			<table border="1">
				<th>RequestID</th>
				<th>RequestType</th>
				<th>ModifiedColumn</th>
				<th>SSN/OldValue</th>
				<th>AccountType/NewValue</th>
				<th>Decision</th>


				<c:forEach items="${requests}" var="request" varStatus="myIndex">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />

					<tr>
						<td><c:out value="${request.requestid}" />
						<td><c:out value="${request.requesttype}" />
						<td><c:out value="${request.modifiedcolumn}"/>
						<td><c:out value="${request.oldvalue}"/>
						<td><c:out value="${request.newvalue}"/>
						<td>
							<div>
								<button name="approve" type="submit" value="${request.requestid}"
									style="margin-left: 50px; float: left;">approve</button>
								<button name="decline" type="submit"  value="${request.requestid}"
									style="margin-left: 50px; float: left;">decline</button>
							</div>
						</td>
					</tr>
                 
				</c:forEach>


			</table>
		</div>
	</form>
		</c:if>
	
	<!--<div style="text-align:center">
	<h3 style="color:red">Verify Request</h3>
</div>
<div style="width:300px;margin-left:auto;margin-right:auto" >
	<div style="margin-bottom:20px">
    <label style="width:50%;float:left">
    	Request ID
     </label>
    <input type="text" id="request_id" style="width:50%"/>
	</div>
    <div style="margin-bottom:20px">
    <label style="width:50%;float:left">
    	Request Type
     </label>
    <input type="text" id="request_type" style="width:50%"/>
	</div>
    <div style="margin-bottom:20px">
       <label style="width:50%;float:left">
    	Description
       </label>
    <input type="text" id="description" style="width:50%"/>
	</div>
    <div>
    <button style="margin-left:50px;float:left;">
    approve
    </button>
    <button style="margin-left:50px;float:left;">
    decline
    </button>
	</div>
</div>-->
</body>
</html>