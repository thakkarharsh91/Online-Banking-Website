package databseHandler;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.apache.log4j.Logger;

import utilities.TimeUtility;

public class CreateTransactionAccess {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private static final Logger LOG = Logger.getLogger(CreateTransactionAccess.class);

	public void getConnection() throws ClassNotFoundException, SQLException{
		// This will load the MySQL driver, each DB has its own driver
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/software_security?"
						+ "user=root&password=Incredibles9");
	}
	
	
	public String insertIntotransDatabase(byte[] username, byte[] transactionamount, 
			byte[] sourceaccountnumber, byte[] destinationaccountnumber) throws SQLException, NoSuchAlgorithmException{
		
		int count=0;
		String dttime=TimeUtility.generateDateMethod();
		byte[] dateandtime = dttime.getBytes();
		Random ran = new Random();
		Integer transactionid=ran.nextInt(1000) + 5;
		
		
		String stat="pendingapproval";
		byte[] status=stat.getBytes();
		byte[] transfertype="fundstransfer".getBytes();
		
		
		try
		{
		preparedStatement = connect
				.prepareStatement("insert into  software_security.tbl_transactions values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		// Parameters start with 1
		preparedStatement.setBytes(1, username);
		preparedStatement.setInt(2, transactionid);  //DB trigger populates this field. Change to Int when db changes
		preparedStatement.setBytes(3, transactionamount);
		preparedStatement.setBytes(4, transactionamount);
		preparedStatement.setBytes(5, sourceaccountnumber);
		preparedStatement.setBytes(6, destinationaccountnumber);
		preparedStatement.setBytes(7, dateandtime);   //DB trigger populates this field
		preparedStatement.setBytes(8, transfertype);
		preparedStatement.setBytes(9, status);
		count=preparedStatement.executeUpdate();
		
		}
		catch(SQLException e)
		{
			LOG.error("Insertion exception" +e.getMessage());
			e.printStackTrace();
		}
		
		if(count>0)
		{
			LOG.info("Transaction successfully created for" +username.toString()+ "amount:"+transactionamount.toString());
			return "Transaction successfully created";
		}
			else
			{
				LOG.error("Transaction could not be created");
		    return "insertion error";
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