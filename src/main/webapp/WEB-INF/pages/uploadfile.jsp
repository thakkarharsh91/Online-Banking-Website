<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Spring MVC Multiple File Upload</title>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<script>
$(document).ready(function() {
    //add more file components if Add is clicked
    $('#addFile').click(function() {
        var fileIndex = $('#fileTable tr').children().length - 1;
        $('#fileTable').append(
                '<tr><td>'+
                '   <input type="file" name="files['+ fileIndex +']" />'+
                '</td></tr>');
    });
     
});
</script>
</head>
<body>
	<br>
	<br>
	<div align="center">
		<h2>Instructions:</h2>
		<h3>For Individual Customer: Upload scanned copy of Passport or
			Driver License or DMV Identification Cards</h3>
		<h3>For Merchant/Organization: Upload scanned copy of Business
			License</h3>
		<form:form method="POST" modelAttribute="uploadForm"
			enctype="multipart/form-data"
			action="${pageContext.servletContext.contextPath}/savefiles/?${_csrf.parameterName}=${_csrf.token}"
			onsubmit="return Validate(this);">

			<p>Select files to upload. inputs.</p>

			<table id="fileTable">
				<tr>
					<td><input name="files[0]" type="file" /></td>
				</tr>

			</table>
			<br />
			<input type="submit" value="Upload" />
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />

		</form:form>
		<script type="text/javascript">
        var _validFileExtensions = [".jpg", ".jpeg", ".doc", , ".docx", ".pdf", ".png"];    
function Validate(oForm) {
    var arrInputs = oForm.getElementsByTagName("input");
    for (var i = 0; i < arrInputs.length; i++) {
        var oInput = arrInputs[i];
        if (oInput.type == "file") {
            var sFileName = oInput.value;
            if (sFileName.length > 0) {
                var blnValid = false;
                for (var j = 0; j < _validFileExtensions.length; j++) {
                    var sCurExtension = _validFileExtensions[j];
                    if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
                        blnValid = true;
                        break;
                    }
                }
                
                if (!blnValid) {
                    alert("Sorry, " + sFileName + " is invalid, allowed extensions are: " + _validFileExtensions.join(", "));
                    return false;
                }
            }
        }
    }
  
    return true;
}
    </script>
		<br />
	</div>

</body>
</html>