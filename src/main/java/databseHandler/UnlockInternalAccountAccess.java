package databseHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.bind.DatatypeConverter;

public class UnlockInternalAccountAccess {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	
	public void getConnection() throws ClassNotFoundException, SQLException{
		// This will load the MySQL driver, each DB has its own driver
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/software_security?"
						+ "user=root&password=Incredibles9");
	}
	
	public String UpdateDataBaseUserAuthentication(String username) throws Exception {
		try {						
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			
			byte[] unlock="N".getBytes();
			String unlock_hex=DatatypeConverter.printHexBinary(unlock);
			byte[] uname=username.getBytes();
			String username_hex= DatatypeConverter.printHexBinary(uname);
			
			int count = statement
					.executeUpdate("UPDATE software_security.tbl_user_authentication SET Islocked=0x"+unlock_hex+" WHERE username=0x"+username_hex);
			System.out.println("unlocking account");
			//add other update statements after integrating locking mechanism.
			if(count>0)
			return "unlocked "+username;
			else
				return "error unlocking, username doesn't exist";
			
		} catch (Exception e) {
			throw e;
		}
	}
	
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