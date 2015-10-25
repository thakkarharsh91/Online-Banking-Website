package databseHandler;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PasswordChangeAccess {
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


	public void UpdatePass(String username) throws SQLException, NoSuchAlgorithmException{

		try
		{
			preparedStatement = connect
					.prepareStatement("update software_security.tbl_user_authentication set ispasswordchange=? where username=?");
			preparedStatement.setString(1, "0");
			preparedStatement.setString(2, username);
			preparedStatement.executeUpdate();	
		}
		catch(SQLException e)
		{
			e.printStackTrace();
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
