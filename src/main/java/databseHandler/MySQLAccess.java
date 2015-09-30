package databseHandler;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import authentication.User;

public class MySQLAccess {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	
	public void getConnection() throws ClassNotFoundException, SQLException{
		// This will load the MySQL driver, each DB has its own driver
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/test?"
						+ "user=root&password=Incredibles9");
	}
	
	public ArrayList<User> readDataBase() throws Exception {
		try {						
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			ResultSet resultSet = statement
					.executeQuery("select * from test.user_auth");
			ArrayList<User> users = writeResultSet(resultSet);
			resultSet.close();
			return users;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	private ArrayList<User> writeResultSet(ResultSet resultSet) throws SQLException, NoSuchAlgorithmException {
		// ResultSet is initially before the first data set
		ArrayList<User> users = new ArrayList<User>();
		
		while (resultSet.next()) {

			byte[] user = resultSet.getBytes("username");
			byte[] password = resultSet.getBytes("password");
			byte[] email = resultSet.getBytes("email");
			byte[] firstname = resultSet.getBytes("firstname");
			byte[] lastname = resultSet.getBytes("lastname");

			users.add(new User(new String(user), new String(password), new String(email), 
					new String(firstname), new String(lastname)));
		}
		
		return users;
	}
	
	public void deleteFromDatabase(String user) throws SQLException{
		// Remove again the insert comment
		preparedStatement = connect
				.prepareStatement("delete from test.user_auth where username= ? ; ");
		preparedStatement.setString(1, user);
		preparedStatement.executeUpdate();
	}
	
	public void insertIntoDatabase(byte[] user, byte[] password, 
			byte[] email, byte[] firstname, byte[] lastname) throws SQLException, NoSuchAlgorithmException{
		// PreparedStatements can use variables and are more efficient
		
		preparedStatement = connect
				.prepareStatement("insert into  test.user_auth values (?, ?, ?, ?, ?)");
		// Parameters start with 1
		preparedStatement.setBytes(1, user);
		preparedStatement.setBytes(2, password);
		preparedStatement.setBytes(3, email);
		preparedStatement.setBytes(4, firstname);
		preparedStatement.setBytes(5, lastname);
		
		preparedStatement.executeUpdate();

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