package databseHandler;

import handlers.individualuserHandlers.UserAccounts;
import handlers.individualuserHandlers.UserRecepients;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import authentication.ModifyUser;

public class MySQLAccess {
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

	public ArrayList<UserAccounts> readDataBase(String username) throws Exception {
		try {						
			// Statements allow to issue SQL queries to the database
			preparedStatement = connect.prepareStatement("select * from software_security.tbl_user_account where username=?");
			// Result set get the result of the SQL query
			preparedStatement.setString(1, username);

			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<UserAccounts> users = writeResultSet(resultSet);
			resultSet.close();
			return users;

		} catch (Exception e) {
			throw e;
		}
	}

	public ArrayList<ModifyUser> readUserDetails(String userName,String parameterType) throws Exception {
		try { 

			if (parameterType.equals("UserName")){
				/*preparedStatement = connect
						.prepareStatement("update software_security.tbl_user_details set tbl_user_details.firstname = ? where tbl_user_details.username = ?");
				preparedStatement.setString(1, "Kartik123456");
				preparedStatement.setString(2, userName);
				int count=preparedStatement.executeUpdate();	*/
				preparedStatement = connect.prepareStatement("SELECT username,firstname,lastname,email,address,state,zip,dateofbirth,phonenumber,passportnumber,businesslicense FROM software_security.tbl_user_details where (tbl_user_details.usertype='USER' or tbl_user_details.usertype='MERCHANT')  and tbl_user_details.username = ?");    
				preparedStatement.setString(1, userName);    
				ResultSet resultSet = preparedStatement.executeQuery();
				ArrayList<ModifyUser> users = writeResultSetModify (resultSet);
				return users;
			}
			else if (parameterType.equals("Name")){
				preparedStatement = connect.prepareStatement("SELECT username,firstname,lastname,email,address,state,zip,dateofbirth,phonenumber,passportnumber,businesslicense FROM software_security.tbl_user_details where (tbl_user_details.usertype='USER' or tbl_user_details.usertype='MERCHANT')   and tbl_user_details.firstname = ?");    
				preparedStatement.setString(1, userName);    
				ResultSet resultSet = preparedStatement.executeQuery();
				ArrayList<ModifyUser> users = writeResultSetModify (resultSet);
				return users;
			}
			else
			{
				preparedStatement = connect.prepareStatement("SELECT tbl_user_details.username,firstname,lastname,email,address,state,zip,dateofbirth,phonenumber,passportnumber,businesslicense FROM software_security.tbl_user_details JOIN "
						+ "software_security.tbl_user_account ON tbl_user_details.username=tbl_user_account.username "
						+ "where ( tbl_user_details.usertype='USER' or tbl_user_details.usertype='MERCHANT')   and tbl_user_account.accountnumber = ?");    
				preparedStatement.setString(1, userName); 
				ResultSet resultSet = preparedStatement.executeQuery();
				ArrayList<ModifyUser> users = writeResultSetModify (resultSet);
				return users;

			}

		} catch (Exception e) {
			throw e;
		}
	}

	public ResultSet readLoginDataBase(String userName) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_user_details JOIN "
					+ "software_security.tbl_user_authentication ON tbl_user_details.username=tbl_user_authentication.username "
					+ "where tbl_user_authentication.username = ?");    
			preparedStatement.setString(1, userName);    
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;

		} catch (Exception e) {
			throw e;
		}
	}

	public ResultSet readAccount(String userName) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_user_details JOIN "
					+ "software_security.tbl_user_account ON tbl_user_details.username=tbl_user_account.username "
					+ "where tbl_user_account.username = ?");    
			preparedStatement.setString(1, userName);    
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;

		} catch (Exception e) {
			throw e;
		}
	}
	
	public ResultSet readAdmin(String roleName) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_user_details where usertype=?");    
			preparedStatement.setString(1, roleName);    
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;

		} catch (Exception e) {
			throw e;
		}
	}

	public void updateAllowDataBase(String userNameTo, String userNameFrom, String requestType ) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("INSERT INTO software_security.tbl_transaction_requests (requestto, requestfrom, requesttype)"
					+ "VALUES (?, ?, ?)"); 
			//INSERT INTO `software_security`.`tbl_transaction_requests` (`requestto`, `requestfrom`, `requesttype`) VALUES ('arpit', 'arpit', 'View');
			preparedStatement.setString(1, userNameTo); 
			preparedStatement.setString(2, userNameFrom);
			preparedStatement.setString(3, requestType);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			throw e;
		}
	}
	public ResultSet getRequestInfo() throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_transaction_requests");
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;

		} catch (Exception e) {
			throw e;
		}
	}

	/*public ResultSet authRequest(String User) throws Exception {
		try {						
						preparedStatement = connect.prepareStatement("SELECT requestid,requestfrom,requestdate,requeststatus FROM software_security.tbl_transaction_requests JOIN " 
								+"software_security.tbl_user_details ON tbl_transaction_requests.requestto=tbl_user_details.username where tbl_transaction_requests.requestto = ?");
						preparedStatement.setString(1, User);
						ResultSet resultSet = preparedStatement.executeQuery();
						return resultSet;

		} catch (Exception e) {
			throw e;
		}
	}*/

	public ResultSet validateUserInfo(String User) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT Usertype FROM software_security.tbl_user_details "
					+"where username= ? ");
			preparedStatement.setString(1, User); 
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;

		} catch (Exception e) {
			throw e;
		}
	}

	public ResultSet updateRequestInfo(String User) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("UPDATE software_security.tbl_transaction_requests SET requeststatus='Approved' WHERE `requestid`='6';");
			preparedStatement.setString(1, User); 
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;

		} catch (Exception e) {
			throw e;
		}
	}

	private ArrayList<UserAccounts> writeResultSet(ResultSet resultSet) throws SQLException, NoSuchAlgorithmException {
		// ResultSet is initially before the first data set

		ArrayList<UserAccounts> users = new ArrayList<UserAccounts>();
		while (resultSet.next()) {
			UserAccounts temp = new UserAccounts();
			temp.setUsername(resultSet.getString("username"));
			temp.setAccountnumber(resultSet.getString("accountnumber"));
			temp.setAccounttype(resultSet.getString("accounttype"));
			temp.setBalance(resultSet.getString("balance"));
			users.add(temp);
		}
		return users;
	}
	private ArrayList<UserRecepients> writeRecResultSet(ResultSet resultSet) throws SQLException, NoSuchAlgorithmException {
		// ResultSet is initially before the first data set
		ArrayList<UserRecepients> users = new ArrayList<UserRecepients>();
		while (resultSet.next()) {
			UserRecepients temp = new UserRecepients();
			temp.setFirstname(resultSet.getString("firstname"));
			temp.setLastname(resultSet.getString("lastname"));
			temp.setAccountnumber(resultSet.getString("accountnumber"));
			temp.setEmail(resultSet.getString("email"));
			users.add(temp);
		}
		return users;
	}

	private ArrayList<ModifyUser> writeResultSetModify(ResultSet resultSet) throws SQLException, NoSuchAlgorithmException {
		// ResultSet is initially before the first data set
		ArrayList<ModifyUser> users = new ArrayList<ModifyUser>();

		while (resultSet.next()) {

			byte[] user = resultSet.getBytes("username");
			byte[] email = resultSet.getBytes("email");
			byte[] firstname = resultSet.getBytes("firstname");
			byte[] lastname = resultSet.getBytes("lastname");
			byte[] address = resultSet.getBytes("address");
			byte[] zip = resultSet.getBytes("zip");
			byte[] phonenumber = resultSet.getBytes("phonenumber");
			byte[] state = resultSet.getBytes("state");
			byte[] passport=resultSet.getBytes("passportnumber");
			byte[] businessLicense=resultSet.getBytes("businesslicense");
			byte[] dateofbirth=resultSet.getBytes("dateofbirth");


			users.add(new ModifyUser(new String(user),new String(email), 
					new String(firstname), new String(lastname),new String(address),new String(dateofbirth), 
					new String(phonenumber), new String(state),new String(zip),new String(passport), 
					new String(businessLicense)));
		}

		return users;
	}

	public void updateUserDetails(String[] parameters) throws SQLException{
		// Remove again the insert comment

		preparedStatement = connect
				.prepareStatement("update software_security.tbl_user_details set tbl_user_details.firstname = ?,tbl_user_details.lastname = ?,tbl_user_details.email = ?,tbl_user_details.phonenumber = ?,tbl_user_details.passportnumber = ?,tbl_user_details.address = ?,tbl_user_details.state = ?,tbl_user_details.zip = ?,tbl_user_details.businesslicense = ?,tbl_user_details.dateofbirth = ? where tbl_user_details.username = ?");
		for(int i=1;i<11;i++){
			preparedStatement.setString(i, parameters[i]);
			System.out.println(parameters[i]);
		}
		System.out.println("hete updating db" +parameters[10]);
		preparedStatement.setString(11, parameters[0].trim());
		System.out.println(preparedStatement);
		int count=preparedStatement.executeUpdate();
		System.out.println(count);
	}


	public void deleteFromDatabase(String user) throws SQLException{
		// Remove again the insert comment
		preparedStatement = connect
				.prepareStatement("delete from test.user_auth where username= ? ; ");
		preparedStatement.setString(1, user);
		preparedStatement.executeUpdate();
	}

	public void addRecepienttodb(String username, String firstname,String lastname,String accountnumber,String email) throws SQLException,NoSuchAlgorithmException{
		preparedStatement = connect.prepareStatement("insert into software_security.tbl_user_recepients values (?,?,?,?,?)");

		preparedStatement.setBytes(1, username.getBytes());
		preparedStatement.setBytes(2,firstname.getBytes());
		preparedStatement.setBytes(3, lastname.getBytes());
		preparedStatement.setBytes(4,accountnumber.getBytes());
		preparedStatement.setBytes(5, email.getBytes());
		preparedStatement.executeUpdate();
	}

	public void deleteUserDetails(String user) throws SQLException{
		// Remove again the insert comment
		preparedStatement = connect
				.prepareStatement("DELETE FROM software_security.tbl_user_details where username= ? ; ");
		preparedStatement.setString(1, user);
		preparedStatement.executeUpdate();
		preparedStatement = connect
				.prepareStatement("DELETE FROM software_security.tbl_user_account where username= ? ; ");
		preparedStatement.setString(1, user);
		preparedStatement.executeUpdate();
		preparedStatement = connect
				.prepareStatement("DELETE FROM software_security.tbl_user_authentication where username= ? ; ");
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

	public void updateFlag(String userName,int status) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("update software_security.tbl_user_authentication set isloggedin = ? where username = ?");    
			preparedStatement.setInt(1, status);
			preparedStatement.setString(2, userName);
			preparedStatement.executeUpdate();
			connect.close();
		} catch (Exception e) {
			throw e;
		}
	}

	public void updateLockFlag(String userName,int status) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("update software_security.tbl_user_authentication set islocked = ? where username = ?");    
			preparedStatement.setInt(1, status);
			preparedStatement.setString(2, userName);
			preparedStatement.executeUpdate();
			connect.close();
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

	public ResultSet getBalance(String userName) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_user_account where tbl_user_account.username = ?");    
			preparedStatement.setString(1, userName);    
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;

		} catch (Exception e) {
			throw e;
		}
	}

	public ResultSet getPendingTransactions(String userName) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_transactions where tbl_transactions.username = ? and tbl_transactions.status = ?");    
			preparedStatement.setString(1, userName); 
			preparedStatement.setString(2, "No");
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;

		} catch (Exception e) {
			throw e;
		}
	}

	public boolean insertTransactions(String userName, int random,
			String amount, String accountNum, String accountNum2, String date,
			String transactionType, String status) {
		try {
			preparedStatement = connect
					.prepareStatement("insert into  software_security.tbl_transactions values (?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setBytes(1, userName.getBytes());
			preparedStatement.setBytes(2, Integer.toString(random).getBytes());
			preparedStatement.setBytes(3, amount.getBytes());
			preparedStatement.setBytes(4, accountNum.getBytes());
			preparedStatement.setBytes(5, accountNum2.getBytes());
			preparedStatement.setBytes(6, date.getBytes());
			preparedStatement.setBytes(7, transactionType.getBytes());
			preparedStatement.setBytes(8, status.getBytes());
			preparedStatement.executeUpdate();

			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}


	}

	public boolean insertUnlockRequest(String userName, String requesttype,
			String requestfrom, String requestto,String modify,String approvalStatus,String oldValue,String newValue) {
		try {
			preparedStatement = connect
					.prepareStatement("insert into software_security.tbl_requests (username,requesttype,requestfrom,requestto,modifiedcolumn,approvalstatus,oldvalue,newvalue) values(?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, requesttype);
			preparedStatement.setString(3, requestfrom);
			preparedStatement.setString(4, requestto);
			preparedStatement.setString(5, modify);
			preparedStatement.setString(6, approvalStatus);
			preparedStatement.setString(7, oldValue);
			preparedStatement.setString(8, newValue);
			preparedStatement.executeUpdate();

			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean insertRequestChange(String userName, int random,
			String currentInfo, String newInfo, String changeColumn) {
		try {
			String type=new String("Modification");
			String status=new String("No");
			preparedStatement = connect
					.prepareStatement("insert into  software_security.tbl_requests values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setBytes(1, userName.getBytes());
			preparedStatement.setBytes(2, Integer.toString(random).getBytes());
			preparedStatement.setBytes(3, type.getBytes());
			preparedStatement.setBytes(4, currentInfo.getBytes());
			preparedStatement.setBytes(5, newInfo.getBytes());
			preparedStatement.setBytes(6, changeColumn.getBytes());
			preparedStatement.setBytes(7, status.getBytes());
			preparedStatement.setBytes(8, currentInfo.getBytes());
			preparedStatement.setBytes(9, newInfo.getBytes());
			preparedStatement.executeUpdate();

			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<UserRecepients> readRecepientDataBase(String userName) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_user_recepients where username=?");    
			preparedStatement.setString(1, userName);    
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<UserRecepients> users = writeRecResultSet(resultSet);
			return users;

		} catch (Exception e) {
			throw e;
		}
	}

	public boolean insertTransactions(String userName, String TransactionID,
			String amount, String SourceAccountnumber, String DestinationAccountnumber, String date,
			String transactionType, String status) {
		try {
			preparedStatement = connect
					.prepareStatement("insert into  software_security.tbl_transactions values (?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setBytes(1, userName.getBytes());
			preparedStatement.setBytes(2, TransactionID.getBytes());
			preparedStatement.setBytes(3, amount.getBytes());
			preparedStatement.setBytes(4, SourceAccountnumber.getBytes());
			preparedStatement.setBytes(5, DestinationAccountnumber.getBytes());
			preparedStatement.setBytes(6, date.getBytes());
			preparedStatement.setBytes(7, transactionType.getBytes());
			preparedStatement.setBytes(8, status.getBytes());
			preparedStatement.executeUpdate();

			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}


	}

	public ResultSet readUserDetails(String accountnumber) throws Exception{
		try{
			preparedStatement = connect.prepareStatement("Select * FROM software_security.tbl_user_account WHERE accountnumber=?");
			preparedStatement.setString(1, accountnumber);
			ResultSet resultset = preparedStatement.executeQuery();
			return resultset;
		}
		catch(Exception e){
			throw e;
		}
	}

	public ResultSet readRecepientDetails(String userName) throws Exception{
		try{
			preparedStatement = connect.prepareStatement("Select * FROM software_security.tbl_user_recepients WHERE username=?");
			preparedStatement.setString(1, userName);
			ResultSet resultset = preparedStatement.executeQuery();
			return resultset;
		}
		catch(Exception e){
			throw e;
		}
	}

	public ResultSet getUserName(String emailAddress) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_user_details where tbl_user_details.email = ?");    
			preparedStatement.setString(1, emailAddress);    
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;

		} catch (Exception e) {
			throw e;
		}
	}
}