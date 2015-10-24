package handlers.systemmanagerHandlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import databseHandler.MySQLAccess;




public class authorizeExtUserHandler {

	static MySQLAccess sql;

	static { 
		sql = new MySQLAccess();
	}

	public ResultSet getExsistingAccount(String ssn, String accounttype) {
		
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			System.out.println("getExistingAccount"+ssn+accounttype);
			return sql.getExsistingAccount(ssn, accounttype);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public ResultSet getExsistingApprovedAccount(String ssn, String accounttype) {
		
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			System.out.println("getExistingAccount"+ssn+accounttype);
			return sql.getExsistingApprovedAccount(ssn, accounttype);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void approveUser(String ssn, String accounttype) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			System.out.println("Accept: Indise handler account type: "+accounttype);
			sql.approveUser(ssn, accounttype );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	public void rejectUser(String ssn, String accounttype) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			System.out.println("Handler: "+accounttype);
			sql.rejectUser(ssn, accounttype );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

}


