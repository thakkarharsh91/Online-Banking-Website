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
        <h1>Upload the files like scanned copy of SSN, Passport and Business License</h1>
 
        <form:form method="POST"
            modelAttribute="uploadForm" enctype="multipart/form-data" action="savefiles/?${_csrf.parameterName}=${_csrf.token}">
 
            <p>Select files to upload. Press Add button to add more file
                inputs.</p>
 
            <table id="fileTable">
                <tr>
                    <td><input name="files[0]" type="file" /></td>
                </tr>
                <tr>
                    <td><input name="files[1]" type="file" /></td>
                </tr>
                <tr>
                    <td><input name="files[2]" type="file" /></td>
                </tr>
            </table>
            <br />
            <input type="submit" value="Upload" />
                <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
            
        </form:form>
 
        <br />
    </div>

</body>
</html>