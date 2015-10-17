<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<h2>Internal Funds Transfer</h2>
<div>
		<c:if test="${not empty destinationError}">
			<div class="msg">${destinationError}</div>
		</c:if>
		<c:if test="${not empty amountError}">
			<div class="msg">${amountError}</div>
		</c:if>
		<c:if test="${not empty balanceInsuficientError}">
			<div class="msg">${balanceInsuficientError}</div>
		</c:if>
		<c:if test="${not empty success}">
			<div class="msg">${success}</div>
		</c:if>
		<c:if test="${not empty failure}">
			<div class="msg">${failure}</div>
		</c:if>
	</div>
<form name="transferform"
		action="${pageContext.servletContext.contextPath}/internalTransfer"
		method='POST'>
	<table width="500" border="0">
  <tbody>
    <tr>
      <th scope="col">&nbsp;</th>
      <th scope="col">&nbsp;</th>
    </tr>
    <tr>
      <td>Select the Source Account: </td>
      <td><select name="sourceuseraccounts">
      <c:forEach var="item" items="${useraccounts}">
        <option> <c:out value="${item.accounttype}"/>
        </option>
    </c:forEach>
    
       </select></td>
    </tr>
    <tr>
      <td>Select the Destination Account: </td>
      <td><select name="destinationuseraccount">
      <c:forEach var="item" items="${useraccounts}">
        <option> <c:out value="${item.accounttype}"/>
        </option>
    </c:forEach>
    </select></td>
    </tr>
    <tr>
      <td>Enter the Amount:</td>
      <td><input id="amount" name="amount" type="text"></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td><button type="submit">Transfer</button></td>
    </tr>
  </tbody>
</table>
<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
</form>
</body>
</html>