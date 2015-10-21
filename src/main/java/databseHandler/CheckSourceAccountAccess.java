package databseHandler;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.bind.DatatypeConverter;

import authentication.UserAccount;

public class CheckSourceAccountAccess {
	private Connection connect = null;
	private Statement statement = null;
	
	public void getConnection() throws ClassNotFoundException, SQLException{
		// This will load the MySQL driver, each DB has its own driver
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/software_security?"
						+ "user=root&password=Incredibles9");
	}
	
	
	public boolean readDataBaseAccount(String username,String fromaccnumber) throws Exception {
		try {
			byte[] uname=username.getBytes();
			String username_hex= DatatypeConverter.printHexBinary(uname);
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			ResultSet resultSet2 = statement
					.executeQuery("select * from software_security.tbl_user_account WHERE username=0x"+username_hex);
			UserAccount useracc = writeResultSetAccount(resultSet2);
			resultSet2.close();
			if(useracc!=null && useracc.getAccountnumber().equals(fromaccnumber))
			   return true;
			else
				return false;
					   
		} catch (Exception e) {
			throw e;
		}
	}
	
	private UserAccount writeResultSetAccount(ResultSet resultSet) throws SQLException, NoSuchAlgorithmException {
		// ResultSet is initially before the first data set
		UserAccount useracc = null;
		
		while (resultSet.next()) {			

				byte[] username = resultSet.getBytes("username");
				byte[] accountnumber = resultSet.getBytes("accountnumber");
				byte[] accounttype = resultSet.getBytes("accounttype");
				byte[] balance = resultSet.getBytes("balance");				

				useracc=new UserAccount(new String(username), new String(accountnumber), new String(accounttype), 
						new String(balance));			
		}
		
		return useracc;
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
	
	