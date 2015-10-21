<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Request for PII</title>
</head>
<body>
	<div>
		<c:if test="${not empty emptyFields}">
			<div class="msg">${emptyFields}</div>
		</c:if>
		<c:if test="${not empty ExsistingUser}">
			<div class="msg">${ExsistingUser}</div>
		</c:if>
		<c:if test="${not empty Successful}">
			<div class="msg">${Successful}</div>
		</c:if>
	</div>
	<h2 align="justify">Request for PII</h2>
	<form mothod='POST'">
		<table width="400" border="0">
			<tbody>
				<tr>
					<th scope="col">&nbsp;</th>
					<th scope="col">&nbsp;</th>
				</tr>
				<tr>
					<td>Please request type:</td>
					<td><select id="requesttype" name="requesttype">
							<option value="SSN">SSN</option>
							<option value="PassportNo">PassportNo</option>
							<option value="AccountNo">AccountNo</option>
					</select></td>
				</tr>
				<tr>
					<td>Enter Details:</td>
					<td><input id="requestdetails" name="requestdetails"
						type="text"></td>
				</tr>
				<tr>
					<td>Provide Authorization to:</td>
					<td><input id="authorizeto" name="authorizeto"
						type="text"></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><font size='4' color="white"></font><input type="submit" name="submit" value="Submit" class="submit_btn"></td>
				</tr>
			</tbody>
		</table>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>

</body>
</html>