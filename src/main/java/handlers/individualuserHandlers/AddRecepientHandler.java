package handlers.individualuserHandlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import databseHandler.MySQLAccess;

public class AddRecepientHandler {

	static MySQLAccess sql;

	static { 
		sql = new MySQLAccess();
	}

	public ResultSet getExsistinguser(String accountNumber) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return sql.readUserDetails(accountNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
}

	//get recepients
	public ArrayList<UserRecepients> getRecepients(String userName) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return sql.readRecepientDataBase(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
}
	public boolean addRecepient(String username,String firstname,String lastname,String accountnumber,String email,boolean success) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.addRecepienttodb(username, firstname, lastname, accountnumber, email);
			success=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
}
	
	public ArrayList<UserAccounts> getaccountdetails(String username) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return sql.readDataBase(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
}
	public boolean submitTrasferRequest(String userName,String TransactionID,String Amount,String SourceAccount,String DestinationAccount,String dateandtime,String transferType,String Status,boolean success) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

        try {
		    sql.insertTransactions(userName, TransactionID, Amount, SourceAccount, DestinationAccount, dateandtime, transferType, Status);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
}
	
}


