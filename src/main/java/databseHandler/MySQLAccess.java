package databseHandler;

import handlers.individualuserHandlers.UserAccounts;
import handlers.individualuserHandlers.UserRecepients;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import utilities.MyStringRandomGen;
import utilities.SendEmail;
import authentication.ModifyUser;
import authentication.Request;
import authentication.Requests;
import authentication.User;

public class MySQLAccess {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private PreparedStatement preparedStatement1 = null;
	private PreparedStatement preparedStatement2 = null;
	private PreparedStatement preparedStatement3 = null;
	private PreparedStatement preparedStatement4 = null;
	private PreparedStatement preparedStatement5 = null;
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

	public ArrayList<Request> viewRequestDatabase() throws Exception {
		try { 


			/*preparedStatement = connect
						.prepareStatement("update software_security.tbl_user_details set tbl_user_details.firstname = ? where tbl_user_details.username = ?");
				preparedStatement.setString(1, "Kartik123456");
				preparedStatement.setString(2, userName);
				int count=preparedStatement.executeUpdate();	*/
			preparedStatement = connect.prepareStatement("SELECT requestfrom,modifiedcolumn,oldvalue,newvalue from software_security.tbl_requests where software_security.tbl_requests.approvalstatus=?");    
			preparedStatement.setString(1, "pending");
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<Request> users = writeResultSetRequests (resultSet);
			return users;



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

	public void updateAllowDataBase(String userNameFor, String userNameFrom, String userNameTo, String requestType ) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("INSERT INTO software_security.tbl_transaction_requests (requestfor, requestfrom, requesttype, requestto)"
					+ "VALUES (?, ?, ?,?)"); 
			//INSERT INTO `software_security`.`tbl_transaction_requests` (`requestto`, `requestfrom`, `requesttype`) VALUES ('arpit', 'arpit', 'View');
			preparedStatement.setString(1, userNameFor); 
			preparedStatement.setString(2, userNameFrom);
			preparedStatement.setString(3, requestType);
			preparedStatement.setString(4, userNameTo);
			preparedStatement.executeUpdate();

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

	public ResultSet checkRequest(String userName,String type, String status) throws SQLException{
		try{
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_requests where username=? AND requesttype=? AND approvalstatus = ?");
			preparedStatement.setString(1,userName);
			preparedStatement.setString(2,type);
			preparedStatement.setString(3,status);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;
		}
		catch(Exception e){
			throw e;
		}

	}

	public ArrayList<Requests> getRequests(String requestto) throws SQLException{
		try{
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_requests where requestto=? AND approvalstatus='PENDING'");
			preparedStatement.setString(1,requestto);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<Requests> requests = changeToArrayList(resultSet);
			return requests;
		}
		catch(Exception e){
			throw e;
		}
	}

	public void updaterequest(String requestid,String action) throws SQLException{
		try{
			preparedStatement = connect.prepareStatement("update software_security.tbl_requests set approvalstatus = ? where requestid = ?");
			preparedStatement.setBytes(1, action.getBytes());

			preparedStatement.setBytes(2, requestid.getBytes());
			preparedStatement.executeUpdate();
		}
		catch(Exception e){
			throw e;
		}
	}

	//updatepi of user//new
	public void updateuserinfo(String username,String modifiedcolumn,String oldvalue,String newvalue) throws SQLException{
		preparedStatement = connect
				.prepareStatement("update software_security.tbl_user_details set " + modifiedcolumn +" = ? where username = ?");
		preparedStatement.setBytes(1, newvalue.getBytes());
		preparedStatement.setBytes(2, username.getBytes());
		preparedStatement.executeUpdate();
	}

	//Change to arraylist //new
	private ArrayList<Requests> changeToArrayList(ResultSet resultSet) throws SQLException{
		ArrayList<Requests> requests = new ArrayList<Requests>();
		while (resultSet.next()) {
			Requests temp = new Requests();
			temp.setUsername(resultSet.getString("username"));
			temp.setRequestid(resultSet.getString("requestid"));
			temp.setRequesttype(resultSet.getString("requesttype"));
			temp.setRequestfrom(resultSet.getString("requestfrom"));
			temp.setRequestto(resultSet.getString("requestto"));
			temp.setModifiedcolumn(resultSet.getString("modifiedcolumn"));
			temp.setApprovalstatus(resultSet.getString("approvalstatus"));
			temp.setOldvalue(resultSet.getString("oldvalue"));
			temp.setNewvalue(resultSet.getString("newvalue"));
			requests.add(temp);
		}
		return requests;
	}

	public ResultSet readUserDetails1(String accountnumber) throws Exception{
		try{
			preparedStatement = connect.prepareStatement("Select * FROM software_security.tbl_user_details WHERE tbl_user_details.accountnumber=? ");
			preparedStatement.setString(1, accountnumber);
			ResultSet resultset = preparedStatement.executeQuery();
			return resultset;
		}
		catch(Exception e){
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

	public ResultSet getRequestInfo(String fromUser) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_transaction_requests where requestfrom=?");
			preparedStatement.setString(1, fromUser);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;

		} catch (Exception e) {
			throw e;
		}
	}
	public void deleteRequest(String[] deleteRequests) throws Exception {
		try {			
			for(int i=0; i<deleteRequests.length; i++)
			{
				preparedStatement = connect.prepareStatement("DELETE FROM software_security.tbl_transaction_requests WHERE requestid=?");
				preparedStatement.setString(1, deleteRequests[i]);
				preparedStatement.executeUpdate();
			}

		} catch (Exception e) {
			throw e;
		}
	}
	public ResultSet getRequestStatusInfo(String forUser,String fromUser) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_transaction_requests where requestfor=? and requestfrom=?");
			preparedStatement.setString(1, forUser);
			preparedStatement.setString(2, fromUser);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;

		} catch (Exception e) {
			throw e;
		}
	}
	public ResultSet getTransaction(String User) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_transactions where username=?");
			preparedStatement.setString(1, User);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;

		} catch (Exception e) {
			throw e;
		}
	}

	public ResultSet authRequest(String User) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT requestid,requestfrom,requestdate,requeststatus, requestfor FROM software_security.tbl_transaction_requests JOIN " 
					+"software_security.tbl_user_details ON tbl_transaction_requests.requestto=tbl_user_details.username and tbl_transaction_requests.requestfor=tbl_user_details.username where tbl_transaction_requests.requestto = ? AND tbl_transaction_requests.requeststatus='Pending'");
			preparedStatement.setString(1, User);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;

		} catch (Exception e) {
			throw e;
		}
	}

	public ResultSet authTransaction(String status,int critical,String payment) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_transactions where tbl_transactions.status=? and tbl_transactions.newamount >= ? and tbl_transactions.transfertype <> ?");
			preparedStatement.setString(1, status);
			preparedStatement.setInt(2, critical);
			preparedStatement.setString(3, payment);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;

		} catch (Exception e) {
			throw e;
		}
	}

	public ResultSet modifyDeleteTransaction(String modifyStatus,String deleteStatus,String payment,int critical) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_transactions where (tbl_transactions.status=? OR tbl_transactions.status=?) AND tbl_transactions.transfertype <> ? AND tbl_transactions.newamount < ?");
			preparedStatement.setString(1, modifyStatus);
			preparedStatement.setString(2, deleteStatus);
			preparedStatement.setString(3, payment);
			preparedStatement.setInt(4, critical);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;

		} catch (Exception e) {
			throw e;
		}
	}

	public ResultSet checkStatus(String User) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT requeststatus FROM software_security.tbl_transaction_requests where tbl_transaction_requests.requestid = ?");
			preparedStatement.setString(1, User);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;

		} catch (Exception e) {
			throw e;
		}
	}

	public ArrayList<String> checkStatus(String[] rID) throws Exception {
		try {		
			ResultSet rs = null;
			ArrayList<String> status = new ArrayList<String>();
			for(int i=0; i<rID.length; i++)
			{
				preparedStatement = connect.prepareStatement("SELECT status FROM software_security.tbl_transactions where tbl_transactions.transactionid = ?");
				preparedStatement.setString(1, rID[i]);
				rs = preparedStatement.executeQuery();
				if(rs.next())
					status.add(rs.getString("status"));
			}
			return status;

		} catch (Exception e) {
			throw e;
		}
	}

	public void deleteTransactions(String[] rID) throws Exception {
		try {				
			for(int i=0; i<rID.length; i++)
			{
				preparedStatement = connect.prepareStatement("DELETE from software_security.tbl_transactions WHERE transactionid=?");
				preparedStatement.setString(1, rID[i]);
				preparedStatement.executeUpdate();
			}

		} catch (Exception e) {
			throw e;
		}
	}

	public void modifyTransactions(String[] rID,String status) throws Exception {
		try {				
			for(int i=0; i<rID.length; i++)
			{
				preparedStatement = connect.prepareStatement("UPDATE software_security.tbl_transactions SET status=? WHERE transactionid=?");
				preparedStatement.setString(1, status);
				preparedStatement.setString(2, rID[i]);
				preparedStatement.executeUpdate();
			}

		} catch (Exception e) {
			throw e;
		}
	}

	public void updateStatus(String rStatus, String[] rID) throws Exception {
		try {				
			for(int i=0; i<rID.length; i++)
			{
				preparedStatement = connect.prepareStatement("UPDATE software_security.tbl_transaction_requests SET requeststatus=? WHERE requestid=?");
				preparedStatement.setString(1, rStatus);
				preparedStatement.setString(2, rID[i]);
				preparedStatement.executeUpdate();
			}

		} catch (Exception e) {
			throw e;
		}
	}

	public void approveTransactions(String rStatus, double balance,String[] rID) throws Exception {
		try {				
			for(int i=0; i<rID.length; i++)
			{
				preparedStatement = connect.prepareStatement("UPDATE software_security.tbl_transactions tt JOIN software_security.tbl_user_account tua ON tt.destinationaccountnumber = tua.accountnumber SET tt.status=?,tua.balance=? WHERE tt.transactionid=?");
				preparedStatement.setString(1, rStatus);
				preparedStatement.setDouble(2, balance);
				preparedStatement.setString(3, rID[i]);
				preparedStatement.executeUpdate();
			}

		} catch (Exception e) {
			throw e;
		}
	}

	public void rejectTransactions(String rStatus, double balance,String[] rID) throws Exception {
		try {				
			for(int i=0; i<rID.length; i++)
			{
				preparedStatement = connect.prepareStatement("UPDATE software_security.tbl_transactions tt JOIN software_security.tbl_user_account tua ON tt.sourceaccountnumber = tua.accountnumber SET tt.status=?,tua.balance=? WHERE tt.transactionid=?");
				preparedStatement.setString(1, rStatus);
				preparedStatement.setDouble(2, balance);
				preparedStatement.setString(3, rID[i]);
				preparedStatement.executeUpdate();
			}

		} catch (Exception e) {
			throw e;
		}
	}

	public String getDestinationAccountNumber(String rID) throws Exception {
		ResultSet rs = null;
		String account = "";
		try {	
			preparedStatement = connect.prepareStatement("SELECT destinationaccountnumber FROM software_security.tbl_transactions where tbl_transactions.transactionid = ?");
			preparedStatement.setString(1, rID);
			rs = preparedStatement.executeQuery();
			if(rs.next()){
				account = rs.getString("destinationaccountnumber");
			}

		} catch (Exception e) {
			throw e;
		}
		return account;
	}

	public String getSourceAccountNumber(String rID) throws Exception {
		ResultSet rs = null;
		String account = "";
		try {	
			preparedStatement = connect.prepareStatement("SELECT sourceaccountnumber FROM software_security.tbl_transactions where tbl_transactions.transactionid = ?");
			preparedStatement.setString(1, rID);
			rs = preparedStatement.executeQuery();
			if(rs.next()){
				account = rs.getString("sourceaccountnumber");
			}

		} catch (Exception e) {
			throw e;
		}
		return account;
	}

	public Double getBalanceAmount(String[] rID) throws Exception {
		ResultSet rs = null;
		double balance = 0.0;
		try {	
			for(int i=0; i<rID.length; i++)
			{
				preparedStatement = connect.prepareStatement("SELECT newamount FROM software_security.tbl_transactions where tbl_transactions.transactionid = ?");
				preparedStatement.setString(1, rID[i]);
				rs = preparedStatement.executeQuery();
				if(rs.next()){
					balance += rs.getDouble("newamount");
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return balance;
	}

	public Double getBalanceDestinationAccount(String accountNumber) throws Exception {
		ResultSet rs = null;
		double balance = 0.0;
		try {	
			preparedStatement = connect.prepareStatement("SELECT balance FROM software_security.tbl_user_account where tbl_user_account.accountnumber = ?");
			preparedStatement.setString(1,accountNumber);
			rs = preparedStatement.executeQuery();
			if(rs.next()){
				balance = rs.getDouble("balance");
			}

		} catch (Exception e) {
			throw e;
		}
		return balance;
	}

	public Double getBalanceSourceAccount(String accountNumber) throws Exception {
		ResultSet rs = null;
		double balance = 0.0;
		try {	
			preparedStatement = connect.prepareStatement("SELECT balance FROM software_security.tbl_user_account where tbl_user_account.accountnumber = ?");
			preparedStatement.setString(1,accountNumber);
			rs = preparedStatement.executeQuery();
			if(rs.next()){
				balance = rs.getDouble("balance");
			}

		} catch (Exception e) {
			throw e;
		}
		return balance;
	}

	public Boolean checkSameAccountNumber(String[] rID) throws Exception {
		ResultSet rs = null;
		boolean flag = false;
		ArrayList<String> accountList = new ArrayList<String>();
		try {	
			for(int i=0; i<rID.length; i++)
			{
				preparedStatement = connect.prepareStatement("SELECT destinationaccountnumber FROM software_security.tbl_transactions where tbl_transactions.transactionid = ?");
				preparedStatement.setString(1, rID[i]);
				rs = preparedStatement.executeQuery();
				if(rs.next()){
					accountList.add(rs.getString("destinationaccountnumber"));
				}
			}
			for(int i = 0;i<accountList.size() -1 ;i++){
				if(accountList.get(i).equals(accountList.get(i+1))){
					flag = true;
				}
				else{
					flag = false;
					break;
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return flag;
	}

	public Boolean checkSameSourceAccountNumber(String[] rID) throws Exception {
		ResultSet rs = null;
		boolean flag = false;
		ArrayList<String> accountList = new ArrayList<String>();
		try {	
			for(int i=0; i<rID.length; i++)
			{
				preparedStatement = connect.prepareStatement("SELECT sourceaccountnumber FROM software_security.tbl_transactions where tbl_transactions.transactionid = ?");
				preparedStatement.setString(1, rID[i]);
				rs = preparedStatement.executeQuery();
				if(rs.next() && !flag){
					accountList.add(rs.getString("sourceaccountnumber"));
				}
			}
			for(int i = 0;i<accountList.size() -1 ;i++){
				if(accountList.get(i).equals(accountList.get(i+1))){
					flag = true;
				}
				else{
					flag = false;
					break;
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return flag;
	}


	public void updateTransactionData(String rStatus, String[] rID) throws Exception {
		try {				
			for(int i=0; i<rID.length; i++)
			{
				preparedStatement = connect.prepareStatement("UPDATE software_security.tbl_transactions SET status=? WHERE transactionid=?");
				preparedStatement.setString(1, rStatus);
				preparedStatement.setString(2, rID[i]);
				preparedStatement.executeUpdate();
			}

		} catch (Exception e) {
			throw e;
		}
	}

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

	public ResultSet validateUserInfo1(String User) throws Exception {
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

	private ArrayList<Request> writeResultSetRequests(ResultSet resultSet) throws SQLException, NoSuchAlgorithmException {
		// ResultSet is initially before the first data set
		ArrayList<Request> users = new ArrayList<Request>();

		while (resultSet.next()) {

			byte[] requestFrom = resultSet.getBytes("requestFrom");
			byte[] columnname = resultSet.getBytes("modifiedcolumn");
			byte[] oldvalue = resultSet.getBytes("oldvalue");
			byte[] newvalue = resultSet.getBytes("newvalue");



			users.add(new Request(new String(requestFrom),new String(columnname), 
					new String(oldvalue), new String(newvalue)));
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
		}
		preparedStatement.setString(11, parameters[0].trim());
		preparedStatement.executeUpdate();
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
	public void insertintoRequestDatabase(String user, String parameter,String parametertype, String permissionto) throws SQLException{
		// Remove again the insert comment

		preparedStatement = connect
				.prepareStatement("SELECT "+ parametertype+" FROM software_security.tbl_user_details where tbl_user_details.username= ? ; ");
		preparedStatement.setString(1, user);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()){
			byte [] test= resultSet.getBytes("address");
			preparedStatement = connect
					.prepareStatement("INSERT into software_security.tbl_requests(username,requesttype,requestfrom,requestto,modifiedcolumn,approvalstatus,oldvalue,newvalue)VALUES(?,?,?,?,?,?,?,?); ");
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, "modify");
			preparedStatement.setString(3, user);
			preparedStatement.setString(4, permissionto);
			preparedStatement.setString(5, parametertype);
			preparedStatement.setString(6, "pending");
			preparedStatement.setString(7, new String(test));
			preparedStatement.setString(8, parameter);
			preparedStatement.executeUpdate();}

	}
	public void updateRequestDatabase(String newvalue, String columnname, String user) throws SQLException{
		// Remove again the insert comment

		preparedStatement = connect
				.prepareStatement("update software_security.tbl_user_details set tbl_user_details."+columnname+"=? where tbl_user_details.username= ? ; ");
		preparedStatement.setString(1, newvalue);
		preparedStatement.setString(2, user);
		preparedStatement.executeUpdate();
		preparedStatement = connect
				.prepareStatement("update software_security.tbl_requests set tbl_requests.approvalstatus=? where tbl_requests.username= ? and tbl_requests.modifiedcolumn=? and tbl_requests.newvalue=? ; ");
		preparedStatement.setString(1, "approved");
		preparedStatement.setString(2, user);
		preparedStatement.setString (3,columnname);
		preparedStatement.setString(4, newvalue);
		preparedStatement.executeUpdate();
	}
	public void declineRequestDatabase(String newvalue, String columnname, String user) throws SQLException{
		// Remove again the insert comment


		preparedStatement.executeUpdate();
		preparedStatement = connect
				.prepareStatement("update software_security.tbl_requests set tbl_requests.approvalstatus=? where tbl_requests.username= ? and tbl_requests.modifiedcolumn=? and tbl_requests.newvalue=? ; ");
		preparedStatement.setString(1, "declined");
		preparedStatement.setString(2, user);
		preparedStatement.setString (3,columnname);
		preparedStatement.setString(4, newvalue);
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
	public ResultSet readAccountDetails(String ssn, String accounttype) throws Exception{
		try{
			preparedStatement = connect.prepareStatement("Select * FROM software_security.tbl_temporary_user WHERE tbl_temporary_user.ssn=? AND tbl_temporary_user.accounttype=?");
			preparedStatement.setString(1, ssn);
			preparedStatement.setString(2, accounttype);
			ResultSet resultset = preparedStatement.executeQuery();
			return resultset;
		}
		catch(Exception e){
			throw e;
		}
	}

	//Sayantan
	public ResultSet getExsistingAccount(String ssn, String accounttype) throws Exception{
		try{
			preparedStatement = connect.prepareStatement("Select * FROM software_security.tbl_temporary_user WHERE tbl_temporary_user.ssn=? AND tbl_temporary_user.accounttype=?");
			preparedStatement.setString(1, ssn);
			preparedStatement.setString(2, accounttype);
			ResultSet resultset = preparedStatement.executeQuery();
			return resultset;
		}
		catch(Exception e){
			throw e;
		}
	}
	//Sayantan
	public ResultSet getExsistingApprovedAccount(String ssn, String accounttype) throws Exception{
		try{
			preparedStatement = connect.prepareStatement("Select * FROM software_security.tbl_temporary_user WHERE tbl_temporary_user.ssn=? AND tbl_temporary_user.accounttype=? AND tbl_temporary_user.status='APPLICATION_APPROVED'");
			preparedStatement.setString(1, ssn);
			preparedStatement.setString(2, accounttype);
			ResultSet resultset = preparedStatement.executeQuery();
			return resultset;
		}
		catch(Exception e){
			throw e;
		}
	}

	//Sayantan: to add items in the database
	public void openAccountodb(String usertype, String acccounttype, String prefix, String firstname, String middlename, String lastname,String gender, String address,String state, String zip, String passportnumber, String ssn, String email, String phonenumber, String dateofbirth, String documents, String businesslicence, String status) throws SQLException,NoSuchAlgorithmException{
		String applicationid = "";
		status ="APPLICATION_APPLIED";
		preparedStatement = connect.prepareStatement("SELECT (applicationid+1) AS applicationid FROM software_security.tbl_temporary_user ORDER BY applicationid DESC LIMIT 1");
		ResultSet rs = preparedStatement.executeQuery();

		while (rs.next())
		{
			applicationid=rs.getString("applicationid");
		}


		//(SELECT (applicationid+1) AS applicationid FROM software_security.tbl_temporary_user ORDER BY applicationid DESC LIMIT 1)
		preparedStatement = connect.prepareStatement("insert into software_security.tbl_temporary_user values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		preparedStatement.setString(1, applicationid);
		preparedStatement.setString(2, ssn);
		preparedStatement.setString(3, acccounttype);
		preparedStatement.setString(4, usertype);
		preparedStatement.setString(5, prefix);
		preparedStatement.setString(6, firstname);
		preparedStatement.setString(7, middlename);
		preparedStatement.setString(8, lastname);
		preparedStatement.setString(9, gender);
		preparedStatement.setString(10, address);
		preparedStatement.setString(11, state);
		preparedStatement.setString(12, zip);
		preparedStatement.setString(13, passportnumber);
		preparedStatement.setString(14, email);
		preparedStatement.setString(15, phonenumber);
		preparedStatement.setString(16, dateofbirth);
		preparedStatement.setString(17, documents);
		preparedStatement.setString(18, businesslicence);
		preparedStatement.setString(19, status);
		preparedStatement.executeUpdate();
		String Decription=firstname + " " + lastname +" has applied for " + acccounttype; 
		//Request to add the request in the tbl_requests table
		preparedStatement2 = connect.prepareStatement("SELECT (requestid+1) AS requestid FROM software_security.tbl_requests ORDER BY requestid DESC LIMIT 1");
		ResultSet rs2 = preparedStatement2.executeQuery();
		String requestid="";
		while (rs2.next())
		{
			requestid=rs2.getString("requestid");
		}

		preparedStatement1 = connect.prepareStatement("insert into software_security.tbl_requests values (?,?,?,?,?,?,?,?,?)");
		preparedStatement1.setString(1, "EXTERNAL_USERS");
		preparedStatement1.setString(2,null );
		preparedStatement1.setString(3, "ADD_ACCOUNT");
		preparedStatement1.setString(4, "EXTERNAL_USERS");
		preparedStatement1.setString(5, "SYSTEM_MANAGER");
		preparedStatement1.setString(6, Decription);
		preparedStatement1.setString(7, "PENDING");
		preparedStatement1.setString(8, ssn);
		preparedStatement1.setString(9, acccounttype);

		preparedStatement1.executeUpdate();
	}
	//Sayantan: Request PII

	public void requestPIItodb(String username, String requesttype,String requestdetails,String authorizeto) throws SQLException,NoSuchAlgorithmException{

		preparedStatement = connect.prepareStatement("SELECT (pid+1) AS pid FROM software_security.tbl_pii_requests ORDER BY pid DESC LIMIT 1");
		ResultSet rs = preparedStatement.executeQuery();
		String pid="";
		while (rs.next())
		{
			pid=rs.getString("pid");
		}
		preparedStatement = connect.prepareStatement("insert into software_security.tbl_pii_requests values (?,?,?,?,?,?)");
		preparedStatement.setString(1, pid);
		preparedStatement.setString(2, username);
		preparedStatement.setString(3, requesttype);
		preparedStatement.setString(4, requestdetails);
		preparedStatement.setString(5, authorizeto);
		preparedStatement.setString(6, "REQUESTED_PII");


		preparedStatement.executeUpdate();

	}
	//Sayantan: Read existing PII request
	public ResultSet readExsistingPIIRequest(String username, String requesttype,String requestdetails) throws Exception{
		try{
			preparedStatement = connect.prepareStatement("Select * FROM software_security.tbl_pii_requests WHERE tbl_pii_requests.username=? AND tbl_pii_requests.requesttype=? AND tbl_pii_requests.requestdetails=?");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, requesttype);
			preparedStatement.setString(3, requestdetails);
			ResultSet resultset = preparedStatement.executeQuery();
			return resultset;
		}
		catch(Exception e){
			throw e;
		}
	}

	//Sayantan: Code to approve the external users by System Manager
	public void approveUser(String ssn, String accounttype) throws SQLException,NoSuchAlgorithmException{
		try{
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_user_details WHERE tbl_user_details.ssn=?");
			preparedStatement.setString(1, ssn);
			ResultSet rs = preparedStatement.executeQuery();
			MyStringRandomGen rangen= new MyStringRandomGen();
			String tmpusername = rangen.gettmpUsername();
			String tmppwd=rangen.generateRandomString();
			Long accountnumber = null;

			Long debitcardno=rangen.getDebitcardAccountNo();
			//Enter SSN already exists in the tbl_user_details table
			if (!rs.next())
			{
				preparedStatement1 = connect.prepareStatement("SELECT * FROM software_security.tbl_temporary_user WHERE tbl_temporary_user.ssn=? and tbl_temporary_user.accounttype=?");
				preparedStatement1.setString(1, ssn);
				preparedStatement1.setString(2, accounttype);
				ResultSet rs1 = preparedStatement1.executeQuery();
				String usertype= "";
				String prefix="";
				String firstname="";
				String middlename="";
				String lastname="";
				String gender="";
				String address="";
				String state="";
				String zip="";
				String passportnumber="";

				String email="";
				String phonenumber="";
				String dateofbirth="";

				String businesslicence="";

				while (rs1.next())
				{
					accounttype=rs1.getString("accounttype");
					ssn=rs1.getString("ssn");
					usertype=rs1.getString("usertype");
					prefix=rs1.getString("prefix");
					firstname=rs1.getString("firstname");
					middlename=rs1.getString("middlename");
					lastname=rs1.getString("lastname");
					gender=rs1.getString("gender");
					address=rs1.getString("address");
					state=rs1.getString("state");
					address=rs1.getString("gender");
					zip=rs1.getString("zip");
					passportnumber=rs1.getString("passportnumber");
					email=rs1.getString("email");
					phonenumber=rs1.getString("phonenumber");
					dateofbirth=rs1.getString("dateofbirth");
					businesslicence=rs1.getString("businesslicense");


				}

				preparedStatement2 = connect.prepareStatement("insert into software_security.tbl_user_details values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				preparedStatement2.setString(1, tmpusername);
				preparedStatement2.setString(2, ssn);
				preparedStatement2.setString(3, usertype);
				preparedStatement2.setString(4, prefix);
				preparedStatement2.setString(5, firstname);
				preparedStatement2.setString(6, middlename);
				preparedStatement2.setString(7, lastname);
				preparedStatement2.setString(8, gender);
				preparedStatement2.setString(9, address);
				preparedStatement2.setString(10, state);
				preparedStatement2.setString(11, zip);
				preparedStatement2.setString(12, passportnumber);
				preparedStatement2.setString(13, phonenumber);
				preparedStatement2.setString(14, dateofbirth);
				preparedStatement2.setString(15, businesslicence);
				preparedStatement2.setString(16, email);
				preparedStatement2.setString(17, "APPLICATION_APPROVED");




				// Get the public/private key pair
				KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
				keyGen.initialize(1024);
				KeyPair keyPair = keyGen.genKeyPair();
				PrivateKey privateKey = keyPair.getPrivate();
				PublicKey publicKey = keyPair.getPublic();


				// Get the bytes of the public and private keys
				byte[] privateKeyBytes = privateKey.getEncoded();
				byte[] publicKeyBytes = publicKey.getEncoded();



				String pri= String.valueOf(privateKeyBytes);
				String pub = String.valueOf(publicKeyBytes);


				//insert into User Authentication Table
				preparedStatement3 = connect.prepareStatement("insert into software_security.tbl_user_authentication values (?,?,?,?,?,?,?,?,?,?)");
				preparedStatement3.setString(1, tmpusername);
				preparedStatement3.setString(2, tmppwd);
				preparedStatement3.setString(3, "");
				preparedStatement3.setString(4, "No");
				preparedStatement3.setString(5, "No");
				preparedStatement3.setString(6, tmpusername);
				preparedStatement3.setString(7, tmppwd);
				preparedStatement3.setString(8, pub);
				preparedStatement3.setString(9, pri);
				preparedStatement3.setString(10, "No");
				preparedStatement3.executeUpdate();

				//Insert into USER details table
				preparedStatement2.executeUpdate();
				SendEmail sendemail=new SendEmail();
				sendemail.sendEmailApprove(tmpusername, tmppwd, pri,email);

			}
			//insert into User Account
			if(accounttype.equals("Saving Account"))
			{
				accountnumber=rangen.getSavingAccountNo();
			}
			else if (accounttype.equals("Checking Account"))
			{

				accountnumber=rangen.getCheckingAccountNo();
			}
			else if (accounttype.equals("Loan Account"))
			{
				accountnumber=rangen.getLoanAccountNo();
			}
			else if (accounttype.equals("Credit Card"))
			{
				accountnumber=rangen.getCreditcardAccountNo();
			}

			preparedStatement4 = connect.prepareStatement("insert into software_security.tbl_user_account values (?,?,?,?,?)");
			preparedStatement4.setString(1, tmpusername);
			preparedStatement4.setLong(2, accountnumber);
			preparedStatement4.setString(3,accounttype);
			preparedStatement4.setLong(4, 0);
			preparedStatement4.setLong(5, debitcardno);

			preparedStatement4.executeUpdate();


			//Update the "Approved" Status in temp user 

			preparedStatement5 = connect.prepareStatement("UPDATE software_security.tbl_temporary_user SET tbl_temporary_user.status=? WHERE tbl_temporary_user.ssn=? AND tbl_temporary_user.accounttype=?");
			preparedStatement5.setString(1, "APPLICATION_APPROVED");
			preparedStatement5.setString(2, ssn);
			preparedStatement5.setString(3, accounttype);
			preparedStatement5.executeUpdate();
		}
		catch(Exception e){
			throw e;
		}
	}

	//Sayantan: Code to reject the external users by System Manager
	public void rejectUser(String ssn, String accounttype) throws SQLException,NoSuchAlgorithmException{
		try{
			preparedStatement = connect.prepareStatement("UPDATE software_security.tbl_temporary_user SET tbl_temporary_user.status=? WHERE tbl_temporary_user.ssn=? AND tbl_temporary_user.accounttype=?");
			preparedStatement.setString(1, "APPLICATION_REJECTED");
			preparedStatement.setString(2, ssn);
			preparedStatement.setString(3, accounttype);
			preparedStatement.executeUpdate();

		}
		catch(Exception e){
			throw e;
		}
	}

	//Sayantan: Review External User Application by System Manager

	public ArrayList<User> reviewExtUser(String ssn, String accounttype) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT concat('XXX-XXX',substr(ssn,8)) AS ssn, accounttype, usertype, firstname, lastname, email, phonenumber FROM software_security.tbl_temporary_user WHERE tbl_temporary_user.ssn=? AND tbl_temporary_user.accounttype=?");
			preparedStatement.setString(1, ssn);
			preparedStatement.setString(2, accounttype);
			ResultSet rs = preparedStatement.executeQuery();
			ArrayList<User> users = writeResultSet1(rs);
			rs.close();
			return users;

		} catch (Exception e) {
			throw e;
		}
	}
	//Sayantan: For generate set of users
	private ArrayList<User> writeResultSet1(ResultSet resultSet) throws SQLException, NoSuchAlgorithmException {
		// ResultSet is initially before the first data set
		ArrayList<User> users = new ArrayList<User>();

		while (resultSet.next()) {

			String ssn = resultSet.getString("ssn");
			String accounttype = resultSet.getString("accounttype");
			String usertype = resultSet.getString("usertype");
			String firstname = resultSet.getString("firstname");
			String lastname = resultSet.getString("lastname");

			String email1 = resultSet.getString("email");
			String phonenumber = resultSet.getString("phonenumber");


			users.add(new User(new String(ssn), new String(accounttype), new String(usertype), 
					new String(firstname), new String(lastname),new String(email1),new String(phonenumber)));
		}

		return users;
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

	public ResultSet getEmail(String userName) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_user_details where tbl_user_details.username = ?");    
			preparedStatement.setString(1, userName);    
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;

		} catch (Exception e) {
			throw e;
		}
	}
}