package databaseEncryptionModules;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PIIRequestDatabaseHandler {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;

	public void getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/test?"
						+ "user=root&password=Incredibles9");
	}

	public void addNewEntry( String username, String requesttype, String requestdetails, 
			String authorizedto, String status) throws Exception {
		
		EncDecModule encrypter = new EncDecModule();

		byte[] encryptedRequesttype = encrypter.encrypt(requesttype);
		byte[] encryptedRequestdetails = encrypter.encrypt(requestdetails);
		byte[] encryptedAuthorizedto = encrypter.encrypt(authorizedto);
		byte[] encryptedStatus = encrypter.encrypt(status);

		getConnection();

		preparedStatement = connect
				.prepareStatement("insert into  software_security.tbl_pii_requests values (?, ?, ?, ?, ?)");
		preparedStatement.setBytes(1, username.getBytes());
		preparedStatement.setBytes(2, encryptedRequesttype);
		preparedStatement.setBytes(3, encryptedRequestdetails);
		preparedStatement.setBytes(4, encryptedAuthorizedto);
		preparedStatement.setBytes(5, encryptedStatus);

		preparedStatement.executeUpdate();

		close();
	}

	public PIIRequest getEntry(String username) throws Exception {		
		PIIRequest user = null;
		EncDecModule encrypter = new EncDecModule();

		getConnection();
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_pii_requests "
					+ "where tbl_pii_requests.username = ?");    
			preparedStatement.setString(1, username);    
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				int pid = resultSet.getInt("pid");
				String requesttype = new String(encrypter.decrypt(resultSet.getBytes("requesttype")));
				String requestdetails = new String(encrypter.decrypt(resultSet.getBytes("requestdetails")));
				String authorizedto = new String(encrypter.decrypt(resultSet.getBytes("authorizedto")));
				String status = new String(encrypter.decrypt(resultSet.getBytes("status")));

				user = new PIIRequest(pid, username, requesttype, requestdetails, 
						authorizedto, status);
			}
			resultSet.close();
			close();
			return user;
		} catch (Exception e) {
			throw e;
		}
	}

	// You need to close the resultSet
	public void close() {
		try {
			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}
}
