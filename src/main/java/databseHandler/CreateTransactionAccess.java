package databseHandler;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CreateTransactionAccess {
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
	
	
	public String insertIntotransDatabase(byte[] username, byte[] transactionamount, 
			byte[] sourceaccountnumber, byte[] destinationaccountnumber, byte[] transfertype) throws SQLException, NoSuchAlgorithmException{
		// PreparedStatements can use variables and are more efficient
		int count=0;
		DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date=new Date();
		String dttime=dateFormat.format(date);
		byte[] dateandtime = dttime.getBytes();
		Random ran = new Random();
		Integer transactionid=ran.nextInt(1000) + 5;
		String stat="WAITING_BANK";
		byte[] status=stat.getBytes();
		//byte[] transactionid = new byte[0]; //change to int if database schema changes
		//byte[] dateandtime = new byte[0];   //change according to database changes
		
		try
		{
		preparedStatement = connect
				.prepareStatement("insert into  software_security.tbl_transactions values (?, ?, ?, ?, ?, ?, ?, ?)");
		// Parameters start with 1
		preparedStatement.setBytes(1, username);
		preparedStatement.setInt(2, transactionid);  //DB trigger populates this field. Change to Int when db changes
		preparedStatement.setBytes(3, transactionamount);
		preparedStatement.setBytes(4, sourceaccountnumber);
		preparedStatement.setBytes(5, destinationaccountnumber);
		preparedStatement.setBytes(6, dateandtime);   //DB trigger populates this field
		preparedStatement.setBytes(7, transfertype);
		preparedStatement.setBytes(8, status);
		count=preparedStatement.executeUpdate();
		
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		if(count>0)
			return "successfully inserted";
			else
		    return "insertion error";
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