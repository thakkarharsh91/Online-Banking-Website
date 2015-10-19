package gunpreetPackage.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class JdbcConnection_tbl_user_authentication 
{		
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private static final Logger LOG = Logger.getLogger(JdbcConnection_tbl_user_authentication.class);
	public Connection getConnection() throws SQLException 
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}

		conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/software_security?" +
						"user=root&password=Incredibles9"
				);
		return conn;
	}	

	public void update_tbl_user_authentication(String username, byte[] usercurrentpassword, byte[] useroldpassword) throws Exception
	{
		try
		{
			conn = this.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE tbl_user_authentication "
					+ "SET usercurrentpassword = ? ,"
					+ "useroldpassword = ? "
					+ "WHERE username  = '"+username+"'");    
			preparedStatement.setBytes(1, usercurrentpassword);
			preparedStatement.setBytes(2, useroldpassword);
			preparedStatement.executeUpdate();			
		}
		catch (Exception e) 
		{
			LOG.error("Issue while updating the password"+e.getMessage());
		}

	}

	public ResultSet get_tbl_user_authentication_Details(String username)
	{
		ResultSet resultSet = null;
		try
		{
			conn = this.getConnection();
			preparedStatement = conn.prepareStatement("SELECT "
					+ "usercurrentpassword,"
					+ "useroldpassword "
					+ "FROM tbl_user_authentication "
					+ "WHERE username= ?");
			preparedStatement.setString(1, username); 
			resultSet = preparedStatement.executeQuery();
		}
		catch (Exception e) 
		{
			LOG.error("Issue while updating the password"+e.getMessage());
		}
		return resultSet;
	}
}
